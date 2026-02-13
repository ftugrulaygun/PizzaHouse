# PizzaHouse

A Java-based pizza ordering application that demonstrates the Decorator Design Pattern with database integration and a graphical user interface.


# Prerequisites

Ensure these following are installed:

JDK Compiler Version: 25.0.2 (https://www.oracle.com/tr/java/technologies/downloads/) 

Java Version: 25.0.2 

Maven Version: 3.9.12 (https://maven.apache.org/download.cgi)

# Setup and Installation

**Clone the repository or download the files**

**Install Dependencies**

## How to run/test

You can test the code by running the `Main.java` file.

Use the example code below to test both the **Pizza Decorator** pattern and the **Database Manager**. You can choose specific functions to run in your `main` method:

```java
import com.pizzashop.database.DatabaseManager;
import com.pizzashop.database.Ingredient;
import java.util.List;

// --- Part 1: Pizza Decorator Pattern ---

// Define a new Pizza (assuming BasePizza or similar exists)
 

myPizza = IngredientDecorator.createAndEditPizza(myPizza, "Mozzarella");
myPizza = IngredientDecorator.createAndEditPizza(myPizza, "Pepperoni");


// --- Part 2: Database Management ---

// 1. Initialize the Database Manager
DatabaseManager db = new DatabaseManager();
db.initializeTables();

// 2. Add Ingredients (Name, Price, Quantity, Unit)
System.out.println("--- Adding Ingredients ---");
db.addIngredient("Mozzarella Cheese", 2.50, 1000, "grams");
db.addIngredient("Tomato Sauce", 1.20, 500, "ml");
db.addIngredient("Pepperoni", 3.00, 50, "slices");

// 3. Check Stock Levels
System.out.println("\n--- Checking Availability ---");
boolean hasCheese = db.isIngredientAvailable("Mozzarella Cheese", 200);
System.out.println("Is there enough cheese for a pizza (200g)? " + (hasCheese ? "Yes" : "No"));

// 4. List All Ingredients
System.out.println("\n--- Current Inventory ---");
List<Ingredient> inventory = db.getAllIngredients();
for (Ingredient item : inventory) {
    System.out.printf("%s: %d %s ($%.2f)%n", 
        item.getName(), item.getQuantity(), item.getUnit(), item.getPrice());
}
```



