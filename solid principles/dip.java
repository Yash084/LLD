/*
===============================================================================
                DEPENDENCY INVERSION PRINCIPLE (DIP)
===============================================================================

Definition:
-----------
"High-level modules should not depend on low-level modules.
Both should depend on abstractions.

Abstractions should not depend on details.
Details should depend on abstractions."

- Robert C. Martin (Uncle Bob)

In simple words:
High-level business logic should NOT directly depend on
concrete implementations.

Instead, both should depend on interfaces (abstractions).

This makes the application flexible, extensible, and easy to test.

===============================================================================
BAD EXAMPLE (Violation of DIP)
===============================================================================

Suppose we have a NotificationService responsible for sending
notifications.

Instead of depending on an abstraction,
it directly creates an EmailNotification object.

*/


class EmailNotification {

    public void send(String message) {
        System.out.println("Sending Email : " + message);
    }
}


class NotificationService {

    // Tight coupling with EmailNotification
    private final EmailNotification emailNotification =
            new EmailNotification();

    public void notifyUser(String message) {
        emailNotification.send(message);
    }
}


/*
Problems:
---------

1. High-level module (NotificationService)
   directly depends on a low-level module (EmailNotification).

2. If tomorrow we need:
       SMS
       Push Notification
       WhatsApp
       Slack

   We must modify NotificationService.

3. Difficult to unit test because EmailNotification
   is hardcoded inside the class.

4. Violates Open Closed Principle as well.

===============================================================================
GOOD EXAMPLE (Following DIP)
===============================================================================

Both NotificationService and notification implementations
depend on an abstraction.

*/


// Abstraction
interface Notification {

    void send(String message);
}


/*
Low-Level Modules
-----------------
*/

class EmailNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending Email : " + message);
    }
}


class SmsNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending SMS : " + message);
    }
}


class PushNotification implements Notification {

    @Override
    public void send(String message) {
        System.out.println("Sending Push Notification : " + message);
    }
}


/*
High-Level Module
-----------------

Notice that NotificationService depends only on the
Notification interface.

It doesn't know whether the notification is Email,
SMS or Push.

*/

class NotificationService {

    private final Notification notification;

    // Dependency Injection
    public NotificationService(Notification notification) {
        this.notification = notification;
    }

    public void notifyUser(String message) {
        notification.send(message);
    }
}


/*
Client Code
-----------
*/

public class Main {

    public static void main(String[] args) {

        Notification email = new EmailNotification();
        Notification sms = new SmsNotification();
        Notification push = new PushNotification();

        NotificationService emailService =
                new NotificationService(email);

        NotificationService smsService =
                new NotificationService(sms);

        NotificationService pushService =
                new NotificationService(push);

        emailService.notifyUser("Welcome!");

        smsService.notifyUser("OTP : 123456");

        pushService.notifyUser("Order Delivered");

    }
}


/*
===============================================================================
WHY IS THIS BETTER?
===============================================================================

NotificationService only knows about Notification.

It does NOT know:

✔ EmailNotification
✔ SmsNotification
✔ PushNotification

Tomorrow, if we introduce:

class WhatsAppNotification implements Notification

No changes are required inside NotificationService.

We simply inject the new implementation.

This is Dependency Inversion Principle.

===============================================================================
DEPENDENCY INJECTION vs DEPENDENCY INVERSION
===============================================================================

Many people confuse these two concepts.

Dependency Inversion Principle (DIP)
------------------------------------
A design principle.

It says:

"Depend on abstractions instead of concrete classes."

Dependency Injection (DI)
-------------------------
A design pattern / technique used to achieve DIP.

Example:

public NotificationService(Notification notification)

Spring injects the dependency automatically.

Constructor Injection
Setter Injection
Field Injection

These are ways to implement DIP.

===============================================================================
BENEFITS OF DIP
===============================================================================

1. Loose Coupling
-----------------
Classes depend on interfaces instead of implementations.

2. Easier Testing
-----------------
We can inject mock implementations.

Example:

Notification mockNotification = mock(Notification.class);

3. Easy to Extend
-----------------
New implementations require no changes to business logic.

4. Better Maintainability
-------------------------
Changing implementation doesn't affect high-level modules.

5. Encourages Interface-based Design
------------------------------------
Business logic becomes independent of implementation details.


===============================================================================
INTERVIEW NOTES
===============================================================================

✔ High-level modules should NOT depend on low-level modules.

✔ Both should depend on abstractions.

✔ Interfaces define contracts.

✔ Implementations provide details.

✔ Constructor Injection is the preferred way
   to achieve Dependency Injection.

===============================================================================
COMMON DIP VIOLATIONS
===============================================================================

❌ Repository tightly coupled

class UserService {

    private MySqlRepository repository =
            new MySqlRepository();
}

Better:

interface UserRepository

MySqlRepository
MongoRepository

Inject UserRepository.

===============================================================================
SPRING BOOT EXAMPLE
===============================================================================

interface PaymentGateway {

    void pay();
}

@Component
class RazorpayGateway implements PaymentGateway {

    @Override
    public void pay() {
        System.out.println("Paid using Razorpay");
    }
}

@Service
class PaymentService {

    private final PaymentGateway paymentGateway;

    // Constructor Injection
    public PaymentService(PaymentGateway paymentGateway) {
        this.paymentGateway = paymentGateway;
    }

    public void processPayment() {
        paymentGateway.pay();
    }
}

Spring automatically injects RazorpayGateway
because it implements PaymentGateway.

The service depends only on the interface.

===============================================================================
REMEMBER
===============================================================================

BAD

NotificationService
        ↓
EmailNotification

High-level depends on low-level.

Tightly coupled.

------------------------------------------------------------

GOOD

          Notification (Interface)
                 ▲
      ┌──────────┼──────────┐
      │          │          │
 EmailNotification
 SmsNotification
 PushNotification

                 ▲
                 │
      NotificationService

NotificationService depends only on the interface.

New implementations can be added
without changing business logic.

===============================================================================

One Principle to Remember:

"Depend on abstractions,
not on concrete implementations."

High-Level Module
        ↓
   Abstraction
        ↑
Low-Level Modules

This is the essence of Dependency Inversion Principle.

===============================================================================
*/
