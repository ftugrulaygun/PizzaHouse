package com.pizzashop.models.decorators;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.pizzashop.database.DatabaseManager;
import com.pizzashop.database.Ingredient;
import com.pizzashop.models.Pizza;
import com.pizzashop.models.PizzaDecorator;


public class IngredientDecorator extends PizzaDecorator {
    private Ingredient ingredient;
    private DatabaseManager dbManager;
    private static final String DB_URL;

    static{
        String projectDir = System.getProperty("user.dir");

        DB_URL = "jdbc:sqlite:" + projectDir + "/database/pizzashop.db";

        System.out.println("Using Database: " + DB_URL);
    }

    public IngredientDecorator(Pizza pizza, Ingredient ingredient){
        super(pizza);
        this.ingredient = ingredient;
    }

    private static Ingredient fetchIngredientFromDB(String name){
        String sql = "SELECT * FROM ingredients WHERE name = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, name);
            ResultSet res = stmt.executeQuery();

            if(res.next()){
                return new Ingredient(
                    res.getInt("id"),
                    res.getString("name"),
                    res.getDouble("price"),
                    res.getInt("quantity"),
                    res.getString("unit")
                );
            }
        }catch(SQLException exception){
            System.out.println("Error returning ingredient: " + exception.getMessage());
        }
        return null;
    }

    public static Pizza createAndEditPizza(Pizza pizza, String IngredientName, int requiredAmount){
        Ingredient ingredient = fetchIngredientFromDB(IngredientName);

        if(ingredient != null && ingredient.getQuantity() >= requiredAmount){
            int newQuantity = ingredient.getQuantity() - requiredAmount;

            ingredient.setQuantity(newQuantity);
            int idForIngredient = ingredient.getID();
            DatabaseManager.updateIngredientQuantity(idForIngredient, newQuantity);
            return new IngredientDecorator(pizza, ingredient);
        } else{
            System.out.println("Cannot add " + IngredientName + ": Out of stock");
            return pizza;
        }
    }


    @Override 
    public double getCost(){
        return decoratedPizza.getCost() + this.ingredient.getPrice();
    }
    @Override
    public String getDescription(){
        return decoratedPizza.getDescription() + ", " + this.ingredient.getName();
    }
    
    @Override
    public double getCost(String name){
        String sql = "SELECT price FROM ingredients WHERE name = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setString(1, name);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    double priceInDB = rs.getDouble("price");
                    return decoratedPizza.getCost(name) + priceInDB;
                }
            }
        }catch(SQLException exception){
            System.out.println("Error connecting to table: " + exception.getMessage());
        }
        return decoratedPizza.getCost(name);
    }
    @Override
    public double getCost(int id){
        String sql = "SELECT price FROM ingredients WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);

            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    double priceInDB = rs.getDouble("price");
                    return decoratedPizza.getCost(id) + priceInDB;
                }
            }
        }catch(SQLException exception){
            System.out.println("Error connecting to table: " + exception.getMessage());
        }
        return decoratedPizza.getCost(id);
    }

    @Override
    public String getDescription(int id){
        String sql = "SELECT name FROM ingredients WHERE id = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setInt(1, id);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    String nameInDB = rs.getString("name");
                    return decoratedPizza.getDescription(id) + "," + nameInDB;
                }
            }
            

        }catch(SQLException exception){
            System.out.println("Error connecting to table: " + exception.getMessage());
        }
        return decoratedPizza.getDescription(id);
    }

    @Override
    public String getDescription(String name){
        String sql = "SELECT name FROM ingredients WHERE name   = ?";

        try(Connection connection = DriverManager.getConnection(DB_URL)){
            PreparedStatement stmt = connection.prepareStatement(sql);
            
            stmt.setString(1, name);

            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    String nameInDB = rs.getString("name");
                    return decoratedPizza.getDescription(name) + "," + nameInDB;
                }
            }
            

        }catch(SQLException exception){
            System.out.println("Error connecting to table: " + exception.getMessage());
        }
        return decoratedPizza.getDescription(name);
    }



}

