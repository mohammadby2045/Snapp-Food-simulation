package View;

import Controller.Controller;

import java.util.regex.Matcher;

public class LoginMenu {
    private final Controller controller;

    public LoginMenu(Controller controller) {
        this.controller = controller;
    }

    public String run() {
        Matcher matcher;
        String command, result;

        while (true) {
            command = Menu.getScanner().nextLine();

            if (command.matches("^\\s*exit\\s*$"))
                return "exit";
            if ((matcher = Menu.getMatcher(command, "^\\s*register\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*$")) != null)
                System.out.println(controller.register(matcher.group("username"), matcher.group("password")));
            else if ((matcher = Menu.getMatcher(command, "^\\s*login\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*$")) != null) {
                result = controller.login(matcher.group("username"), matcher.group("password"));
                System.out.println(result);
                if (result.equals("login successful"))
                    return "Logged in";
            } else if ((matcher = Menu.getMatcher(command, "^\\s*change\\s+password\\s+(?<username>\\S+)\\s+(?<oldpass>\\S+)\\s+(?<newpass>\\S+)\\s*$")) != null) {
                System.out.println(controller.changePassword(matcher.group("username"), matcher.group("oldpass"), matcher.group("newpass")));
            } else if ((matcher = Menu.getMatcher(command, "^\\s*remove\\s+account\\s+(?<username>\\S+)\\s+(?<password>\\S+)\\s*$")) != null) {
                System.out.println(controller.removeAccount(matcher.group("username"), matcher.group("password")));
            } else if (command.matches("^\\s*show\\s+current\\s+menu\\s*$")) {
                System.out.println("login menu");
            }
            else {
                System.out.println("invalid command!");
            }
        }
    }
}