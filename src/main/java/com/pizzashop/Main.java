package com.pizzashop;

import com.pizzashop.models.Pizza;
import com.pizzashop.models.BasePizza;
import com.pizzashop.models.decorators.CheeseDecorator;
import com.pizzashop.models.decorators.MushroomDecorator;
import com.pizzashop.models.decorators.OliveDecorator;
import com.pizzashop.models.decorators.PepperoniDecorator;

public class Main{
    public static void main(String[] args){
        Pizza pizza = new BasePizza();

        pizza = new CheeseDecorator(pizza);
        System.out.println(pizza.getDescription());
        System.out.println("Cost: $" + pizza.getCost());
    }
}
