package com.pizzashop;

import com.pizzashop.database.DatabaseManager;
import com.pizzashop.models.BasePizza;
import com.pizzashop.models.Pizza;
import com.pizzashop.models.decorators.IngredientDecorator;
import com.pizzashop.ui.PizzaOrderUI;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();

        System.out.println("--- Initializing Database ---");
        db.initializeTables();

        db.addIngredient("Mozzarella", 2.50, 100, "grams");
        db.addIngredient("Pepperoni", 3.00, 50, "slices");
        db.addIngredient("Olives", 1.50, 200, "grams");

        System.out.println("\n--- Testing Dynamic Decorators ---");

        
        Pizza myPizza = new BasePizza();
        Pizza myPizza2 = new BasePizza();
        System.out.println("Base: " + myPizza.getDescription() + " | Cost: $" + myPizza.getCost());
        System.out.println("Base2: " + myPizza2.getDescription() + " | Cost: $" + myPizza2.getCost());
        

        
        myPizza = IngredientDecorator.createAndEditPizza(myPizza, "Mozzarella", 70);
        myPizza2 = IngredientDecorator.createAndEditPizza(myPizza2, "Mozzarella", 60); 
        
        System.out.println(myPizza.getDescription());
        System.out.println(myPizza.getCost());
        System.out.println(myPizza2.getDescription());
        System.out.println(myPizza2.getCost());

        PizzaOrderUI frame = new PizzaOrderUI();

        frame.displayIngredients();

        frame.setVisible(true);



        



        
    }
}
