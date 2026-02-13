package com.pizzashop.models;

public class BasePizza extends Pizza{
    public BasePizza() {
        description = "Plain Pizza Dough";
    }

    @Override
    public double getCost(){
        return 5.00;
    }

    
}

