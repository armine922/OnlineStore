package commands;


    public interface Commands {

        //main commands
        String EXIT = "0";
        String LOGIN = "1";
        String REGISTER = "2";
        //user commands
        String LOGOUT = "0";
        String PRINT_ALL_PRODUCTS = "1";
        String BUY_PRODUCT = "2";
        String PRINT_MY_ORDERS = "3";
        String CANCEL_ORDER_BY_ID = "4";
        //admin commands
        String ADD_PRODUCT = "1";
        String REMOVE_PRODUCT_BY_ID = "2";
        String PRINT_PRODUCTS = "3";
        String PRINT_USERS = "4";
        String PRINT_ORDERS = "5";
        String CHANGE_ORDER_STATUS_BY_ID = "6";
        String EXPORTS_ORDERS_TO_EXCEL="7";


        static void printMainCommands() {
            System.out.println("Please input " + EXIT + " for EXIT");
            System.out.println("Please input " + LOGIN + " for LOGIN");
            System.out.println("Please input " + REGISTER + " for REGISTER");
        }

        static void printAdminCommands() {
            System.out.println("Please input " + LOGOUT + " for LOGOUT");
            System.out.println("Please input " + ADD_PRODUCT + " for ADD_PRODUCT");
            System.out.println("Please input " + REMOVE_PRODUCT_BY_ID + " for REMOVE_PRODUCT_BY_ID");
            System.out.println("Please input " + PRINT_PRODUCTS + " for PRINT_PRODUCTS");
            System.out.println("Please input " + PRINT_USERS + " for PRINT_USERS");
            System.out.println("Please input " + PRINT_ORDERS + " for PRINT_ORDERS");
            System.out.println("Please input " + CHANGE_ORDER_STATUS_BY_ID + " for CHANGE_ORDER_STATUS_BY_ID");
            System.out.println("Please input " + EXPORTS_ORDERS_TO_EXCEL + " for CHANGE_ORDER_STATUS_BY_ID");
        }

        static void printUserCommands() {
            System.out.println("Please input " + LOGOUT + " for LOGOUT");
            System.out.println("Please input " + PRINT_ALL_PRODUCTS + " for PRINT_ALL_PRODUCTS");
            System.out.println("Please input " + BUY_PRODUCT + " for BUY_PRODUCT");
            System.out.println("Please input " + PRINT_MY_ORDERS + " for PRINT_MY_ORDERS");
            System.out.println("Please input " + CANCEL_ORDER_BY_ID + " for CANCEL_ORDER_BY_ID");

        }

    }


