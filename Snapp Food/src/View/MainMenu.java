package View;

import Controller.Controller;

public class MainMenu {
    private final Controller controller;

    public MainMenu(Controller controller) {
        this.controller = controller;
    }

    public String run() {
        String command;
        while (true) {
            command = Menu.getScanner().nextLine();
            if (command.matches("^\\s*enter\\s+customer\\s+menu\\s*$")) {
                if (controller.isLoggedInUserCustomer()) {
                    System.out.println("enter menu successful: You are in the customer menu!");
                    return "CustomerMenu";
                } else
                    System.out.println("enter menu failed: access denied");
            } else if (command.matches("^\\s*enter\\s+restaurant\\s+admin\\s+menu\\s*$")) {
                if (controller.isLoggedInUserAdminRestaurant()) {
                    System.out.println("enter menu successful: You are in the restaurant admin menu!");
                    return "RestaurantAdminMenu";
                } else
                    System.out.println("enter menu failed: access denied");
            } else if (command.matches("^\\s*enter\\s+Snappfood\\s+admin\\s+menu\\s*$")) {
                if (controller.isLoggedInUserAdminSF()) {
                    System.out.println("enter menu successful: You are in the Snappfood admin menu!");
                    return "RestaurantAdminSF";
                } else
                    System.out.println("enter menu failed: access denied");
            } else if (command.matches("^\\s*logout\\s*$")) {
                controller.logout();
                return "logout";
            } else if (command.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("main menu");
            } else if (command.matches("^\\s*enter\\s+([\\S\\s]+)\\s*$")) {
                System.out.println("enter menu failed: invalid menu name");
            } else System.out.println("invalid command!");
        }
    }
}