package com.pizzashop;

import com.pizzashop.models.Pizza;
import com.pizzashop.models.BasePizza;
import com.pizzashop.models.decorators.CheeseDecorator;
import com.pizzashop.models.decorators.MushroomDecorator;
import com.pizzashop.models.decorators.OliveDecorator;
import com.pizzashop.models.decorators.PepperoniDecorator;
import com.pizzashop.database.DatabaseManager;
import com.pizzashop.database.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Pizza pizza = new BasePizza();

        pizza = new CheeseDecorator(pizza);
        System.out.println(pizza.getDescription());
        System.out.println("Cost: $" + pizza.getCost());

        DatabaseManager db = new DatabaseManager();

        db.initializeTables();

        db.addIngredient("Mozzarella Cheese", 2.50, 1000, "grams");
        db.addIngredient("Pepperoni", 3.00, 500, "slices");
        db.addIngredient("Flour", 0.50, 5000, "grams");

        var inventory = db.getAllIngredients();

        for(Ingredient item: inventory){
            System.out.println(item.getName() + " - " + item.getQuantity());
        }

        boolean check = db.isIngredientAvailable("Mozzarella Cheese", 700);
        System.out.println(check);
    }
}
