package util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.util.Date;

import commands.Commands;
import enums.OrderStatus;
import enums.PaymentMethod;
import enums.ProductType;
import enums.UserType;


import model.Order;
import model.Product;
import model.ShopUser;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import storage.OrderStorage;
import storage.ProductStorage;
import storage.UserStorage;
import util.FileUtil;
import util.IdGenerator;

import java.util.List;
import java.util.Scanner;


public class OnlineShopping implements Commands {

    private static Scanner scanner = new Scanner(System.in);
    private static ProductStorage productStorage = FileUtil.deserializeProductStorage();
    private static OrderStorage orderStorage = FileUtil.deserializeOrderStorage();
    private static UserStorage userStorage = FileUtil.deserializeUserStorage();
    private static ShopUser currentUser = null;

    public static void main(String[] args) {
        boolean isRun = true;
        while (isRun) {
            Commands.printMainCommands();

            String command = scanner.nextLine();
            switch (command) {
                case EXIT:
                    isRun = false;
                    break;
                case LOGIN:
                    login();
                    break;
                case REGISTER:
                    register();
                    break;
                default:
                    System.out.println("Unknown command.");
            }
        }
    }

    private static void register() {
        System.out.println("Please input name,email,password,userType(ADMIN,USER)");
        String userDataStr = scanner.nextLine();
        String[] userDataArr = userDataStr.split(",");
        ShopUser user = userStorage.getByEmail(userDataArr[1]);
        if (user != null) {
            System.out.println("User already exists!");
            return;
        }
        try {
            user = new ShopUser(IdGenerator.generateId(), userDataArr[0], userDataArr[1], userDataArr[2], UserType.valueOf(userDataArr[3].toUpperCase()));
            userStorage.add(user);
            System.out.println("User registered!");
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid data or user type!");
        }
    }

    private static void login() {
        System.out.println("Please input email,password");
        String loginDataStr = scanner.nextLine();
        String[] loginDataArr = loginDataStr.split(",");
        ShopUser user = userStorage.getByEmail(loginDataArr[0]);
        if (user == null || !user.getPassword().equals(loginDataArr[1])) {
            System.out.println("email or password is incorrect!");
            return;
        }
        currentUser = user;
        if (user.getType() == UserType.ADMIN) {
            adminCommands();
        } else if (user.getType() == UserType.USER) {
            userCommands();
        }

    }


    private static void changeOrderStatusById() {
        orderStorage.print();
        System.out.println("Please input order id,new status(NEW,DELIVERED,CANCELED)");
        String orderDataStr = scanner.nextLine();
        String[] orderDataArr = orderDataStr.split(",");
        Order order = orderStorage.getById(orderDataArr[0]);
        if (order == null) {
            System.out.println("Order does not exists");
            return;
        }
        OrderStatus newStatus = OrderStatus.valueOf(orderDataArr[1]);
        if (order.getOrderstatus() == OrderStatus.NEW
                && newStatus == OrderStatus.DELIVERED) {
            if (order.getProduct().getStockQty() < order.getQty()) {
                System.out.println("You do not have enough product qty");
                return;
            }
            order.getProduct().setStockQty((int) (order.getProduct().getStockQty() - order.getQty()));
            order.setOrderstatus(newStatus);
            System.out.println("Order status has changed!");
            FileUtil.serializeOrderStorage(orderStorage);
        }
    }

    private static void removeProductById() {
        productStorage.print();
        System.out.println("Please input product id");
        String productId = scanner.nextLine();
        Product product = productStorage.getById(productId);
        if (product == null) {
            System.out.println("Wrong product Id");
            return;
        }
        product.setRemoved(true);
        FileUtil.serializeProductStorage(productStorage);
    }

    private static void addProduct() {
        System.out.println("Please input name,description,stockQty,price,productType(ELECTRONICS,CLOTHING,BOOKS)");
        String productDataStr = scanner.nextLine();
        String[] productDataArr = productDataStr.split(",");

        try {
            Product product = new Product();
            product.setId(IdGenerator.generateId());
            product.setName(productDataArr[0]);
            product.setDescription(productDataArr[1]);
            product.setStockQty(Integer.parseInt(productDataArr[2]));
            product.setPrice(Double.parseDouble(productDataArr[3]));
            product.setProduct(ProductType.valueOf(productDataArr[4]));
            productStorage.add(product);
            System.out.println("Product added!");
        } catch (IllegalArgumentException | ArrayIndexOutOfBoundsException e) {
            System.out.println("Invalid data: " + e.getMessage());
        }
    }

