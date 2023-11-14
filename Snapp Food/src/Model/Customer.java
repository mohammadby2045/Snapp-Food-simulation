package Model;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class Customer {

    private static final ArrayList<Customer> customers = new ArrayList<>();
    private int balance;
    private final String password;
    private final String username;
    private final LinkedHashMap<String, Integer> discounts ;
    private final ArrayList<String> cartRestaurants;
    private final ArrayList<String> cartFoods;
    private final ArrayList<Integer> cartNumber;

    public Customer(int balance, String password, String username) {
        this.balance = balance;
        this.password = password;
        this.username = username;
        this.cartFoods = new ArrayList<>();
        this.cartNumber = new ArrayList<>();
        this.cartRestaurants = new ArrayList<>();
        this.discounts = new LinkedHashMap<>();
    }

    public static Customer getCustomerByUsername(String username) {
        for (Customer customer : customers) {
            if (customer.getUsername().equals(username))
                return customer;
        }
        return null;
    }

    public static void addCustomer(String username, String password) {
        customers.add(new Customer(0,password, username));
    }

    public void addDiscount(String code , int amount){
        this.discounts.put(code,amount);
    }
    public static void removeCustomer(String username){
        customers.remove(getCustomerByUsername(username));
    }

    public int getBalance() {
        return balance;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }

    public LinkedHashMap<String, Integer> getDiscounts() {
        return discounts;
    }

    public ArrayList<String> getCartRestaurants() {
        return cartRestaurants;
    }

    public ArrayList<String> getCartFoods() {
        return cartFoods;
    }

    public ArrayList<Integer> getCartNumber() {
        return cartNumber;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public boolean isPasswordCorrect(String password) {
        return this.getPassword().equals(password);
    }

}
