package com.pizzashop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager{
    
    private static final String DB_URL;

    static{
        String projectDir = System.getProperty("user.dir");

        DB_URL = "jdbc:sqlite:" + projectDir + "/database/pizzashop.db";

        System.out.println("Using Database: " + DB_URL);
    }

    public void addIngredient(String name, double price, int quantity, String unit){
        String sql = "INSERT  or IGNORE INTO ingredients (name, price, quantity, unit) VALUES (?,?,?,?)";
        
        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, price);
            stmt.setInt(3, quantity);
            stmt.setString(4, unit);
            stmt.executeUpdate();
            System.out.println("Successfully added: " + name);     
        }catch(SQLException exception){
            System.out.println("Error adding " + name + ": " + exception.getMessage());
        }
    }

    public void connect(String dbPath){
        String url = "jdbc:sqlite:" + dbPath;
        try(Connection connection = DriverManager.getConnection(url)){
            System.out.println("Connection established to: " + url);
        }catch(SQLException exception){
            System.out.println("Connection failed to: " + url);
        }
    }



    
    public List<Ingredient> getAllIngredients(){
        List<Ingredient> ingredientList = new ArrayList<>();
        String sql = "SELECT * FROM ingredients";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            Statement stmt = connection.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            while(rs.next()){
                int id = rs.getInt("id");
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                int quantity = rs.getInt("quantity");
                String unit = rs.getString("unit");

                Ingredient item = new Ingredient(id, name, price, quantity, unit);
                ingredientList.add(item);
            }
        }catch(SQLException exception){
            System.out.println("Error getting all ingredients: " + exception.getMessage());
        }
    return ingredientList;
    }

    public boolean isIngredientAvailable(String name, int requiredQuantity){
        String sql = "SELECT quantity FROM ingredients WHERE name = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, name);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    int quantityInDB = rs.getInt("quantity");
                    return quantityInDB >= requiredQuantity;
                }
            }
        } catch(SQLException exception){
            System.out.println("Error checking ingredient: " + exception.getMessage());
            
        }
    return false;
    }

    public static void updateIngredientQuantity(int id, int newQuantity){
        String sql = "UPDATE ingredients SET quantity = ? WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, newQuantity);
            stmt.setInt(2, id);
            stmt.executeUpdate();
            System.out.println("Quantity updated.");
        }catch(SQLException exception){
            System.out.println("Error updating quantity: " + exception.getMessage());
        }
    }

    public void initializeTables(){
        String sql = """ 
    CREATE TABLE IF NOT EXISTS ingredients (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL UNIQUE,
        price DECIMAL(10, 2) NOT NULL,
        quantity INTEGER NOT NULL,
        unit TEXT NOT NULL
    );
 
    CREATE TABLE IF NOT EXISTS preset_pizzas (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        name TEXT NOT NULL UNIQUE,
        description TEXT
    );

    CREATE TABLE IF NOT EXISTS preset_pizza_ingredients (
        preset_pizza_id INTEGER,
        ingredient_id INTEGER,
        quantity INTEGER NOT NULL,
        FOREIGN KEY (preset_pizza_id) REFERENCES preset_pizzas(id),
        FOREIGN KEY (ingredient_id) REFERENCES ingredients(id),
        PRIMARY KEY (preset_pizza_id, ingredient_id)
    );
    """;
        try(Connection connection = DriverManager.getConnection(DB_URL)){
            Statement stmt = connection.createStatement();
            stmt.executeUpdate(sql);
        }catch(SQLException exception){
            System.out.println("Error connecting to: " + exception.getMessage());
        }
    }
}


    


