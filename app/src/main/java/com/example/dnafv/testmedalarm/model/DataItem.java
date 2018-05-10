package com.example.dnafv.testmedalarm.model;

import java.util.UUID;

/** Example of DataItem Class that is used to populate the app with data */

public class DataItem {

    /** These are the variables that hold the data being entered into the app */
    //This will be the PK
    //We will also be generating UUID's for unique id's for objects and their instances - easier to
    // retrieve & sync data
    private String itemId;
    private String Name;
    private String description;
    private String Category;
    private int sortPosition;
    private double price;
    private String image;

    /** Create 2 constructor methods:
     * 1. One that receives no arguments
     * 2. One that receives an arg for each private field
     * */

    //Empty constructor
    public DataItem() {
    }

    /**Full constructor -
     * that will receive args for each of the fields
     * & saves the arg values to each field
     */

    public DataItem(String itemId, String name, String description, String category, int sortPosition, double price, String image) {

        //If we are creating a new object and it doesn't have a UUID (Universal Unique Identifier) then create one now.
        if(itemId == null){
            itemId = UUID.randomUUID().toString();
        }

        this.itemId = itemId;
        Name = name;
        this.description = description;
        Category = category;
        this.sortPosition = sortPosition;
        this.price = price;
        this.image = image;
    }

    /**
     * Next Add Getters & Setters
     * allowing us to get(retrieve) and set() values from the rest of the app
     *
     * */

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return Category;
    }

    public void setCategory(String category) {
        Category = category;
    }

    public int getSortPosition() {
        return sortPosition;
    }

    public void setSortPosition(int sortPosition) {
        this.sortPosition = sortPosition;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    /**
     * Add an Implementation of the StringTo Method
     * which returns STRING which reflects all the current values for the current instance of the class
     * ie; a dataItem
     * */
    @Override
    public String toString() {
        return "DataItem{" +
                "itemId='" + itemId + '\'' +
                ", Name='" + Name + '\'' +
                ", description='" + description + '\'' +
                ", Category='" + Category + '\'' +
                ", sortPosition=" + sortPosition +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}
