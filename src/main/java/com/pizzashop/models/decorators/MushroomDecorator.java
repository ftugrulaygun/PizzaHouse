package com.pizzashop.models.decorators;
import com.pizzashop.models.PizzaDecorator;
import com.pizzashop.models.Pizza;

public class MushroomDecorator extends PizzaDecorator{
    public MushroomDecorator(Pizza pizza){
        super(pizza);
    }

    @Override
    public double getCost(){
        return decoratedPizza.getCost() + 1.50;
    }

    @Override
    public String getDescription(){
        return decoratedPizza.getDescription() + ", Mushroom";
    }
}
