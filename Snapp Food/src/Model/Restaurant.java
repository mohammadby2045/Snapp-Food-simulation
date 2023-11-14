package Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Restaurant {
    private static final ArrayList<Restaurant> restaurants = new ArrayList<>();
    private int balance;
    private final String name;
    private final ArrayList<String> foods;
    private final HashMap<String, String> foodCategories;
    private final HashMap<String, Integer> foodPrices;
    private final HashMap<String, Integer> foodCosts;
    private final String type;

    public Restaurant(int balance, String name, String type) {
        this.balance = balance;
        this.name = name;
        this.type = type;
        this.foodCategories = new HashMap<>();
        this.foodCosts = new HashMap<>();
        this.foodPrices = new HashMap<>();
        this.foods = new ArrayList<>();
    }


    public static Restaurant getRestaurantByUsername(String name) {
        for (Restaurant restaurant : restaurants) {
            if (restaurant.getName().equals(name))
                return restaurant;
        }
        return null;
    }

    public static ArrayList<Restaurant> getRestaurants() {
        return restaurants;
    }

    public static void removeRestaurant(String username) {
        restaurants.remove(getRestaurantByUsername(username));
    }

    public ArrayList<String> getFoods() {
        return foods;
    }

    public HashMap<String, String> getFoodCategories() {
        return foodCategories;
    }

    public HashMap<String, Integer> getFoodPrices() {
        return foodPrices;
    }

    public HashMap<String, Integer> getFoodCosts() {
        return foodCosts;
    }

    public static void addRestaurant(String name, String type, int balance) {
        restaurants.add(new Restaurant(balance, name, type));
    }

    public static void addFood( Restaurant restaurant,String name , String category , int price, int cost){
        restaurant.getFoods().add(name);
        restaurant.getFoodCosts().put(name,cost);
        restaurant.getFoodCategories().put(name,category);
        restaurant.getFoodPrices().put(name,price);
    }

    public static void removeFood(Restaurant restaurant, String name){
        restaurant.getFoods().remove(name);
        restaurant.getFoodPrices().remove(name);
        restaurant.getFoodCategories().remove(name);
        restaurant.getFoodCosts().remove(name);
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

}
