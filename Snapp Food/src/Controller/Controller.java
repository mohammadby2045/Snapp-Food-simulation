package Controller;

import java.util.*;

import Model.*;
import View.*;

public class Controller {

    private Customer loggedInCustomer;
    private Restaurant loggedInRestaurant;
    private AdminSnappFood loggedInAdminSF;
    private AdminRestaurant loggedInAdminRestaurant;
    private final LoginMenu loginMenu;
    private final RestaurantAdminMenu restaurantAdminMenu;
    private final SnappFoodAdminMenu snappFoodAdminMenu;
    private final MainMenu mainMenu;
    private final CustomerMenu customerMenu;

    public boolean isLoggedInUserCustomer() {
        return loggedInCustomer != null;
    }

    public boolean isLoggedInUserAdminRestaurant() {
        return loggedInAdminRestaurant != null;
    }

    public boolean isLoggedInUserAdminSF() {
        return loggedInAdminSF != null;
    }

    public Controller() {
        loginMenu = new LoginMenu(this);
        mainMenu = new MainMenu(this);
        restaurantAdminMenu = new RestaurantAdminMenu(this);
        snappFoodAdminMenu = new SnappFoodAdminMenu(this);
        customerMenu = new CustomerMenu(this);
    }

    public void run() {
        String AdminSfUsername = Menu.getScanner().nextLine();
        String AdminSFPassword = Menu.getScanner().nextLine();
        AdminSnappFood.addAdminSF(AdminSFPassword, AdminSfUsername);
        boolean bool = true;
        while (true) {
            if (bool)
                if (loginMenu.run().equals("exit")) return;
            switch (mainMenu.run()) {
                case "CustomerMenu": {
                    bool = true;
                    customerMenu.run();
                    break;
                }
                case "RestaurantAdminSF": {
                    bool = true;
                    snappFoodAdminMenu.run();
                    break;
                }
                case "RestaurantAdminMenu": {
                    bool = true;
                    restaurantAdminMenu.run();
                    break;
                }
                case "logout": {
                    bool = false;
                    if (loginMenu.run().equals("exit")) return;
                    break;
                }
            }
        }
    }

