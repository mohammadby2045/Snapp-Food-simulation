package View;

import Controller.Controller;

import java.util.regex.Matcher;

public class CustomerMenu {
    private final Controller controller;

    public CustomerMenu(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        Matcher matcher;
        String command;

        while (true) {
            command = Menu.getScanner().nextLine();
            if ((matcher = Menu.getMatcher(command, "^\\s*charge\\s+account\\s+(?<amount>-?\\d+)\\s*$")) != null) {
                System.out.println(controller.chargeAccountForCustomer(matcher.group("amount")));
            } else if (command.matches("^\\s*show\\s+balance\\s*$")) {
                System.out.println(controller.showBalanceForCustomer());
            } else if ((matcher = Menu.getMatcher(command, "^\\s*show\\s+restaurant\\s+-t\\s+(?<type>\\S+)\\s*$")) != null) {
                controller.showRestaurantTypeForCustomer(matcher.group("type"));
            } else if (command.matches("^\\s*show\\s+restaurant\\s*$")) {
                controller.showRestaurantForCustomer();
            } else if ((matcher = Menu.getMatcher(command, "^\\s*show\\s+menu\\s+(?<name>\\S+)\\s*$")) != null) {
                controller.showRestaurantMenu(matcher.group("name"));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*show\\s+menu\\s+(?<name>\\S+)\\s+-c\\s+(?<category>\\S+)\\s*$")) != null) {
                controller.showRestaurantMenuCategory(matcher.group("name"), matcher.group("category"));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*add\\s+to\\s+cart\\s+(?<rname>\\S+)\\s+(?<fname>\\S+)\\s*$")) != null) {
                System.out.println(controller.addToCart(matcher.group("rname"), matcher.group("fname")));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*add\\s+to\\s+cart\\s+(?<rname>\\S+)\\s+(?<fname>\\S+)\\s+-n\\s+(?<number>-?\\d+)\\s*$")) != null) {
                System.out.println(controller.addToCartWithNumber(matcher.group("rname"), matcher.group("fname"), matcher.group("number")));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*remove\\s+from\\s+cart\\s+(?<rname>\\S+)\\s+(?<fname>\\S+)\\s*$")) != null) {
                System.out.println(controller.removeFromCart(matcher.group("rname"), matcher.group("fname")));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*remove\\s+from\\s+cart\\s+(?<rname>\\S+)\\s+(?<fname>\\S+)\\s+-n\\s+(?<number>-?\\d+)\\s*$")) != null) {
                System.out.println(controller.removeFromCartWithNumber(matcher.group("rname"), matcher.group("fname"), matcher.group("number")));
            } else if (command.matches("^\\s*show\\s+cart\\s*$")) {
                controller.showCart();
            } else if (command.matches("^\\s*show\\s+discounts\\s*$")) {
                controller.showDiscountsForCustomer();
            } else if (command.matches("^\\s*purchase\\s+cart\\s*$")) {
                System.out.println(controller.purchaseCart());
            } else if ((matcher = Menu.getMatcher(command, "^\\s*purchase\\s+cart\\s+-\\s*d\\s+(?<discount>\\S+)\\s*$")) != null) {
                System.out.println(controller.purchaseCartWithDiscount(matcher.group("discount")));
            } else if (command.matches("^\\s*logout\\s*$")) {
                controller.logout();
                return;
            } else if (command.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("customer menu");
            } else System.out.println("invalid command!");
        }
    }
}