    private static void adminCommands() {
        boolean isRun = true;
        while (isRun) {
            Commands.printAdminCommands();
            String command = scanner.nextLine();
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    currentUser = null;
                    break;
                case ADD_PRODUCT:
                    addProduct();
                    break;
                case REMOVE_PRODUCT_BY_ID:
                    removeProductById();
                    break;
                case PRINT_PRODUCTS:
                    productStorage.print();
                    break;
                case PRINT_USERS:
                    userStorage.printByType(UserType.USER);
                    break;
                case PRINT_ORDERS:
                    orderStorage.print();
                    break;
                case CHANGE_ORDER_STATUS_BY_ID:
                    changeOrderStatusById();
                    break;
                case EXPORTS_ORDERS_TO_EXCEL:
                    exportOrdersToExcel();
                default:
                    System.out.println("Unknown command!");

            }
        }
    }

    private static void exportOrdersToExcel() {
        System.out.println("Please input path.");
        String path = scanner.nextLine();
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            List<Order> orders = orderStorage.getOrders();
            try (Workbook workbook = new XSSFWorkbook()) {
                Sheet orderSheet = workbook.createSheet("orders");
                Row headRow = orderSheet.createRow(0);
                Cell headIdCell = headRow.createCell(0);
                headIdCell.setCellValue("Order id");
                Cell headUserName = headRow.createCell(1);
                headUserName.setCellValue("User Name");
                Cell headProductNameCell = headRow.createCell(2);
                headProductNameCell.setCellValue("Product Name");
                Cell headOrderQty = headRow.createCell(3);
                headOrderQty.setCellValue("Product price");
                Cell headOrderPrice = headRow.createCell(4);
                headOrderPrice.setCellValue("Product price");

                int rowIndex = 1;
                for (Order order : orders) {
                    Row row = orderSheet.createRow(rowIndex++);
                    Cell idCell = row.createCell(0);

                    idCell.setCellValue(order.getId());

                    Cell userNameCell = row.createCell(1);
                    userNameCell.setCellValue(order.getUser().getName());
                    Cell productNameCell = row.createCell(2);
                    productNameCell.setCellValue(order.getProduct().getName());
                    Cell orderQtyCell = row.createCell(3);
                    orderQtyCell.setCellValue(order.getQty());
                    Cell orderPriceCell = row.createCell(4);
                    orderPriceCell.setCellValue(order.getPrice());


                }
                workbook.write(new FileOutputStream(new File(directory,"report_"+System.currentTimeMillis()+".xlsx")));
            } catch (Exception e) {
e.printStackTrace();
            }
        } else {
            System.out.println("Wrong path");
        }
    }


    private static void userCommands() {
        boolean isRun = true;
        while (isRun) {
            Commands.printUserCommands();
            String command = scanner.nextLine();
            switch (command) {
                case LOGOUT:
                    isRun = false;
                    currentUser = null;
                    break;
                case PRINT_ALL_PRODUCTS:
                    productStorage.print();
                    break;
                case BUY_PRODUCT:
                    buyProduct();
                    break;
                case PRINT_MY_ORDERS:
                    orderStorage.printByUser(currentUser);
                    break;
                case CANCEL_ORDER_BY_ID:
                    cancelOrderById();
                    break;
                default:
                    System.out.println("Unknown Command!");
            }
        }
    }

    private static void buyProduct() {
        productStorage.print();
        System.out.println("Please input productId,qty,paymentMethod(CARD,CASH,PAYPAL)");
        String orderDataStr = scanner.nextLine();
        String[] orderDataArr = orderDataStr.split(",");
        Product product = productStorage.getById(orderDataArr[0]);
        PaymentMethod paymentMethod = PaymentMethod.valueOf(orderDataArr[2]);
        if (product == null) {
            System.out.println("Wrong product Id");
            return;
        }
        int qty = Integer.parseInt(orderDataArr[1]);

        if (product.getStockQty() < qty) {
            System.out.println("Wrong qty");
            return;
        }
        double price = qty * product.getPrice();

        System.out.println("You want to buy " + product.getName() + " qty: " + qty + " price: " + price + " paymentMethod: " + paymentMethod + " \n Are you sure? (Yes/No)");
        String answer = scanner.nextLine();

        if (!answer.equalsIgnoreCase("yes")) {
            System.out.println("Order canceled!");
            return;
        }

        Order order = new Order(IdGenerator.generateId(), currentUser, product, new Date(), price, OrderStatus.NEW, qty, paymentMethod);
        orderStorage.add(order);
    }

    private static void cancelOrderById() {
        orderStorage.printByUser(currentUser);
        System.out.println("Please input order Id");
        String orderId = scanner.nextLine();
        Order order = orderStorage.getById(orderId);
        if (order == null || !order.getUser().equals(currentUser)) {
            System.out.println("Wrong order id");
            return;
        }
        if (order.getOrderstatus() != OrderStatus.NEW) {
            System.out.println("Order can not be canceled!");
            return;
        }
        order.setOrderstatus(OrderStatus.CANCELED);
        System.out.println("Order canceled!");
        FileUtil.serializeOrderStorage(orderStorage);
    }

}