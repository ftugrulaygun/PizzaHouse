package com.pizzashop.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DriverPropertyInfo;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager{
    private Connection connection;

    public void addIngredient(String name, double prive, int quantity, String unit, String DBpath){
        String sql = "INSERT INTO ingredients (name, price, quantity, unit) VALUES " +
                    "('Mozzarella Cheese', 2.50, 1000, 'grams')," +
                    "('Pepperoni', 3.00, 500, 'slices')," +
                    "('Mushrooms', 1.50, 300, 'grams')," +
                    "('Bell Peppers', 1.20, 400, 'grams')," +
                    "('Olives', 1.80, 250, 'grams');";

        String url = "jdbc:sqlite" + DBpath;
        
        try(Connection connection = DriverManager.getConnection(url); Statement stmt = connection.createStatement()){
            stmt.execute(sql);
        }catch(SQLException exception){
            System.out.println("Error adding " + name + ": " + exception.getMessage());
        }
    }

    public void connect(String dbPath){
        String url = "jdbc:sqlite" + dbPath;
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
                            

        try{
            this.connection = DriverManager.getConnection(url);

            System.out.println("Successfully connected to: " + dbPath);

            Statement stmt = connection.createStatement();

            if(connection != null){
                stmt.execute(sql);
            }
        }catch(SQLException exception){
            System.out.println("Connection failed" + exception.getMessage());
        }finally{
            try{
                if(this.connection != null){
                    this.connection.close();
                }
            }catch(SQLException exception2){
                    System.out.println("Error closing connection: " + exception2.getMessage());
                }
            }
        }
    }


    


