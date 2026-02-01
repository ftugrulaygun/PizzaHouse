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

# How to run/test

You can test the code by running Main.java file.

by the example down below, you can test the code by adding the sample these code structures(functions can be chosen specifically by the user):
''

        pizza = new PepperoniDecorator(pizza);
        System.out.println(pizza.getDescription());
        System.out.println("Cost: $" + pizza.getCost());

        pizza = new OliveDecorator(pizza);
        System.out.println(pizza.getDescription());
        System.out.println("Cost: $" + pizza.getCost());

        pizza = new MushroomDecorator(pizza);
        System.out.println(pizza.getDescription());
        System.out.println("Cost: $" + pizza.getCost());'''

