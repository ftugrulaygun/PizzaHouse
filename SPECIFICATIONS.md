# Pizza Decorator System - Technical Specifications

## Project Overview
Build a Pizza ordering system that demonstrates the **Decorator Design Pattern** with a simple UI and database integration. The system allows users to create custom pizzas by adding ingredients, select from preset pizzas, and manage inventory.

** Don't forget to edit the README.md file after every issue to describe the basics of your project and how to run it. **
---

## Required Technologies

### 1. Java Version
- **Requirement**: Java 11 or higher
- **Why**: Modern language features and better support for libraries

### 2. Database
- **Library**: SQLite with JDBC
- **Package**: `org.xerial:sqlite-jdbc:3.44.1.0` (Maven) or `org.xerial.sqlite-jdbc` (Gradle)
- **Why**: 
  - Lightweight, no server setup needed
  - Perfect for learning database concepts
  - Single file database (easy to share and test)
  - Full SQL support

### 3. UI Framework
- **Library**: Java Swing
- **Package**: Built into Java (javax.swing)
- **Why**:
  - No external dependencies needed
  - Simple to learn and use
  - Good for basic desktop applications
  - Visual designers available in most IDEs

### 4. Build Tool
- **Recommended**: Maven or Gradle
- **Why**: Manages dependencies and builds automatically

---

## Database Schema

### Table 1: `ingredients`
Stores all available ingredients and their inventory.

```sql
CREATE TABLE ingredients (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    price DECIMAL(10, 2) NOT NULL,
    quantity INTEGER NOT NULL,
    unit TEXT NOT NULL  -- e.g., "grams", "slices", "pieces"
);
```

**Example Data:**
```sql
INSERT INTO ingredients (name, price, quantity, unit) VALUES 
('Mozzarella Cheese', 2.50, 1000, 'grams'),
('Pepperoni', 3.00, 500, 'slices'),
('Mushrooms', 1.50, 300, 'grams'),
('Tomato Sauce', 1.00, 2000, 'ml');
```

### Table 2: `preset_pizzas`
Stores preset pizza configurations.

```sql
CREATE TABLE preset_pizzas (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    name TEXT NOT NULL UNIQUE,
    description TEXT
);
```

**Example Data:**
```sql
INSERT INTO preset_pizzas (name, description) VALUES 
('Margherita', 'Classic pizza with tomato sauce and mozzarella'),
('Pepperoni', 'Tomato sauce, mozzarella, and pepperoni'),
('Veggie Supreme', 'Loaded with mushrooms, peppers, and olives');
```

### Table 3: `preset_pizza_ingredients`
Links preset pizzas to their ingredients.

```sql
CREATE TABLE preset_pizza_ingredients (
    preset_pizza_id INTEGER,
    ingredient_id INTEGER,
    quantity INTEGER NOT NULL,  -- How much of this ingredient
    FOREIGN KEY (preset_pizza_id) REFERENCES preset_pizzas(id),
    FOREIGN KEY (ingredient_id) REFERENCES ingredients(id),
    PRIMARY KEY (preset_pizza_id, ingredient_id)
);
```

---

## Design Pattern: Decorator Pattern

### What is the Decorator Pattern?
The Decorator pattern allows you to **add new features to an object dynamically** without changing its original code. Think of it like adding toppings to a pizza - each topping "wraps" the pizza and adds its own cost and description.

### Why Use It Here?
- Each ingredient is a "decorator" that adds to the base pizza
- You can stack multiple decorators (ingredients) in any order
- Each decorator adds its own price and description
- The base pizza stays unchanged

### Class Structure

```
Pizza (interface/abstract class)
├── BasePizza (concrete - plain dough)
│
└── PizzaDecorator (abstract decorator)
    ├── CheeseDecorator
    ├── PepperoniDecorator
    ├── MushroomDecorator
    └── ... (more ingredient decorators)
```

---

## Class Design Details

### 1. Core Pizza Classes

