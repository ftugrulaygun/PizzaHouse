package com.pizzashop.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import com.pizzashop.database.DatabaseManager;
import com.pizzashop.database.Ingredient;
import com.pizzashop.models.Pizza;


public class PizzaOrderUI extends JFrame{
    private Pizza pizza;
    private DatabaseManager dbManager;
    private List<JCheckBox> ingredientCheckboxes;
    private JTextArea pizzaDescriptionArea;
    private JLabel costLabel;

    public PizzaOrderUI() {
        // Setup frame
        setTitle("Pizza Order System");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        try {
        this.dbManager = new DatabaseManager();
        this.dbManager.initializeTables();
        } catch (Exception exception) {
            System.err.println("Failed to initialize database: " + exception.getMessage());
            exception.printStackTrace();
        }
    }
    public void displayIngredients(){
        List<Ingredient> Ingredients = dbManager.getAllIngredients();

        JPanel listPanel = new JPanel(new GridLayout(0, 1, 0, 5));

        for(Ingredient ingredient: Ingredients){
            String text = "-" + ingredient.getName() + "($" + ingredient.getPrice() + ")";

            JLabel label =  new JLabel(text);
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
            listPanel.add(label);
        }
        JScrollPane scrollPane = new JScrollPane(listPanel);
        this.add(scrollPane, BorderLayout.CENTER);

        this.revalidate();
        this.repaint();

    }
    private void updatePizza() {
        // Rebuild pizza based on selected checkboxes
        // Update display
    }
}

