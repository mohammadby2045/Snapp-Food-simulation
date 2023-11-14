package Model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;

public class AdminSnappFood {
    private static final ArrayList<AdminSnappFood> adminSnappFood = new ArrayList<>();
    private static final LinkedHashMap<String, String> discountsusername = new LinkedHashMap<>();
    private static final LinkedHashMap<String, String> discountsamount = new LinkedHashMap<>();
    private final String password;
    private final String username;

    public AdminSnappFood(String password, String username) {
        this.password = password;
        this.username = username;
    }

    public String getPassword() {
        return password;
    }


    public String getUsername() {
        return username;
    }

    public static void addDiscount(String code, String amount, String username) {
        discountsusername.put(code, username);
        discountsamount.put(code, amount);
    }

    public static LinkedHashMap<String, String> getDiscountsusername() {
        return discountsusername;
    }

    public static LinkedHashMap<String, String> getDiscountsamount() {
        return discountsamount;
    }

    public static AdminSnappFood getAdminSFByUsername(String username) {
        if (adminSnappFood.get(0).getUsername().equals(username))
            return adminSnappFood.get(0);
        return null;
    }

    public static void addAdminSF(String username, String password) {
        adminSnappFood.add(new AdminSnappFood(username, password));
    }

    public static void removeAdminSF(String username) {
        adminSnappFood.remove(getAdminSFByUsername(username));
    }


    public boolean isPasswordCorrect(String password) {
        return this.getPassword().equals(password);
    }
}
