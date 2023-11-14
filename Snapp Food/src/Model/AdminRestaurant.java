package Model;


import java.util.ArrayList;

public class AdminRestaurant {

    private static final ArrayList<AdminRestaurant> adminRestaurants = new ArrayList<>();

    private final String username;
    private final String password;

    public AdminRestaurant(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public static AdminRestaurant getAdminRestaurantByUsername(String username) {
        for (AdminRestaurant Arestaurant : adminRestaurants) {
            if (Arestaurant.getUsername().equals(username))
                return Arestaurant;
        }
        return null;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static void addAdminRestaurant(String userename , String password) {
        adminRestaurants.add(new AdminRestaurant(userename , password));
    }

    public static void removeAdminRestaurant(String username){
        adminRestaurants.remove(getAdminRestaurantByUsername(username));
    }

    public boolean isPasswordCorrect(String password) {
        return this.getPassword().equals(password);
    }
}
