/*
===============================================================================
                    SINGLE RESPONSIBILITY PRINCIPLE (SRP)
===============================================================================

Definition:
-----------
"A class should have one, and only one, reason to change."

In simple words:
A class should perform only ONE responsibility (ONE job).

NOTE:
-----
A responsibility is NOT the same as a method.

Think of it as:
    "Who would ask me to modify this class?"

If different teams/business requirements can force changes to the same class,
then the class is handling multiple responsibilities.

===============================================================================
BAD EXAMPLE (Violation of SRP)
===============================================================================

In the example below, Invoice is responsible for:

1. Business Logic      -> Calculating invoice amount
2. Presentation        -> Printing invoice
3. Persistence         -> Saving invoice into database

Since all three responsibilities are inside the same class,
Invoice has THREE reasons to change.

Possible reasons:
-----------------
✓ Finance team changes tax calculation.
✓ UI team changes invoice format.
✓ DBA changes database or persistence framework.

Every one of these changes requires modifying Invoice.

This violates the Single Responsibility Principle.

*/

class Invoice {

    private final String customerName;
    private final double amount;

    public Invoice(String customerName, double amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    // Responsibility #1
    // Business Logic
    public double calculateTotal() {
        return amount + amount * 0.18;
    }

    // Responsibility #2
    // Presentation
    public void printInvoice() {
        System.out.println(customerName);
        System.out.println(calculateTotal());
    }

    // Responsibility #3
    // Persistence
    public void saveToDatabase() {
        System.out.println("Saving invoice...");
    }
}


/*
===============================================================================
GOOD EXAMPLE (Following SRP)
===============================================================================

Instead of putting everything inside Invoice,
split every responsibility into its own class.

Invoice
    ↓
Stores invoice data only.

InvoiceCalculator
    ↓
Contains only business logic.

InvoicePrinter
    ↓
Contains only printing logic.

InvoiceRepository
    ↓
Contains only database logic.

Now every class has exactly ONE reason to change.
*/


// Only responsible for holding invoice data.
class Invoice {

    private final String customerName;
    private final double amount;

    public Invoice(String customerName, double amount) {
        this.customerName = customerName;
        this.amount = amount;
    }

    public String getCustomerName() {
        return customerName;
    }

    public double getAmount() {
        return amount;
    }
}


// Only responsible for calculation.
class InvoiceCalculator {

    public double calculateTotal(Invoice invoice) {
        return invoice.getAmount() + invoice.getAmount() * 0.18;
    }
}


// Only responsible for printing.
class InvoicePrinter {

    public void print(Invoice invoice) {
        System.out.println(invoice.getCustomerName());
        System.out.println(invoice.getAmount());
    }
}


// Only responsible for database operations.
class InvoiceRepository {

    public void save(Invoice invoice) {
        System.out.println("Saving invoice to database...");
    }
}


/*
===============================================================================
BENEFITS OF SRP
===============================================================================

1. Easier Maintenance
---------------------
A change in one responsibility does not affect others.

2. Better Readability
---------------------
Each class has a clear purpose.

3. Easier Testing
-----------------
Each class can be tested independently.

4. Better Reusability
---------------------
Classes can be reused without unnecessary dependencies.

5. Lower Coupling
-----------------
Changes in one module have minimal impact on others.

6. Easier Extension
-------------------
New functionality can be added by creating new classes
instead of modifying existing ones.

===============================================================================
REAL WORLD ANALOGY
===============================================================================

Restaurant Example

Chef
    -> Cooks food only.

Cashier
    -> Handles billing only.

Delivery Person
    -> Delivers food only.

Each person has ONE responsibility.

Imagine one person doing:
    - Cooking
    - Billing
    - Delivery

Whenever any one responsibility changes,
that same person needs retraining.

This is exactly what happens when a class violates SRP.

===============================================================================
INTERVIEW NOTES
===============================================================================

✔ One Class = One Responsibility

✔ One Responsibility = One Reason To Change

✔ Responsibility is a business concern, NOT simply a method.

Ask yourself:

"If a requirement changes, how many different reasons could force
this class to change?"

If the answer is more than one,
the class is likely violating SRP.

===============================================================================
Remember:
-------------------------------------------------------------------------------

BAD:
Invoice
 ├── calculate()
 ├── print()
 └── save()

GOOD:
Invoice              -> Data
InvoiceCalculator    -> Business Logic
InvoicePrinter       -> Presentation
InvoiceRepository    -> Persistence

One class.
One job.
One reason to change.

===============================================================================