    public String register(String username, String password) {
        if (!username.matches("^[a-zA-Z0-9_]+$") || !username.matches(".*[A-Za-z].*"))
            return "register failed: invalid username format";
        if (Customer.getCustomerByUsername(username) != null || AdminRestaurant.getAdminRestaurantByUsername(username) != null || AdminSnappFood.getAdminSFByUsername(username) != null)
            return "register failed: username already exists";
        if (!password.matches("^[a-zA-Z0-9_]+$"))
            return "register failed: invalid password format";
        if (password.length() < 5 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*"))
            return "register failed: weak password";
        Customer.addCustomer(username, password);
        return "register successful";
    }

    public String login(String username, String password) {
        if ((loggedInCustomer = Customer.getCustomerByUsername(username)) != null) {
            if (!loggedInCustomer.isPasswordCorrect(password)) {
                loggedInCustomer = null;
                return "login failed: incorrect password";
            }
            return "login successful";
        } else if ((loggedInAdminRestaurant = AdminRestaurant.getAdminRestaurantByUsername(username)) != null) {
            if (!loggedInAdminRestaurant.isPasswordCorrect(password)) {
                loggedInAdminRestaurant = null;
                return "login failed: incorrect password";
            }
            loggedInRestaurant = Restaurant.getRestaurantByUsername(username);
            return "login successful";
        } else if ((loggedInAdminSF = AdminSnappFood.getAdminSFByUsername(username)) != null) {
            if (!loggedInAdminSF.isPasswordCorrect(password)) {
                loggedInAdminSF = null;
                return "login failed: incorrect password";
            }
            return "login successful";
        } else
            return "login failed: username not found";
    }

    public void logout() {
        loggedInAdminRestaurant = null;
        loggedInCustomer = null;
        loggedInAdminSF = null;
    }

    public String changePassword(String username, String oldpass, String password) {
        boolean checkPassword = password.length() < 5 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*");
        if ((loggedInCustomer = Customer.getCustomerByUsername(username)) != null) {
            if (!loggedInCustomer.isPasswordCorrect(oldpass)) {
                loggedInCustomer = null;
                return "password change failed: incorrect password";
            }
            if (!password.matches("^[a-zA-Z0-9_]+$")) {
                loggedInCustomer = null;
                return "password change failed: invalid new password";
            }
            if (checkPassword) {
                loggedInCustomer = null;
                return "password change failed: weak new password";
            }
            loggedInCustomer = null;
            Customer.removeCustomer(username);
            Customer.addCustomer(username, password);
            return "password change successful";
        } else if ((loggedInAdminRestaurant = AdminRestaurant.getAdminRestaurantByUsername(username)) != null) {
            if (!loggedInAdminRestaurant.isPasswordCorrect(oldpass)) {
                loggedInAdminRestaurant = null;
                return "password change failed: incorrect password";
            }
            if (!password.matches("^[a-zA-Z0-9_]+$")) {
                loggedInAdminRestaurant = null;
                return "password change failed: invalid new password";
            }
            if (checkPassword) {
                loggedInAdminRestaurant = null;
                return "password change failed: weak new password";
            }
            loggedInAdminRestaurant = null;
            AdminRestaurant.removeAdminRestaurant(username);
            AdminRestaurant.addAdminRestaurant(username, password);
            return "password change successful";
        } else if ((loggedInAdminSF = AdminSnappFood.getAdminSFByUsername(username)) != null) {
            if (!loggedInAdminSF.isPasswordCorrect(oldpass)) {
                loggedInAdminSF = null;
                return "password change failed: incorrect password";
            }
            loggedInAdminSF = null;
            AdminSnappFood.removeAdminSF(username);
            AdminSnappFood.addAdminSF(password, username);
            return "password change successful";
        }
        return "password change failed: username not found";
    }

    public String removeAccount(String username, String password) {
        if ((loggedInCustomer = Customer.getCustomerByUsername(username)) != null) {
            if (!loggedInCustomer.isPasswordCorrect(password)) {
                loggedInCustomer = null;
                return "remove account failed: incorrect password";
            }
            Customer.removeCustomer(username);
            return "remove account successful";
        } else if ((loggedInAdminRestaurant = AdminRestaurant.getAdminRestaurantByUsername(username)) != null) {
            if (!loggedInAdminRestaurant.isPasswordCorrect(password)) {
                loggedInAdminRestaurant = null;
                return "remove account failed: incorrect password";
            }
            Restaurant.removeRestaurant(username);
            AdminRestaurant.removeAdminRestaurant(username);
            return "remove account successful";
        }
        return "remove account failed: username not found";
    }

    public String addNewRestaurant(String name, String password, String type) {
        if (!name.matches("^[a-zA-Z0-9_]+$") || !name.matches(".*[A-Za-z].*"))
            return "add restaurant failed: invalid username format";
        else if (Customer.getCustomerByUsername(name) != null || AdminRestaurant.getAdminRestaurantByUsername(name) != null || AdminSnappFood.getAdminSFByUsername(name) != null) {
            return "add restaurant failed: username already exists";
        } else if (!password.matches("^[a-zA-Z0-9_]+$")) {
            return "add restaurant failed: invalid password format";
        } else if (password.length() < 5 || !password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || !password.matches(".*[0-9].*")) {
            return "add restaurant failed: weak password";
        } else if (!type.matches("^[a-z-]+$")) {
            return "add restaurant failed: invalid type format";
        }
        AdminRestaurant.addAdminRestaurant(name, password);
        Restaurant.addRestaurant(name, type, 0);
        return "add restaurant successful";
    }

    public void removeRestaurant(String name) {
        if (Restaurant.getRestaurantByUsername(name) == null) {
            System.out.println("remove restaurant failed: restaurant not found");
            return;
        }
        AdminRestaurant.removeAdminRestaurant(name);
        Restaurant.removeRestaurant(name);
    }

    public void showRestaurantType(String type) {
        int flag = 1;
        for (int i = 0; i < Restaurant.getRestaurants().size(); i++) {
            if (Restaurant.getRestaurants().get(i).getType().equals(type)) {
                System.out.println(flag + ") " + Restaurant.getRestaurants().get(i).getName() + ": type=" + type + " balance=" + Restaurant.getRestaurants().get(i).getBalance());
                flag++;
            }
        }
    }

    public void showRestaurant() {
        for (int i = 0; i < Restaurant.getRestaurants().size(); i++) {
            System.out.println((i + 1) + ") " + Restaurant.getRestaurants().get(i).getName() + ": type=" + Restaurant.getRestaurants().get(i).getType() + " balance=" + Restaurant.getRestaurants().get(i).getBalance());
        }
    }

    public String setDiscount(String username, String amount, String code) {
        if (Customer.getCustomerByUsername(username) == null) {
            return "set discount failed: username not found";
        }
        if (Integer.parseInt(amount) <= 0) {
            return "set discount failed: invalid amount";
        }
        if (!code.matches("^[a-zA-Z0-9]+$")) {
            return "set discount failed: invalid code format";
        }
        Customer.getCustomerByUsername(username).addDiscount(code, Integer.parseInt(amount));
        AdminSnappFood.addDiscount(code, amount, username);
        return "set discount successful";
    }

    public void showDiscounts() {
        Set<String> keySet = AdminSnappFood.getDiscountsamount().keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        for (int i = 0; i < keyArray.length; i++) {
            System.out.println((i + 1) + ") " + keyArray[i] + " | amount=" + AdminSnappFood.getDiscountsamount().get(keyArray[i]) + " --> user=" + AdminSnappFood.getDiscountsusername().get(keyArray[i]));
        }
    }

    public String chargeAccount(String amount) {
        if (Integer.parseInt(amount) <= 0)
            return "charge account failed: invalid cost or price";
        int balance = loggedInRestaurant.getBalance();
        loggedInRestaurant.setBalance(balance + Integer.parseInt(amount));
        return "charge account successful";
    }

    public Integer showBalance() {
        return loggedInRestaurant.getBalance();
    }

    public String addFood(String name, String category, String price, String cost) {
        if (!category.matches("^starter$") && !category.matches("^entree$") && !category.matches("^dessert$")) {
            return "add food failed: invalid category";
        }
        if (!name.matches("^[a-z-]+$")) {
            return "add food failed: invalid food name";
        }
        if (loggedInRestaurant.getFoods().contains(name)) {
            return "add food failed: food already exists";
        }
        if (Integer.parseInt(price) <= 0 || Integer.parseInt(cost) <= 0 || Integer.parseInt(cost) > Integer.parseInt(price)) {
            return "add food failed: invalid cost or price";
        }
        Restaurant.addFood(loggedInRestaurant, name, category, Integer.parseInt(price), Integer.parseInt(cost));
        return "add food successful";
    }

    public void removeFood(String name) {
        if (!loggedInRestaurant.getFoods().contains(name)) {
            System.out.println("remove food failed: food not found");
            return;
        }
        Restaurant.removeFood(loggedInRestaurant, name);
    }

    public void showRestaurantTypeForCustomer(String type) {
        int flag = 1;
        for (int i = 0; i < Restaurant.getRestaurants().size(); i++) {
            if (Restaurant.getRestaurants().get(i).getType().equals(type)) {
                System.out.println(flag + ") " + Restaurant.getRestaurants().get(i).getName() + ": type=" + type);
                flag++;
            }
        }
    }

    public void showRestaurantForCustomer() {
        for (int i = 0; i < Restaurant.getRestaurants().size(); i++) {
            System.out.println((i + 1) + ") " + Restaurant.getRestaurants().get(i).getName() + ": type=" + Restaurant.getRestaurants().get(i).getType());
        }
    }

    public void showRestaurantMenu(String name) {
        int index = -1;
        for (int i = 0; i < Restaurant.getRestaurants().size(); i++) {
            if (Restaurant.getRestaurants().get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("show menu failed: restaurant not found");
            return;
        }
        System.out.println("<< STARTER >>");
        for (int i = 0; i < Restaurant.getRestaurants().get(index).getFoods().size(); i++) {
            if (Restaurant.getRestaurants().get(index).getFoodCategories().get(Restaurant.getRestaurants().get(index).getFoods().get(i)).equals("starter")) {
                System.out.println(Restaurant.getRestaurants().get(index).getFoods().get(i) + " | price=" + Restaurant.getRestaurants().get(index).getFoodPrices().get(Restaurant.getRestaurants().get(index).getFoods().get(i)));
            }
        }
        System.out.println("<< ENTREE >>");
        for (int i = 0; i < Restaurant.getRestaurants().get(index).getFoods().size(); i++) {
            if (Restaurant.getRestaurants().get(index).getFoodCategories().get(Restaurant.getRestaurants().get(index).getFoods().get(i)).equals("entree")) {
                System.out.println(Restaurant.getRestaurants().get(index).getFoods().get(i) + " | price=" + Restaurant.getRestaurants().get(index).getFoodPrices().get(Restaurant.getRestaurants().get(index).getFoods().get(i)));
            }
        }
        System.out.println("<< DESSERT >>");
        for (int i = 0; i < Restaurant.getRestaurants().get(index).getFoods().size(); i++) {
            if (Restaurant.getRestaurants().get(index).getFoodCategories().get(Restaurant.getRestaurants().get(index).getFoods().get(i)).equals("dessert")) {
                System.out.println(Restaurant.getRestaurants().get(index).getFoods().get(i) + " | price=" + Restaurant.getRestaurants().get(index).getFoodPrices().get(Restaurant.getRestaurants().get(index).getFoods().get(i)));
            }
        }
    }

    public void showRestaurantMenuCategory(String name, String category) {
        int index = -1;
        for (int i = 0; i < Restaurant.getRestaurants().size(); i++) {
            if (Restaurant.getRestaurants().get(i).getName().equals(name)) {
                index = i;
                break;
            }
        }
        if (index == -1) {
            System.out.println("show menu failed: restaurant not found");
            return;
        }
        if (!category.matches("^starter$") && !category.matches("^entree$") && !category.matches("^dessert$")) {
            System.out.println("show menu failed: invalid category");
            return;
        }
        if (category.matches("^starter$")) {
            for (int i = 0; i < Restaurant.getRestaurants().get(index).getFoods().size(); i++) {
                if (Restaurant.getRestaurants().get(index).getFoodCategories().get(Restaurant.getRestaurants().get(index).getFoods().get(i)).equals("starter")) {
                    System.out.println(Restaurant.getRestaurants().get(index).getFoods().get(i) + " | price=" + Restaurant.getRestaurants().get(index).getFoodPrices().get(Restaurant.getRestaurants().get(index).getFoods().get(i)));
                }
            }
        } else if (category.matches("^entree$")) {
            for (int i = 0; i < Restaurant.getRestaurants().get(index).getFoods().size(); i++) {
                if (Restaurant.getRestaurants().get(index).getFoodCategories().get(Restaurant.getRestaurants().get(index).getFoods().get(i)).equals("entree")) {
                    System.out.println(Restaurant.getRestaurants().get(index).getFoods().get(i) + " | price=" + Restaurant.getRestaurants().get(index).getFoodPrices().get(Restaurant.getRestaurants().get(index).getFoods().get(i)));
                }
            }
        } else {
            for (int i = 0; i < Restaurant.getRestaurants().get(index).getFoods().size(); i++) {
                if (Restaurant.getRestaurants().get(index).getFoodCategories().get(Restaurant.getRestaurants().get(index).getFoods().get(i)).equals("dessert")) {
                    System.out.println(Restaurant.getRestaurants().get(index).getFoods().get(i) + " | price=" + Restaurant.getRestaurants().get(index).getFoodPrices().get(Restaurant.getRestaurants().get(index).getFoods().get(i)));
                }
            }
        }
    }

    public String addToCart(String restaurantName, String foodName) {
        if (Restaurant.getRestaurantByUsername(restaurantName) == null) {
            return "add to cart failed: restaurant not found";
        }
        if (!Restaurant.getRestaurantByUsername(restaurantName).getFoods().contains(foodName)) {
            return "add to cart failed: food not found";
        }
        // add number to previous added
        for (int i = 0; i < loggedInCustomer.getCartRestaurants().size(); i++) {
            if (loggedInCustomer.getCartRestaurants().get(i).equals(restaurantName)) {
                if (loggedInCustomer.getCartFoods().size() > i && loggedInCustomer.getCartFoods().get(i).equals(foodName)) {
                    int number = loggedInCustomer.getCartNumber().get(i);
                    loggedInCustomer.getCartNumber().remove(i);
                    loggedInCustomer.getCartNumber().add(i, (number + 1));
                    return "add to cart successful";
                }
            }
        }
        //add number to new one
        loggedInCustomer.getCartFoods().add(foodName);
        loggedInCustomer.getCartRestaurants().add(restaurantName);
        loggedInCustomer.getCartNumber().add(1);
        return "add to cart successful";
    }

    public String addToCartWithNumber(String restaurantName, String foodName, String number) {
        if (Restaurant.getRestaurantByUsername(restaurantName) == null) {
            return "add to cart failed: restaurant not found";
        }
        if (!Restaurant.getRestaurantByUsername(restaurantName).getFoods().contains(foodName)) {
            return "add to cart failed: food not found";
        }
        if (Integer.parseInt(number) <= 0) {
            return "add to cart failed: invalid number";
        }
        // add number to previous added
        for (int i = 0; i < loggedInCustomer.getCartRestaurants().size(); i++) {
            if (loggedInCustomer.getCartRestaurants().get(i).equals(restaurantName)) {
                if (loggedInCustomer.getCartFoods().size() > i && loggedInCustomer.getCartFoods().get(i).equals(foodName)) {
                    int previousNumber = loggedInCustomer.getCartNumber().get(i);
                    previousNumber += Integer.parseInt(number);
                    loggedInCustomer.getCartNumber().remove(i);
                    loggedInCustomer.getCartNumber().add(i, previousNumber);
                    return "add to cart successful";
                }
            }
        }
        //add number to new one
        loggedInCustomer.getCartFoods().add(foodName);
        loggedInCustomer.getCartRestaurants().add(restaurantName);
        loggedInCustomer.getCartNumber().add(Integer.parseInt(number));
        return "add to cart successful";
    }

    public String removeFromCart(String restaurantName, String foodName) {
        for (int i = 0; i < loggedInCustomer.getCartRestaurants().size(); i++) {
            if (loggedInCustomer.getCartRestaurants().get(i).equals(restaurantName)) {
                if (loggedInCustomer.getCartFoods().size() > i && loggedInCustomer.getCartFoods().get(i).equals(foodName)) {
                    int previousNumber = loggedInCustomer.getCartNumber().get(i);
                    if (previousNumber == 0) {
                        return "remove from cart failed: not enough food in cart";
                    }
                    loggedInCustomer.getCartNumber().remove(i);
                    if (previousNumber > 1)
                        loggedInCustomer.getCartNumber().add(i, (previousNumber - 1));
                    return "remove from cart successful";
                }
            }
        }
        return "remove from cart failed: not in cart";
    }

    public String removeFromCartWithNumber(String restaurantName, String foodName, String number) {
        for (int i = 0; i < loggedInCustomer.getCartRestaurants().size(); i++) {
            if (loggedInCustomer.getCartRestaurants().get(i).equals(restaurantName)) {
                if (loggedInCustomer.getCartFoods().size() > i && loggedInCustomer.getCartFoods().get(i).equals(foodName)) {
                    if (Integer.parseInt(number) <= 0) {
                        return "remove from cart failed: invalid number";
                    }
                    int previousNumber = loggedInCustomer.getCartNumber().get(i);
                    if (previousNumber - Integer.parseInt(number) < 0) {
                        return "remove from cart failed: not enough food in cart";
                    }
                    loggedInCustomer.getCartNumber().remove(i);
                    if (previousNumber - Integer.parseInt(number) > 0)
                        loggedInCustomer.getCartNumber().add(i, (previousNumber - Integer.parseInt(number)));
                    return "remove from cart successful";
                }
            }
        }
        return "remove from cart failed: not in cart";
    }

    public void showCart() {
        int totalPrice = 0;
        for (int i = 0; i < loggedInCustomer.getCartFoods().size(); i++) {
            int price = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodPrices().get(loggedInCustomer.getCartFoods().get(i)));
            System.out.println((i + 1) + ") " + loggedInCustomer.getCartFoods().get(i) + " | restaurant=" + loggedInCustomer.getCartRestaurants().get(i) + " price=" + price);
            totalPrice += price;
        }
        System.out.println("Total: " + totalPrice);
    }

    public void showDiscountsForCustomer() {
        Set<String> keySet = loggedInCustomer.getDiscounts().keySet();
        String[] keyArray = keySet.toArray(new String[keySet.size()]);
        for (int i = 0; i < keyArray.length; i++) {
            System.out.println((i + 1) + ") " + keyArray[i] + " | amount=" + loggedInCustomer.getDiscounts().get(keyArray[i]));
        }
    }

    public String purchaseCart() {
        int totalPrice = 0;
        for (int i = 0; i < loggedInCustomer.getCartFoods().size(); i++) {
            int price = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodPrices().get(loggedInCustomer.getCartFoods().get(i)));
            totalPrice += price;
        }
        if (totalPrice > loggedInCustomer.getBalance()) {
            return "purchase failed: inadequate money";
        }
        int previousBalance = loggedInCustomer.getBalance();
        loggedInCustomer.setBalance(previousBalance - totalPrice);
        for (int i = 0; i < loggedInCustomer.getCartFoods().size(); i++) {
            int cost = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodCosts().get(loggedInCustomer.getCartFoods().get(i)));
            int price = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodPrices().get(loggedInCustomer.getCartFoods().get(i)));
            int previousBalanceRestaurant = Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getBalance();
            Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).setBalance(previousBalanceRestaurant + price - cost);
        }
        loggedInCustomer.getCartNumber().clear();
        loggedInCustomer.getCartRestaurants().clear();
        loggedInCustomer.getCartFoods().clear();
        return "purchase successful";
    }

    public String purchaseCartWithDiscount(String discount) {
        if (loggedInCustomer.getDiscounts().get(discount) == null) {
            return "purchase failed: invalid discount code";
        }
        int discountAmount = loggedInCustomer.getDiscounts().get(discount);
        int totalPrice = 0;
        for (int i = 0; i < loggedInCustomer.getCartFoods().size(); i++) {
            int price = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodPrices().get(loggedInCustomer.getCartFoods().get(i)));
            totalPrice += price;
        }
        if (totalPrice > (loggedInCustomer.getBalance() + discountAmount)) {
            return "purchase failed: inadequate money";
        }
        int previousBalance = loggedInCustomer.getBalance();
        if (discountAmount < totalPrice)
            loggedInCustomer.setBalance(previousBalance + discountAmount - totalPrice);
        for (int i = 0; i < loggedInCustomer.getCartFoods().size(); i++) {
            int cost = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodCosts().get(loggedInCustomer.getCartFoods().get(i)));
            int price = loggedInCustomer.getCartNumber().get(i) * (Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getFoodPrices().get(loggedInCustomer.getCartFoods().get(i)));
            int previousBalanceRestaurant = Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).getBalance();
            Restaurant.getRestaurantByUsername(loggedInCustomer.getCartRestaurants().get(i)).setBalance(previousBalanceRestaurant + price - cost);
        }
        loggedInCustomer.getCartNumber().clear();
        loggedInCustomer.getCartRestaurants().clear();
        loggedInCustomer.getCartFoods().clear();
        loggedInCustomer.getDiscounts().remove(discount);
        return "purchase successful";
    }

    public String chargeAccountForCustomer(String amount) {
        if (Integer.parseInt(amount) <= 0)
            return "charge account failed: invalid cost or price";
        int balance = loggedInCustomer.getBalance();
        loggedInCustomer.setBalance(balance + Integer.parseInt(amount));
        return "charge account successful";
    }

    public Integer showBalanceForCustomer() {
        return loggedInCustomer.getBalance();
    }
}