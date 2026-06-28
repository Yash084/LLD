/*
==================== ABSTRACT FACTORY DESIGN PATTERN ====================

🔹 What is Abstract Factory?
A creational design pattern used to create families of related objects
without specifying their concrete classes.

👉 It is called "Factory of Factories"

-----------------------------------------------------------------------

🔹 Why use it?
- When you have multiple families of related objects
- To ensure consistency among related objects
- To avoid mixing incompatible object types
- To remove conditional logic from object creation

-----------------------------------------------------------------------

🔹 Core Idea:
- Define an abstract factory interface
- Create multiple concrete factories (each for a family)
- Each factory creates related objects (car, bike, etc.)
- Client chooses factory, not individual objects

-----------------------------------------------------------------------

🔹 Structure:

1. Product Interface:
   Vehicle → common contract

2. Concrete Products (Families):
   Luxury → LuxuryCar, LuxuryBike
   Ordinary → OrdinaryCar, OrdinaryBike

3. Abstract Factory:
   VehicleFactory → defines methods:
       - createCar()
       - createBike()

4. Concrete Factories:
   LuxuryVehicleFactory → creates luxury objects
   OrdinaryVehicleFactory → creates ordinary objects

5. Factory Producer:
   Returns appropriate factory based on input

-----------------------------------------------------------------------

🔹 Flow:
VehicleFactory factory = FactoryProducer.getVehicleFactory("LUXURY");

Vehicle car = factory.createCar();
Vehicle bike = factory.createBike();

→ Select factory (family)
→ Create related objects
→ Use them via interface

-----------------------------------------------------------------------

🔹 Key Concept:
Factory → creates one object
Abstract Factory → creates a family of related objects

-----------------------------------------------------------------------

🔹 Advantages:
✔ Ensures consistency (no mixing of families)
✔ Removes if-else logic from object creation
✔ Promotes loose coupling
✔ Easy to switch entire family at runtime

-----------------------------------------------------------------------

🔹 Interview One-liner:
"Abstract Factory pattern provides an interface to create families
of related objects without exposing their concrete implementations."

-----------------------------------------------------------------------

🔹 Real-world analogy:
Theme-based UI:
Dark Theme → DarkButton, DarkTextBox
Light Theme → LightButton, LightTextBox

Selecting theme ensures all components match.

=======================================================================
*/

interface Vehicle {
	public void drive();
}

class LuxuryCar implements Vehicle {
	public void drive() {
		System.out.println("Drivind LuxuryCar");
	}
}

class LuxuryBike implements Vehicle {
	public void drive() {
		System.out.println("Drivind LuxuryBike");
	}
}

class OrdinaryCar implements Vehicle {
	public void drive() {
		System.out.println("Drivind OrdinaryCar");
	}
}


class OrdinaryBike implements Vehicle {
	public void drive() {
		System.out.println("Drivind OrdinaryBike");
	}
}

interface VehicleFactory {
	public Vehicle createCar();
	public Vehicle createBike();
}

class LuxuryVehicleFactory implements VehicleFactory {

	@Override
	public Vehicle createCar() {
		return new LuxuryCar();
	}

	@Override
	public Vehicle createBike() {
		return new LuxuryBike();
	}

}

class OrdinaryVehicleFactory implements VehicleFactory {

	@Override
	public Vehicle createCar() {
		return new OrdinaryCar();
	}

	@Override
	public Vehicle createBike() {
		return new OrdinaryBike();
	}
}


class FactoryProducer{
    public static VehicleFactory getVehicleFactory(String type){
        if(type.equalsIgnoreCase("LUXURY")){
            return new LuxuryVehicleFactory();
        }else if(type.equalsIgnoreCase("ORDINARY")){
            return new OrdinaryVehicleFactory();
        }
        throw new IllegalArgumentException();
    }
}

public class Main
{
	public static void main(String[] args) {
		VehicleFactory luxuryFactory = FactoryProducer.getVehicleFactory("LUXURY");
		var bike = luxuryFactory.createBike();
		bike.drive();
	}
}
