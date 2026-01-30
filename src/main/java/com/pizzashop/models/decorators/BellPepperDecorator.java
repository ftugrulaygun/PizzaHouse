package com.pizzashop.models.decorators;
import com.pizzashop.models.PizzaDecorator;
import com.pizzashop.models.Pizza;

public class BellPepperDecorator extends PizzaDecorator{
    public BellPepperDecorator(Pizza pizza){
        super(pizza);
    }

    public double getCost(){
        return decoratedPizza.getCost() + 1.20;
    }

    public String getDescription(){
        return decoratedPizza.getDescription() + ", Bell Peppers";
    }
}