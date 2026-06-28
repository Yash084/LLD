/*
======================== STRATEGY DESIGN PATTERN ========================

🔹 What is Strategy Pattern?
A behavioral design pattern that defines a family of algorithms,
encapsulates each one, and makes them interchangeable at runtime.

👉 It allows behavior to be selected dynamically.

-----------------------------------------------------------------------

🔹 Why use it?
- To eliminate large if-else or switch statements
- To make code flexible and extensible
- To allow changing behavior at runtime

-----------------------------------------------------------------------

🔹 Core Idea:
- Define a common interface (Strategy)
- Implement multiple behaviors (Concrete Strategies)
- Use a context class to execute chosen strategy

-----------------------------------------------------------------------

🔹 Structure:

1. Strategy Interface:
   PaymentStrategy → method: pay()

2. Concrete Strategies:
   UPIStrategy
   DebitCardStrategy
   CreditCardStrategy

3. Context:
   PaymentService
   - Has a reference to PaymentStrategy
   - Delegates work to strategy

4. Client:
   Chooses and injects strategy

-----------------------------------------------------------------------

🔹 Flow:
PaymentService service = new PaymentService(new UPIStrategy());
service.makePayment();

→ Client selects strategy
→ Context calls strategy.pay()
→ Correct behavior is executed

-----------------------------------------------------------------------

🔹 Key Concept:
Encapsulate behavior and make it interchangeable.

👉 Replace if-else with polymorphism

-----------------------------------------------------------------------

🔹 Advantages:
✔ Removes conditional logic (if-else)
✔ Easy to add new strategies (Open/Closed Principle)
✔ Improves code readability and maintainability
✔ Allows runtime behavior change

-----------------------------------------------------------------------

🔹 Important Design Points:
- Uses composition (has-a relationship)
- Context should depend on interface, not implementation
- Strategy can be changed dynamically

-----------------------------------------------------------------------

🔹 Real-world analogy:
Google Maps navigation:
- Car 🚗
- Bike 🏍️
- Walking 🚶

👉 Same app, different strategies

-----------------------------------------------------------------------

🔹 When to use:
- Multiple ways to perform same operation
- Behavior needs to change at runtime
- Avoiding complex conditional logic

-----------------------------------------------------------------------

🔹 Interview One-liner:
"Strategy pattern encapsulates different algorithms and allows
them to be selected and used interchangeably at runtime."

-----------------------------------------------------------------------

🔹 Comparison:
Strategy → chooses behavior
Factory  → creates object
Decorator → adds behavior

=======================================================================
*/

interface PaymentStratagey{
    public void pay();
}

class UPIStratagey implements PaymentStratagey{
    
    @Override
    public void pay(){
        System.out.println("Pay method of UPI");
    }
    
}

class DebitCardStratagey implements PaymentStratagey{
    
    @Override
    public void pay(){
        System.out.println("Pay method of Debit card");
    }
}

class CreditCardStratagey implements PaymentStratagey{
    
    @Override
    public void pay(){
        System.out.println("Pay method of Credit card");
    }
}


class PaymentService{
    
    PaymentStratagey  stratagey;
    
    PaymentService(PaymentStratagey stratagey){
        this.stratagey = stratagey;
    }
    
    public void makePayment(){
        stratagey.pay();
    }
    
}

public class Main {
    public static void main(String[] args) {
        var obj = new PaymentService(new UPIStratagey());
        obj.makePayment();
        
        var obj1 = new PaymentService(new CreditCardStratagey());
        obj1.makePayment();
    }
}
