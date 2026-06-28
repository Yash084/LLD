```java
/*
===============================================================================
                    OPEN CLOSED PRINCIPLE (OCP)
===============================================================================

Definition:
-----------
"Software entities (classes, modules, functions, etc.) should be
OPEN for extension, but CLOSED for modification."

- Bertrand Meyer

In simple words:
A class should allow adding new functionality WITHOUT modifying
its existing source code.

Why?
----
Existing code is already:
- Tested
- Stable
- Used in production

Modifying it increases the risk of introducing bugs.

Instead, extend the behavior by adding new classes.

===============================================================================
BAD EXAMPLE (Violation of OCP)
===============================================================================

Suppose we have a NotificationService that can send Email notifications.

Initially it works fine.

Later, a new requirement comes:
    "Support SMS notifications."

Developer modifies the existing class.

A month later:
    "Support Push notifications."

Again the same class is modified.

Every new notification type requires changing existing code.

This violates the Open Closed Principle.
*/

class NotificationService {

    public void send(String type, String message) {

        if ("EMAIL".equals(type)) {
            System.out.println("Sending Email: " + message);
        }
        else if ("SMS".equals(type)) {
            System.out.println("Sending SMS: " + message);
        }
        else if ("PUSH".equals(type)) {
            System.out.println("Sending Push Notification: " + message);
        }

        // Every new notification type requires modifying this method.
    }
}


/*
Problems:
---------
1. Existing class changes whenever a new notification type is added.

2. if-else chain keeps growing.

3. High chance of breaking existing functionality.

4. Difficult to maintain.

This is NOT closed for modification.
*/


/*
===============================================================================
GOOD EXAMPLE (Following OCP)
===============================================================================

Instead of checking notification type using if-else,
create a common interface.

Every notification type will implement that interface.

When a new notification type is needed,
simply create another implementation.

Existing classes remain unchanged.
*/


// Common abstraction
interface Notification {

    void send(String message);
}


// Email implementation
class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending Email: " + message);
    }
}


// SMS implementation
class SmsNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending SMS: " + message);
    }
}


// Push implementation
class PushNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending Push Notification: " + message);
    }
}


// NotificationService depends on abstraction,
// not on concrete notification types.
class NotificationService {

    public void sendNotification(Notification notification, String message) {
        notification.send(message);
    }
}


/*
Client Code
-----------
*/

public class Main {

    public static void main(String[] args) {

        NotificationService service = new NotificationService();

        service.sendNotification(new EmailNotification(), "Welcome!");

        service.sendNotification(new SmsNotification(), "OTP: 123456");

        service.sendNotification(new PushNotification(), "Order Delivered");

        // Tomorrow if WhatsApp notification is required,
        // simply create WhatsAppNotification class.

        // Notification whatsapp = new WhatsAppNotification();
        // service.sendNotification(whatsapp, "Hello");

        // NotificationService remains unchanged.
    }
}


/*
===============================================================================
BENEFITS OF OCP
===============================================================================

1. Existing Code Remains Stable
-------------------------------
Already tested code is not modified.

2. Easy to Extend
-----------------
Add new functionality by creating new classes.

3. Lower Risk of Bugs
---------------------
Existing behavior remains untouched.

4. Better Maintainability
-------------------------
No huge if-else or switch statements.

5. Better Scalability
---------------------
Application grows without modifying old code.

6. Encourages Polymorphism
--------------------------
Uses interfaces and inheritance instead of conditionals.

===============================================================================
REAL WORLD ANALOGY
===============================================================================

Think of a Mobile Charger.

Your wall socket does not change
when you buy a new phone.

Instead,
you use a different charging cable or adapter.

Wall Socket
    ↓
Closed for modification.

New Charger
    ↓
Extension.

Similarly,

NotificationService
    ↓
Does not change.

New Notification Type
    ↓
Create a new implementation.

===============================================================================
INTERVIEW NOTES
===============================================================================

✔ Open for Extension

You should be able to add new functionality.

✔ Closed for Modification

Existing code should not change.

Instead of writing:

if(type == EMAIL)
else if(type == SMS)
else if(type == PUSH)

Use:

Interface
    ↓
Different implementations
    ↓
Runtime polymorphism

===============================================================================
WHEN DOES OCP HELP?
===============================================================================

Whenever new business requirements arrive frequently.

Examples:

✓ New Payment Methods
    - Credit Card
    - UPI
    - PayPal
    - Apple Pay

✓ New Notification Types
    - Email
    - SMS
    - Push
    - WhatsApp

✓ New Discount Strategies
    - Festival Discount
    - Premium Customer Discount
    - Coupon Discount

✓ New Shipping Methods
    - Air
    - Road
    - Express

Instead of modifying existing classes,
create new implementations.

===============================================================================
REMEMBER
===============================================================================

BAD:

NotificationService

if (EMAIL)
else if (SMS)
else if (PUSH)
else if (WHATSAPP)
...

Every new feature changes existing code.


GOOD:

Notification (Interface)

        ↑
   EmailNotification
   SmsNotification
   PushNotification
   WhatsAppNotification

NotificationService

        ↓

Uses Notification interface only.

No modification required.

===============================================================================

One Principle to Remember:

"Don't modify working code to add new behavior.
Extend it."

===============================================================================
```

*/
