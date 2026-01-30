package com.pizzashop.models.decorators;
import com.pizzashop.models.PizzaDecorator;
import com.pizzashop.models.Pizza;

public class OliveDecorator extends PizzaDecorator {
    public OliveDecorator(Pizza pizza){
        super(pizza);
    }

    @Override
    public double getCost(){
        return decoratedPizza.getCost() + 1.00;
    }

    @Override
    public String getDescription(){
        return decoratedPizza.getDescription() + ", Olives";
    }
    
}
