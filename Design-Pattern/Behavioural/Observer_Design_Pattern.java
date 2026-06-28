import java.util.*;


/*
======================== OBSERVER DESIGN PATTERN ========================

🔹 What is Observer Pattern?
A behavioral design pattern that defines a one-to-many relationship
between objects, so that when one object (Observable) changes state,
all its dependents (Observers) are notified automatically.

-----------------------------------------------------------------------

🔹 Why use it?
- To implement event-driven systems
- To notify multiple objects about a state change
- To achieve loose coupling between components

-----------------------------------------------------------------------

🔹 Core Idea:
- One Observable (publisher)
- Multiple Observers (subscribers)
- Observable maintains a list of observers
- When state changes → all observers are notified

-----------------------------------------------------------------------

🔹 Structure:

1. Observable Interface:
   - addSubscriber()
   - removeSubscriber()
   - notifyObservers()

2. Concrete Observable:
   - YoutubeChannel
   - Maintains list of observers
   - Calls update() on each observer

3. Observer Interface:
   - update()

4. Concrete Observer:
   - Subscriber
   - Implements update() to react to changes

-----------------------------------------------------------------------

🔹 Flow:
channel.sendUpdate()
        ↓
notifyObservers()
        ↓
observer.update()

→ All subscribers receive notification

-----------------------------------------------------------------------

🔹 Key Concept:
Observable pushes updates to observers.

👉 Push Model:
Observable → sends data to observers

(Alternative: Pull Model → observer fetches data)

-----------------------------------------------------------------------

🔹 Advantages:
✔ Loose coupling between objects
✔ Easy to add/remove observers
✔ Supports event-driven architecture
✔ Follows Open/Closed Principle

-----------------------------------------------------------------------

🔹 Important Design Points:
- Observable controls notification flow
- Observers are passive receivers
- No direct dependency on concrete classes
- Use interface-based design

-----------------------------------------------------------------------

🔹 Real-world examples:
- YouTube subscriptions
- Notification systems
- Event listeners (UI, backend)
- Pub-Sub systems

-----------------------------------------------------------------------

🔹 When to use:
- Multiple objects depend on one object
- State changes must be propagated
- Need event-based communication

-----------------------------------------------------------------------

🔹 Limitations:
- Can lead to many notifications
- Hard to debug if many observers
- No control over notification order

-----------------------------------------------------------------------

🔹 Interview One-liner:
"Observer pattern defines a one-to-many dependency where changes
in one object automatically notify all its dependents."

=======================================================================
*/



interface Observable {
	public boolean addSubscriber(Observer obj);
	public boolean removeSubscriber(Observer obj);
	public void notifyObservers();
}


class YoutubeChannel implements Observable{
    
	public List<Observer> observerList = new ArrayList<>();

	@Override
	public boolean addSubscriber(Observer obj) {
		if(observerList.contains(obj)) {
			return false;
		}
		observerList.add(obj);
		return true;
	}

	@Override
	public boolean removeSubscriber(Observer obj) {
		if(observerList.contains(obj)) {
			observerList.remove(obj);
			return true;
		}
		return false;
	}

    @Override
	public void notifyObservers() {
		for(var obj: observerList) {
			obj.update();
		}
	}

	public void sendUpdate() {
		System.out.println("Observable has updated");
	}

}

interface Observer {
	public void update();
}


class Subscriber implements Observer {
    
    public String name;

	public Subscriber(String name) {
		this.name = name;
	}
	
	public void update(){
	    System.out.println(this.name + ", there is a new update from channel");
	}

}



public class Main {
	public static void main(String[] args) {
	    YoutubeChannel obj = new YoutubeChannel();
	    Subscriber obj1 = new Subscriber("Yash");
	    Subscriber obj2 = new Subscriber("Ayush");
	    Subscriber obj3 = new Subscriber("Piyush");
	    obj.addSubscriber(obj1);
	    obj.addSubscriber(obj2);
	    obj.addSubscriber(obj3);
	    
	    obj.notifyObservers();
	}
}
