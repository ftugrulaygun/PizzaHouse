package com.pizzashop.models;

public abstract class PizzaDecorator extends Pizza{
    protected Pizza decoratedPizza;

    public PizzaDecorator(Pizza pizza){
        this.decoratedPizza = pizza;
    }

    @Override
    public abstract String getDescription();
}

