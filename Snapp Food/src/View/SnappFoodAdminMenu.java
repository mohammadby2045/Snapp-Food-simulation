package View;

import Controller.Controller;

import java.util.regex.Matcher;

public class SnappFoodAdminMenu {
    private final Controller controller;

    public SnappFoodAdminMenu(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        Matcher matcher;
        String command;

        while (true) {
            command = Menu.getScanner().nextLine();
            if ((matcher = Menu.getMatcher(command, "^\\s*add\\s+restaurant\\s+(?<name>\\S+)\\s+(?<password>\\S+)\\s+(?<type>\\S+)\\s*$")) != null) {
                System.out.println(controller.addNewRestaurant(matcher.group("name"), matcher.group("password"), matcher.group("type")));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*remove\\s+restaurant\\s+(?<name>\\S+)\\s*$")) != null) {
                controller.removeRestaurant(matcher.group("name"));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*show\\s+restaurant\\s+-t\\s+(?<type>\\S+)\\s*$")) != null) {
                controller.showRestaurantType(matcher.group("type"));
            } else if (command.matches("^\\s*show\\s+restaurant\\s*$")) {
                controller.showRestaurant();
            } else if ((matcher = Menu.getMatcher(command, "^\\s*set\\s+discount\\s+(?<username>\\S+)\\s+(?<amount>-?\\d+)\\s+(?<code>\\S+)\\s*$")) != null) {
                System.out.println(controller.setDiscount(matcher.group("username"), matcher.group("amount"), matcher.group("code")));
            } else if (command.matches("^\\s*show\\s+discounts\\s*$")) {
                controller.showDiscounts();
            } else if (command.matches("^\\s*logout\\s*$")) {
                controller.logout();
                return;
            } else if (command.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("Snappfood admin menu");
            } else System.out.println("invalid command!");
        }
    }
}