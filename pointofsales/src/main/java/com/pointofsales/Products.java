package com.pointofsales;

/**
 * Products.java - Constructor class containing informations of products.
 *
 * @author Rohan
 * @version 1.0
 */
public class Products extends PointOfSales {

    /**
     * Instance variables
     */
    private String name;
    private int productID;
    private double price;
    private String weight;
    private int calories;
    private int quantity;
    private String category;

    /**
     * Default Constructor.
     */
    public Products() {
        this.name = " ";
        this.productID = 0;
        this.price = 0.0;
        this.weight = " ";
        this.calories = 0;
        this.quantity = 0;
        this.category = " ";
    }

    /**
     * Second constructor.
     *
     * @param name A variable of type String.
     * @param productID A variable of type Integer.
     * @param price A variable of type Double.
     * @param weight A variable of type String.
     * @param calories A variable of type Integer.
     * @param quantity A variable of type Integer.
     * @param category A variable of type String.
     */
    public Products(String name, int productID, double price, String weight, int calories, int quantity, String category) {
        this.name = name;
        this.productID = productID;
        this.price = price;
        this.weight = weight;
        this.calories = calories;
        this.quantity = quantity;
        this.category = category;
    }
    
    /**
     * Retrieve the value of name.
     *
     * @return A string data type.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the value of name.
     *
     * @param name A variable of type String.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Retrieve the value of productID.
     *
     * @return An Integer data type.
     */
    public int getProductID() {
        return this.productID;
    }

    /**
     * Set the value of productID.
     *
     * @param productID A variable of type Integer.
     */
    public void setProductID(int productID) {
        this.productID = productID;
    }

    /**
     * Retrieve the value of price.
     *
     * @return A Double data type.
     */
    public double getPrice() {
        return this.price;
    }

    /**
     * Set the value of price.
     *
     * @param price A variable of type Double.
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Retrieve the value of weight.
     *
     * @return A String data type.
     */
    public String getWeight() {
        return this.weight;
    }

    /**
     * Set the value of weight.
     *
     * @param weight A variable of type String.
     */
    public void setWeight(String weight) {
        this.weight = weight;
    }

    /**
     * Retrieve the value of calories.
     *
     * @return An Integer data type.
     */
    public int getCalories() {
        return this.calories;
    }

    /**
     * Set the value of calories.
     *
     * @param calories A variable of type Integer.
     */
    public void setCalories(int calories) {
        this.calories = calories;
    }

    /**
     * Retrieve the value of quantity.
     *
     * @return An Integer data type.
     */
    public int getQuantity() {
        return this.quantity;
    }

    /**
     * Set the value of quantity.
     *
     * @param quantity A variable of type Integer.
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Retrieve the value of category.
     *
     * @return A String data type.
     */
    public String getCategory() {
        return this.category;
    }

    /**
     * Set the value of category.
     *
     * @param category A variable of type String.
     */
    public void setCategory(String category) {
        this.category = category;
    }
}
