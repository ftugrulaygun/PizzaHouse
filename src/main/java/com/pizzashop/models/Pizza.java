package com.pizzashop.models;

public abstract class Pizza{
    protected String description;

    public String getDescription(){
        return description;
    }

    public abstract double getCost();
}