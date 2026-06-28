/*
===============================================================================
                INTERFACE SEGREGATION PRINCIPLE (ISP)
===============================================================================

Definition:
-----------
"Clients should not be forced to depend upon interfaces
that they do not use."

- Robert C. Martin (Uncle Bob)

In simple words:
Instead of creating one large interface,
create multiple small and specific interfaces.

A class should only implement the methods it actually needs.

Large "God Interfaces" force implementing classes to provide
unnecessary methods, often resulting in empty implementations
or UnsupportedOperationException.

===============================================================================
BAD EXAMPLE (Violation of ISP)
===============================================================================

Suppose we have a Machine interface.

Some machines can:
    - Print
    - Scan
    - Fax

But not every machine supports all three operations.

*/

interface Machine {

    void print();

    void scan();

    void fax();
}


/*
A modern multifunction printer supports all operations.
*/

class MultiFunctionPrinter implements Machine {

    @Override
    public void print() {
        System.out.println("Printing...");
    }

    @Override
    public void scan() {
        System.out.println("Scanning...");
    }

    @Override
    public void fax() {
        System.out.println("Faxing...");
    }
}


/*
A basic printer only prints.

However, because of the large Machine interface,
it is forced to implement scan() and fax().

*/

class BasicPrinter implements Machine {

    @Override
    public void print() {
        System.out.println("Printing...");
    }

    @Override
    public void scan() {
        throw new UnsupportedOperationException(
                "Basic printer cannot scan."
        );
    }

    @Override
    public void fax() {
        throw new UnsupportedOperationException(
                "Basic printer cannot fax."
        );
    }
}


/*
Problems:
---------

1. BasicPrinter is forced to implement methods
   it does not support.

2. UnsupportedOperationException is a strong
   indicator that ISP is being violated.

3. Clients depending only on printing are still
   forced to know about scan() and fax().

4. Large interfaces become difficult to maintain.

===============================================================================
GOOD EXAMPLE (Following ISP)
===============================================================================

Split the large interface into multiple smaller interfaces.

Each interface should represent exactly ONE capability.

*/


interface Printer {

    void print();
}


interface Scanner {

    void scan();
}


interface Fax {

    void fax();
}


/*
Basic printer only prints.
*/

class BasicPrinter implements Printer {

    @Override
    public void print() {
        System.out.println("Printing...");
    }
}


/*
Modern printer supports printing and scanning.
*/

class OfficePrinter implements Printer, Scanner {

    @Override
    public void print() {
        System.out.println("Printing...");
    }

    @Override
    public void scan() {
        System.out.println("Scanning...");
    }
}


/*
Enterprise printer supports everything.
*/

class EnterprisePrinter implements Printer, Scanner, Fax {

    @Override
    public void print() {
        System.out.println("Printing...");
    }

    @Override
    public void scan() {
        System.out.println("Scanning...");
    }

    @Override
    public void fax() {
        System.out.println("Faxing...");
    }
}


/*
Client Code
-----------
*/

public class Main {

    public static void main(String[] args) {

        Printer printer = new BasicPrinter();
        printer.print();

        Printer officePrinter = new OfficePrinter();
        officePrinter.print();

        Scanner scanner = new OfficePrinter();
        scanner.scan();

        EnterprisePrinter enterprise = new EnterprisePrinter();
        enterprise.print();
        enterprise.scan();
        enterprise.fax();
    }
}


/*
===============================================================================
BENEFITS OF ISP
===============================================================================

1. No Unnecessary Methods
-------------------------
Classes only implement methods they actually need.

2. Easier Maintenance
---------------------
Changes in one interface do not affect unrelated classes.

3. Better Readability
---------------------
Small interfaces are easier to understand.

4. Better Flexibility
---------------------
Classes can combine multiple small interfaces as needed.

5. Reduced Coupling
-------------------
Clients depend only on the functionality they use.

6. Encourages Composition
-------------------------
Small interfaces are easier to compose together.

===============================================================================
INTERVIEW NOTES
===============================================================================

✔ Many small interfaces are better than one large interface.

✔ A class should never be forced to implement methods
   that it does not use.

✔ ISP reduces unnecessary dependencies.

✔ Empty methods and UnsupportedOperationException
   are common signs of an ISP violation.

===============================================================================
HOW TO IDENTIFY ISP VIOLATIONS
===============================================================================

Ask yourself:

"Does this class implement methods that it doesn't actually need?"

OR

"Am I writing empty methods or throwing
UnsupportedOperationException?"

If YES,

Your interface is probably too large
and should be split into smaller interfaces.

===============================================================================
REMEMBER
===============================================================================

BAD

Machine
 ├── print()
 ├── scan()
 └── fax()

BasicPrinter

print() ✔
scan() ✖
fax() ✖

Forced implementation.

------------------------------------------------------------

GOOD

Printer
Scanner
Fax

BasicPrinter
    implements Printer

OfficePrinter
    implements Printer, Scanner

EnterprisePrinter
    implements Printer, Scanner, Fax

Each class implements only what it needs.

===============================================================================

One Principle to Remember:

"Don't force a class to depend on methods
it doesn't use."

Small interfaces.
Focused responsibilities.
Better design.

===============================================================================
```
*/