#### `Pizza` (Abstract Class or Interface)
```java
public abstract class Pizza {
    protected String description = "Unknown Pizza";
    
    public String getDescription() {
        return description;
    }
    
    public abstract double getCost();
}
```

**Purpose**: Defines what all pizzas must have - a description and a cost.

---

#### `BasePizza` (Concrete Class)
```java
public class BasePizza extends Pizza {
    public BasePizza() {
        description = "Plain Pizza Dough";
    }
    
    @Override
    public double getCost() {
        return 5.00;  // Base price
    }
}
```

**Purpose**: The starting point - a plain pizza dough that we'll add ingredients to.

---

### 2. Decorator Classes

#### `PizzaDecorator` (Abstract Decorator)
```java
public abstract class PizzaDecorator extends Pizza {
    protected Pizza decoratedPizza;
    
    public PizzaDecorator(Pizza pizza) {
        this.decoratedPizza = pizza;
    }
    
    @Override
    public abstract String getDescription();
}
```

**Purpose**: The base for all ingredient decorators. It "wraps" another Pizza object.

---

#### Concrete Decorators (Examples)

**CheeseDecorator:**
```java
public class CheeseDecorator extends PizzaDecorator {
    public CheeseDecorator(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", Mozzarella Cheese";
    }
    
    @Override
    public double getCost() {
        return decoratedPizza.getCost() + 2.50;
    }
}
```

**PepperoniDecorator:**
```java
public class PepperoniDecorator extends PizzaDecorator {
    public PepperoniDecorator(Pizza pizza) {
        super(pizza);
    }
    
    @Override
    public String getDescription() {
        return decoratedPizza.getDescription() + ", Pepperoni";
    }
    
    @Override
    public double getCost() {
        return decoratedPizza.getCost() + 3.00;
    }
}
```

**How It Works:**
```java
// Start with base pizza
Pizza myPizza = new BasePizza();  // Cost: $5.00

// Add cheese
myPizza = new CheeseDecorator(myPizza);  // Cost: $7.50

// Add pepperoni
myPizza = new PepperoniDecorator(myPizza);  // Cost: $10.50

System.out.println(myPizza.getDescription());  
// Output: "Plain Pizza Dough, Mozzarella Cheese, Pepperoni"

System.out.println("Total: $" + myPizza.getCost());  
// Output: "Total: $10.50"
```

---

### 3. Database Layer

#### `DatabaseManager` (Class)
Handles all database operations.

**Responsibilities:**
- Connect to SQLite database
- Initialize tables if they don't exist
- CRUD operations for ingredients
- CRUD operations for preset pizzas
- Check ingredient availability
- Deduct ingredients when pizza is ordered

**Example Methods:**
```java
public class DatabaseManager {
    private Connection connection;
    
    // Connect to database
    public void connect(String dbPath);
    
    // Initialize tables
    public void initializeTables();
    
    // Ingredient operations
    public List<Ingredient> getAllIngredients();
    public void addIngredient(String name, double price, int quantity, String unit);
    public void updateIngredientQuantity(int id, int newQuantity);
    public boolean isIngredientAvailable(int id, int requiredQuantity);
    
    // Preset pizza operations
    public List<PresetPizza> getAllPresetPizzas();
    public void addPresetPizza(String name, String description, List<Integer> ingredientIds);
    public PresetPizza getPresetPizzaById(int id);
}
```

---

### 4. Model Classes

#### `Ingredient` (Data Class)
```java
public class Ingredient {
    private int id;
    private String name;
    private double price;
    private int quantity;
    private String unit;
    
    // Constructor, getters, setters
}
```

#### `PresetPizza` (Data Class)
```java
public class PresetPizza {
    private int id;
    private String name;
    private String description;
    private List<Ingredient> ingredients;
    
    // Constructor, getters, setters
}
```

---

### 5. UI Layer

#### `PizzaOrderUI` (Swing GUI)
Main window for the application.

