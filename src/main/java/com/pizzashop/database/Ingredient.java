package com.pizzashop.database;

public class Ingredient{
    private final int id;
    private final String name;
    private double price;
    private int quantity;
    private final String unit;
    
    public Ingredient(int id, String name, double price , int quantity, String unit){
        this.id = id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.unit = unit;
    }

        public String getName(){
            return name;
        }
       
        public int getQuantity(){
            return quantity;
        }

        public double getPrice(){
            return price;
        }

        public String getUnit(){
            return unit;
        }

        public int getID(){
            return id;
        }

        public void setQuantity(int appliedQuantity){
            this.quantity = appliedQuantity;
        }
}

