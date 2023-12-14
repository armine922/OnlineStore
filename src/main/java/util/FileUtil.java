package util;




import storage.OrderStorage;
import storage.ProductStorage;
import storage.UserStorage;

import java.io.*;

public  class FileUtil {
    private static final String ORDER_FILE_PATH = "/Users/developer/Desktop/OnlineStore/src/main/java/infoplace/order.data";
    private static final String USER_FILE_PATH = "/Users/developer/Desktop/OnlineStore/src/main/java/infoplace/product.data";
    private static final String PRODUCT_FILE_PATH = "/Users/developer/Desktop/OnlineStore/src/main/java/infoplace/product.data";

    public static void serializeProductStorage(ProductStorage productStorage) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(PRODUCT_FILE_PATH))) {
            outputStream.writeObject(productStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void serializeUserStorage(UserStorage userStorage) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(USER_FILE_PATH))) {
            outputStream.writeObject(userStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public static void serializeOrderStorage(OrderStorage orderStorage) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(ORDER_FILE_PATH))) {
            outputStream.writeObject(orderStorage);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public static ProductStorage deserializeProductStorage() {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            return new ProductStorage();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(PRODUCT_FILE_PATH))) {
            Object o = inputStream.readObject();
            if (o instanceof ProductStorage){
               ProductStorage productStorage=(ProductStorage) o;
                return productStorage ;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        } return new ProductStorage();


    }

    public static UserStorage deserializeUserStorage() {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            return new UserStorage();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(USER_FILE_PATH))) {
            Object o = inputStream.readObject();
            if (o instanceof UserStorage  ) {
                UserStorage userStorage=(UserStorage) o;
                return userStorage;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new UserStorage();

    }

    public static OrderStorage deserializeOrderStorage() {
        File file = new File(USER_FILE_PATH);
        if (!file.exists()) {
            return new OrderStorage();
        }
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(ORDER_FILE_PATH))) {
            Object o = inputStream.readObject();
            if (o instanceof OrderStorage ){
                OrderStorage orderStorage = (OrderStorage) o;
                return orderStorage;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return new OrderStorage();
    }


}
