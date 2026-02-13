package com.pizzashop.models;

public abstract class Pizza{
    protected String description;

    public String getDescription(){
        return description;
    }
    public abstract double getCost();

    public String getDescription(int id) { 
        return getDescription(); 
    }
    public String getDescription(String name) {
         return getDescription(); 
    }

    public double getCost(int id) { 
        return getCost(); 
    }
    public double getCost(String name) { 
        return getCost(); 
    }



    
}