**Components:**
- **Preset Pizza Panel**: Dropdown to select preset pizzas
- **Available Ingredients Panel**: List/checkboxes of ingredients to add
- **Current Pizza Display**: Shows description and running cost
- **Order Button**: Finalizes the order
- **Admin Panel**: Add ingredients/presets (can be separate window)

**Basic Layout:**
```
┌─────────────────────────────────────┐
│        Pizza Order System           │
├─────────────────────────────────────┤
│ Preset Pizzas: [Dropdown         ▼] │
│ [Load Preset]                       │
├─────────────────────────────────────┤
│ Available Ingredients:              │
│ ☐ Mozzarella ($2.50) - 1000g       │
│ ☐ Pepperoni ($3.00) - 500 slices   │
│ ☐ Mushrooms ($1.50) - 300g         │
├─────────────────────────────────────┤
│ Current Pizza:                      │
│ Plain Pizza Dough                   │
│                                     │
│ Total Cost: $5.00                   │
├─────────────────────────────────────┤
│ [Place Order]  [Clear]  [Admin]    │
└─────────────────────────────────────┘
```

---

## Project Structure

```
pizza-decorator/
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── pizzashop/
│                   ├── models/
│                   │   ├── Pizza.java
│                   │   ├── BasePizza.java
│                   │   ├── PizzaDecorator.java
│                   │   ├── decorators/
│                   │   │   ├── CheeseDecorator.java
│                   │   │   ├── PepperoniDecorator.java
│                   │   │   └── ... (other ingredient decorators)
│                   │   ├── Ingredient.java
│                   │   └── PresetPizza.java
│                   ├── database/
│                   │   └── DatabaseManager.java
│                   ├── ui/
│                   │   ├── PizzaOrderUI.java
│                   │   └── AdminPanel.java
│                   └── Main.java
├── database/
│   └── pizza.db (SQLite database file)
├── pom.xml (if using Maven)
└── README.md
```

---

## Key Learning Objectives

1. **Decorator Pattern**: Understanding how to extend functionality dynamically
2. **Object-Oriented Programming**: Inheritance, polymorphism, abstraction
3. **Database Integration**: CRUD operations, SQL queries
4. **GUI Development**: Basic Swing components and event handling
5. **Inventory Management**: Checking availability and updating quantities

---

## Development Workflow

1. **Start Simple**: Create the Pizza and BasePizza classes first
2. **Add Decorators**: Implement 2-3 ingredient decorators
3. **Test Pattern**: Write a simple main method to test decoration
4. **Database Setup**: Create DatabaseManager and initialize tables
5. **Connect Pattern to DB**: Load ingredients from database to create decorators
6. **Build UI**: Create basic Swing interface
7. **Add Features**: Preset pizzas, admin panel, inventory checks
8. **Polish**: Error handling, validation, better UI

---

## Testing Suggestions

### Manual Testing
1. Create a pizza with multiple ingredients
2. Verify the total cost is calculated correctly
3. Try to order a pizza when ingredient is out of stock
4. Add a new ingredient through admin panel
5. Load a preset pizza and modify it

### Code Testing
Create a simple test in `Main.java`:
```java
public static void main(String[] args) {
    // Test the decorator pattern
    Pizza pizza = new BasePizza();
    System.out.println(pizza.getDescription() + " $" + pizza.getCost());
    
    pizza = new CheeseDecorator(pizza);
    System.out.println(pizza.getDescription() + " $" + pizza.getCost());
    
    pizza = new PepperoniDecorator(pizza);
    System.out.println(pizza.getDescription() + " $" + pizza.getCost());
}
```

Expected output:
```
Plain Pizza Dough $5.0
Plain Pizza Dough, Mozzarella Cheese $7.5
Plain Pizza Dough, Mozzarella Cheese, Pepperoni $10.5
```

---

## Additional Notes

- Keep the code simple and well-commented
- Focus on understanding the pattern first, then add features
- Each class should have a single responsibility
- Use meaningful variable and method names
- Handle errors gracefully (e.g., database connection failures)
