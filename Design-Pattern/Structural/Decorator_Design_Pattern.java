/*
======================== DECORATOR DESIGN PATTERN ========================

🔹 What is Decorator Pattern?
A structural design pattern that allows behavior to be added to an object
dynamically without modifying its class.

👉 It wraps an object and enhances its functionality.

-----------------------------------------------------------------------

🔹 Why use it?
- To add features without changing existing code
- To avoid creating many subclasses (class explosion)
- To combine multiple behaviors dynamically

-----------------------------------------------------------------------

🔹 Core Idea:
- Have a base interface (Component)
- Create a concrete implementation
- Create a decorator that wraps the component
- Decorators add behavior before/after delegating the call

-----------------------------------------------------------------------

🔹 Structure:

1. Component Interface:
   Coffee → method: cost()

2. Concrete Component:
   BasicCoffee → base implementation

3. Abstract Decorator:
   CoffeeDecorator
   - Implements Coffee
   - Has a reference to Coffee (composition)

4. Concrete Decorators:
   MilkDecorator, SugarDecorator
   - Extend CoffeeDecorator
   - Add extra functionality

5. Client:
   Uses decorated object

-----------------------------------------------------------------------

🔹 Flow:
Coffee coffee = new MilkDecorator(
                    new SugarDecorator(
                        new BasicCoffee()));

coffee.cost();

→ BasicCoffee.cost() → 100
→ Sugar adds → +30 → 130
→ Milk adds → +20 → 150

-----------------------------------------------------------------------

🔹 Key Concept:
Decorator wraps an object, delegates the call, then enhances behavior.

👉 Wrap → Delegate → Enhance

-----------------------------------------------------------------------

🔹 Advantages:
✔ Adds functionality dynamically
✔ Avoids subclass explosion
✔ Follows Open/Closed Principle
✔ Flexible combination of behaviors

-----------------------------------------------------------------------

🔹 Important Design Points:
- Uses composition (has-a relationship)
- Does NOT modify original class
- Each decorator calls wrapped object first
- Multiple decorators can be chained

-----------------------------------------------------------------------

🔹 Types:
- Object Decorator (uses composition) → MOST COMMON ✅

-----------------------------------------------------------------------

🔹 Real-world analogy:
Clothing layers 👕🧥🧣
You wear layers over base clothes → each adds functionality

-----------------------------------------------------------------------

🔹 When to use:
- Need to add features dynamically
- Multiple combinations of behavior required
- Want to extend without modifying base class

-----------------------------------------------------------------------

🔹 Interview One-liner:
"Decorator pattern dynamically adds behavior to an object by wrapping it,
without modifying the original class."

=======================================================================
*/



interface Coffee{
    public int cost();
}

class BasicCoffee implements Coffee{
    
    @Override
    public int cost(){
        System.out.println("Cost of BasicCoffee");
        return 100;
    }
}

abstract class CoffeeDecorator implements Coffee{
    
    Coffee coffee;
    
    public CoffeeDecorator(Coffee coffee){
        this.coffee = coffee;
    }
    
}

class MilkDecorator extends CoffeeDecorator{
    
    public MilkDecorator(Coffee coffee){
        super(coffee);
    }
    
    @Override
    public int cost(){
        var cost = coffee.cost();
        System.out.println("Cost of MilkDecorator");
        return cost + 20; 
    }
    
}

class SugarDecorator extends CoffeeDecorator{
    
    SugarDecorator(Coffee coffee){
        super(coffee);
    }
    
    @Override
    public int cost(){
        var cost = coffee.cost();
        System.out.println("Cost of SugarDecorator");
        return cost + 30;
    }
}

public class Main {
    public static void main(String[] args) {
        Coffee  obj = new MilkDecorator( new SugarDecorator( new BasicCoffee()));
        System.out.println("Total Cost: " + obj.cost());
    }
}
