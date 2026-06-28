/*
======================== ADAPTER DESIGN PATTERN ========================

🔹 What is Adapter Pattern?
A structural design pattern that allows two incompatible interfaces
to work together by converting one interface into another.

👉 It acts as a bridge between old and new systems.

-----------------------------------------------------------------------

🔹 Why use it?
- When existing class has incompatible interface
- When you cannot modify legacy/third-party code
- To integrate old systems with new requirements

-----------------------------------------------------------------------

🔹 Core Idea:
- Client expects a certain interface (Target)
- Existing class has a different interface (Adaptee)
- Adapter converts calls from Target → Adaptee

-----------------------------------------------------------------------

🔹 Structure:

1. Target Interface:
   Printer → method: print()

2. Adaptee:
   OldPrinter → method: printOld()

3. Adapter:
   PrinterAdapter
   - Implements Target interface
   - Wraps Adaptee object
   - Converts method call

4. Client:
   Uses Target interface (Printer)

-----------------------------------------------------------------------

🔹 Flow:
Printer printer = new PrinterAdapter(new OldPrinter());
printer.print();

→ Client calls print()
→ Adapter converts call → printOld()
→ Adaptee executes logic

-----------------------------------------------------------------------

🔹 Key Concept:
Adapter translates interface, not behavior.

-----------------------------------------------------------------------

🔹 Advantages:
✔ Allows reuse of existing/legacy code
✔ Promotes loose coupling
✔ No need to modify old classes
✔ Clean integration between systems

-----------------------------------------------------------------------

🔹 Important Design Points:
- Adapter uses composition (has-a relationship)
- Client should depend only on Target interface
- Adapter should focus on translation, not business logic

-----------------------------------------------------------------------

🔹 Types of Adapter:
1. Object Adapter (uses composition) → MOST COMMON ✅
2. Class Adapter (uses inheritance) → less used in Java

-----------------------------------------------------------------------

🔹 Real-world analogy:
Power adapter 🔌
Different plug → converted to fit socket

-----------------------------------------------------------------------

🔹 When to use:
- Integrating third-party libraries
- Migrating legacy systems
- API compatibility layers

-----------------------------------------------------------------------

🔹 Interview One-liner:
"Adapter pattern converts one interface into another so that
incompatible classes can work together."

=======================================================================
*/

interface Printer {
    void print();
}

// Adaptee
class OldPrinter {
    public void printOld() {
        System.out.println("Old printing logic");
    }
}

// Adapter
class PrinterAdapter implements Printer {

    private OldPrinter oldPrinter;

    public PrinterAdapter(OldPrinter oldPrinter) {
        this.oldPrinter = oldPrinter;
    }

    @Override
    public void print() {
        oldPrinter.printOld(); // adapting old method
        System.out.println("Successfully used the old printing logic");
    }
}

public class Main {
    public static void main(String[] args) {
        Printer printer = new PrinterAdapter(new OldPrinter());
        printer.print();
    }
}
