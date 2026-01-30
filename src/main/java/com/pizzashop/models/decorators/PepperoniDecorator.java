package com.pizzashop.models.decorators;
import com.pizzashop.models.PizzaDecorator;
import com.pizzashop.models.Pizza;

public class PepperoniDecorator extends PizzaDecorator{
    public PepperoniDecorator(Pizza pizza){
        super(pizza);
    }

    @Override
    public String getDescription(){
        return decoratedPizza.getDescription() + ", Pepperoni";
    }

    @Override
    public double getCost(){
        return decoratedPizza.getCost() + 3.00;
    }
}