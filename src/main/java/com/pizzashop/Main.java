package com.pizzashop;

import com.pizzashop.database.DatabaseManager;
import com.pizzashop.database.Ingredient;
import com.pizzashop.models.BasePizza;
import com.pizzashop.models.Pizza;
import com.pizzashop.models.decorators.IngredientDecorator;

public class Main {
    public static void main(String[] args) {
        DatabaseManager db = new DatabaseManager();

        // 1. Setup the Database Tables
        System.out.println("--- Initializing Database ---");
        db.initializeTables();

        // 2. Add Test Ingredients (using your addIngredient method)
        // Values: Name, Price, Quantity, Unit
        db.addIngredient("Mozzarella", 2.50, 100, "grams");
        db.addIngredient("Pepperoni", 3.00, 50, "slices");
        db.addIngredient("Olives", 1.50, 200, "grams");

        System.out.println("\n--- Testing Dynamic Decorators ---");

        // 3. Create a Base Pizza
        Pizza myPizza = new BasePizza(); // Assume base price is 10.0
        System.out.println("Base: " + myPizza.getDescription() + " | Cost: $" + myPizza.getCost());

        // 4. Decorate using the Name-based Overload
        // This will trigger your "SELECT price FROM ingredients WHERE name = ?" logic
        

// Now you can call the static factory method directly:
        myPizza = IngredientDecorator.createAndEditPizza(myPizza, "Mozzarella");
        myPizza = IngredientDecorator.createAndEditPizza(myPizza, "Pepperoni"); // Ingredient obj can be null if methods use name/id
        
        System.out.println(myPizza.getDescription());
        System.out.println(myPizza.getCost());

        // 5. Decorate using the ID-based Overload (Assuming ID 2 is Pepperoni)
    }
}
