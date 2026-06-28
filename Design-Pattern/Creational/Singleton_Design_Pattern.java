/*
==================== SINGLETON DESIGN PATTERN ====================

Ensures only one instance of a class and provides global access.

Different implementations based on:
- Initialization timing (eager vs lazy)
- Thread safety
- Performance

=================================================================
*/


/* 
-------------------- 1. EAGER INITIALIZATION --------------------

- Instance created at class loading time
- Thread-safe (handled by JVM)
- Not lazy (may waste memory if unused)

class Singleton {

    private static final Singleton instance = new Singleton();

    private Singleton() {}

    public static Singleton getInstance() {
        return instance;
    }

    void instanceMethod() {
        System.out.println("Eager Singleton");
    }
}
*/


/* 
-------------------- 2. LAZY INITIALIZATION --------------------

- Instance created only when needed
- NOT thread-safe

class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    void instanceMethod() {
        System.out.println("Lazy Singleton (Not Thread Safe)");
    }
}
*/


/* 
-------------------- 3. SYNCHRONIZED METHOD --------------------

- Thread-safe
- Slower due to method-level synchronization

class Singleton {

    private static Singleton instance;

    private Singleton() {}

    public static synchronized Singleton getInstance() {
        if (instance == null) {
            instance = new Singleton();
        }
        return instance;
    }

    void instanceMethod() {
        System.out.println("Synchronized Singleton");
    }
}
*/


/* 
-------------------- 4. DOUBLE-CHECKED LOCKING --------------------

- Thread-safe and efficient
- Uses volatile to prevent instruction reordering
- Synchronization only when instance is null

class Singleton {

    private static volatile Singleton instance;

    private Singleton() {}

    public static Singleton getInstance() {
        if (instance == null) {
            synchronized (Singleton.class) {
                if (instance == null) {
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    void instanceMethod() {
        System.out.println("Double Checked Locking Singleton");
    }
}
*/


/* 
-------------------- 5. BILL PUGH SINGLETON (BEST PRACTICE) --------------------

- Lazy initialization
- Thread-safe (class loading guarantees it)
- No synchronization overhead
- Most recommended in Java

class Singleton {

    private Singleton() {}

    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }

    void instanceMethod() {
        System.out.println("Bill Pugh Singleton");
    }
}
*/


/* ==================== ACTIVE IMPLEMENTATION ==================== */

class Singleton {

    private Singleton() {}

    private static class Holder {
        private static final Singleton INSTANCE = new Singleton();
    }

    public static Singleton getInstance() {
        return Holder.INSTANCE;
    }

    void instanceMethod() {
        System.out.println("Using Bill Pugh Singleton");
    }
}


public class Main {
    public static void main(String[] args) {
        Singleton obj = Singleton.getInstance();
        obj.instanceMethod();
    }
}
