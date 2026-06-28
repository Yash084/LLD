
/*
======================== FACTORY DESIGN PATTERN ========================

🔹 What is Factory Pattern?
A creational design pattern used to create objects without exposing
the creation logic to the client.

Instead of using `new` directly, the client asks a factory class to
provide the required object.

-----------------------------------------------------------------------

🔹 Why use it?
- To reduce tight coupling between client and concrete classes
- To centralize object creation logic
- To make code easier to extend and maintain

-----------------------------------------------------------------------

🔹 Core Idea:
- Define a common interface (e.g., Vehicle)
- Create multiple implementations (Bike, Car)
- Create a Factory class that decides which object to return
- Client interacts only with interface, not implementation

-----------------------------------------------------------------------

🔹 Structure:

1. Interface:
   Vehicle → common contract

2. Implementations:
   TwoWheelerVehicle, FourWheelerVehicle

3. Factory Class:
   VehicleFactory → contains logic to create objects

4. Client:
   Calls factory instead of using `new`

-----------------------------------------------------------------------

🔹 Advantages:
✔ Loose coupling (client doesn’t depend on concrete classes)
✔ Centralized object creation
✔ Easy to manage and maintain
✔ Cleaner and more readable code

-----------------------------------------------------------------------

🔹 Interview One-liner:
"Factory pattern centralizes object creation and returns objects
based on input, reducing coupling and improving flexibility."

-----------------------------------------------------------------------

🔹 When to use:
- Multiple implementations of same interface
- Object creation logic is complex
- Want to hide implementation details

-----------------------------------------------------------------------

🔹 Limitations:
- Uses if-else / switch → violates Open/Closed Principle
- Needs modification when new types are added

(Improvement: use map-based factory or Factory Method pattern)

=======================================================================
*/


interface Vehicle{
    public void drive();
}

class TwoWheelerVehicle implements Vehicle{
    
    @Override
    public void drive(){
        System.out.println("Driving TwoWheelerVehicle");
    }
}

class FourWheelerVehicle implements Vehicle{
    
    @Override
    public void drive(){
        System.out.println("Driving FourWheelerVehicle");
    }
}


class VehicleFactory{
    
    public static Vehicle getVehicle(String type){
        if(type.equalsIgnoreCase("CAR")){
            return new FourWheelerVehicle();
        }else if(type.equalsIgnoreCase("BIKE")){
                   return new TwoWheelerVehicle();   
        }else{
            throw new IllegalArgumentException();
        }
    }
    
}


public class Main
{
	public static void main(String[] args) {
	    Vehicle veh1 = VehicleFactory.getVehicle("BKE");
	    veh1.drive();
	}
}
