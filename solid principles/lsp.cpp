/*
===============================================================================
                LISKOV SUBSTITUTION PRINCIPLE (LSP)
===============================================================================

Definition:
-----------
"Objects of a superclass should be replaceable with objects of its
subclasses without affecting the correctness of the program."

- Barbara Liskov

In simple words:
Wherever a parent object is expected,
its child object should be usable without breaking the program.

A subclass should behave like its parent.

If replacing the parent with a child changes the expected behavior,
then LSP is violated.

===============================================================================
BAD EXAMPLE (Violation of LSP)
===============================================================================

Suppose we have a Bird class.

Every bird can fly... right?

Not really.

Penguins are birds but they cannot fly.

If Penguin extends Bird and overrides fly() by throwing an exception,
clients using Bird will break.

*/

class Bird {

    public void fly() {
        System.out.println("Bird is flying...");
    }
}

class Sparrow extends Bird {

    @Override
    public void fly() {
        System.out.println("Sparrow is flying...");
    }
}

class Penguin extends Bird {

    @Override
    public void fly() {
        throw new UnsupportedOperationException("Penguins cannot fly!");
    }
}


/*
Client Code
-----------
*/

public class Main {

    public static void makeBirdFly(Bird bird) {
        bird.fly();
    }

    public static void main(String[] args) {

        Bird sparrow = new Sparrow();
        makeBirdFly(sparrow);

        Bird penguin = new Penguin();
        makeBirdFly(penguin);     // Runtime Exception

    }
}


/*
Problems:
---------

1. Penguin IS-A Bird,
   but it cannot fulfill Bird's expected behavior.

2. Client expects every Bird to fly.

3. Replacing Bird with Penguin breaks the application.

This violates the Liskov Substitution Principle.

===============================================================================
GOOD EXAMPLE (Following LSP)
===============================================================================

Instead of forcing every Bird to fly,
move the flying behavior into a separate abstraction.

Now,

Bird
    ↓
General bird properties

FlyingBird
    ↓
Only birds capable of flying

Non-flying birds simply extend Bird.

*/


// General Bird
class Bird {

    public void eat() {
        System.out.println("Bird is eating...");
    }
}


// Only flying birds extend this class.
class FlyingBird extends Bird {

    public void fly() {
        System.out.println("Flying...");
    }
}


// Sparrow can fly.
class Sparrow extends FlyingBird {

    @Override
    public void fly() {
        System.out.println("Sparrow is flying...");
    }
}


// Eagle can fly.
class Eagle extends FlyingBird {

    @Override
    public void fly() {
        System.out.println("Eagle is flying...");
    }
}


// Penguin is still a Bird,
// but NOT a FlyingBird.
class Penguin extends Bird {

    public void swim() {
        System.out.println("Penguin is swimming...");
    }
}


/*
Client Code
-----------
*/

public class Main {

    public static void makeBirdFly(FlyingBird bird) {
        bird.fly();
    }

    public static void main(String[] args) {

        makeBirdFly(new Sparrow());

        makeBirdFly(new Eagle());

        // Penguin is not a FlyingBird.
        // Compiler prevents incorrect usage.

        // makeBirdFly(new Penguin()); // Compile-time error

    }
}


/*
===============================================================================
BENEFITS OF LSP
===============================================================================

1. Correct Inheritance
----------------------
Child classes truly satisfy the parent's contract.

2. No Unexpected Runtime Errors
-------------------------------
Replacing parent with child does not break behavior.

3. Better Polymorphism
----------------------
Objects become interchangeable.

4. Easier Maintenance
---------------------
Less defensive programming.

5. More Reliable Code
---------------------
Behavior remains consistent across implementations.

===============================================================================
REAL WORLD ANALOGY
===============================================================================

Think of a Vehicle.

Vehicle
    ↓
Start Engine

Car
Bike
Truck

All can start the engine.

Now imagine Bicycle extends Vehicle
and throws an exception when startEngine() is called.

That would be wrong.

Instead,

Vehicle
    ↓
Car
Bike
Truck

Bicycle should inherit from a different abstraction
because it doesn't have an engine.

Inheritance should represent true behavior,
not just an "IS-A" relationship.

===============================================================================
INTERVIEW NOTES
===============================================================================

✔ Child classes must honor the parent's contract.

✔ Parent object should be replaceable by any child object.

✔ Child classes should NOT:
    - Throw UnsupportedOperationException
    - Remove expected behavior
    - Strengthen method preconditions
    - Weaken method guarantees

A subclass should extend behavior,
not contradict it.

===============================================================================
COMMON LSP VIOLATIONS
===============================================================================

❌ Rectangle / Square Problem

Rectangle
    setWidth()
    setHeight()

Square overrides both methods
to keep width == height.

Client expecting Rectangle behavior gets incorrect results.

------------------------------------------------------------

❌ Bird / Penguin

Bird has fly()

Penguin throws exception.

------------------------------------------------------------

❌ Payment Interface

interface Payment {
    pay();
}

CreditCardPayment
UPIPayment

Good.

Later,

FreePayment implements Payment
but throws exception inside pay().

Violation.

===============================================================================
HOW TO IDENTIFY LSP VIOLATIONS
===============================================================================

Ask yourself:

"If I replace every Parent object with any Child object,
will my application still behave correctly?"

If the answer is NO,
your inheritance hierarchy is wrong.

===============================================================================
REMEMBER
===============================================================================

BAD

Bird
 ├── Sparrow
 └── Penguin

Bird has fly()

Penguin throws exception.

Client breaks.

------------------------------------------------------------

GOOD

Bird
 ├── FlyingBird
 │      ├── Sparrow
 │      └── Eagle
 │
 └── Penguin

Only FlyingBird has fly().

No runtime surprises.

Compiler prevents incorrect usage.

===============================================================================

One Principle to Remember:

"A subclass should extend the parent's behavior,
not change or break it."

===============================================================================
*/
