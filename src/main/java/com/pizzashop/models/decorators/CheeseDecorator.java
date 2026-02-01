package com.pizzashop.models.decorators;
import com.pizzashop.models.PizzaDecorator;
import com.pizzashop.models.Pizza;

public class CheeseDecorator extends PizzaDecorator{
    public CheeseDecorator(Pizza pizza){
        super(pizza);
    }

    @Override
    public String getDescription(){
        return decoratedPizza.getDescription() + ", Mozzeralla Cheese";
    }

    @Override
    public double getCost(){
        return decoratedPizza.getCost() + 2.50;
    }
}

