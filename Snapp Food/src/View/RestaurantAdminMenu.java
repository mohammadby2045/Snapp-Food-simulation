package View;

import Controller.Controller;

import java.util.regex.Matcher;

public class RestaurantAdminMenu {
    private final Controller controller;

    public RestaurantAdminMenu(Controller controller) {
        this.controller = controller;
    }

    public void run() {
        Matcher matcher;
        String command;

        while (true) {
            command = Menu.getScanner().nextLine();
            if ((matcher = Menu.getMatcher(command, "^\\s*charge\\s+account\\s+(?<amount>-?\\d+)\\s*$")) != null) {
                System.out.println(controller.chargeAccount(matcher.group("amount")));
            } else if (command.matches("^\\s*show\\s+balance\\s*$")) {
                System.out.println(controller.showBalance());
            } else if ((matcher = Menu.getMatcher(command, "^\\s*add\\s+food\\s+(?<name>\\S+)\\s+(?<category>\\S+)\\s+(?<price>-?\\d+)\\s+(?<cost>-?\\d+)\\s*$")) != null) {
                System.out.println(controller.addFood(matcher.group("name"), matcher.group("category"), matcher.group("price"), matcher.group("cost")));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*remove\\s+food\\s+(?<name>\\S+)\\s*$")) != null) {
                controller.removeFood(matcher.group("name"));
            } else if (command.matches("^\\s*logout\\s*$")) {
                controller.logout();
                return;
            } else if (command.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("restaurant admin menu");
            } else System.out.println("invalid command!");
        }
    }
}