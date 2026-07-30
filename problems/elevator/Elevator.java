import java.util.*;
/*

elevator-system/
│
├── enums/
│   ├── Direction.java
│   └── State.java
│
├── requests/
│   ├── ExternalRequest.java
│   └── InternalRequest.java
│
├── model/
│   └── Elevator.java
│
├── strategy/
│   ├── ElevatorDispatchStrategy.java
│   ├── NearestElevatorStrategy.java
│   ├── OddEvenStrategy.java
│   └── FixedFloorStrategy.java
│
├── controller/
│   └── ElevatorController.java
│
├── actor/
│   └── User.java
│
└── Main.java

*/

enum Direction {
    UP,
    DOWN,
    IDLE
}

enum State {
    IDLE,
    MOVING,
    OUT_OF_SERVICE
}

class ExternalRequest {

    private final int floor;
    private final Direction direction;

    public ExternalRequest(int floor, Direction direction) {
        this.floor = floor;
        this.direction = direction;
    }

    public int getFloor() {
        return floor;
    }

    public Direction getDirection() {
        return direction;
    }
}

class InternalRequest {

    private final int destinationFloor;

    public InternalRequest(int destinationFloor) {
        this.destinationFloor = destinationFloor;
    }

    public int getDestinationFloor() {
        return destinationFloor;
    }
}


class Elevator {
    private final int id;
    private int currentFloor;
    private Direction direction;
    private State state;
    private Queue<InternalRequest> requests;

    public Elevator(int id) {
        this.id = id;
        this.currentFloor = 0;
        this.direction = Direction.IDLE;
        this.state = State.IDLE;
        this.requests = new LinkedList<>();
    }

    public void addRequest(InternalRequest request) {
        requests.offer(request);
        if(state == State.IDLE)
            state = State.MOVING;
    }

    public void processRequests() {
        while(!requests.isEmpty()) {
            InternalRequest request = requests.poll();
            moveToFloor(request.getDestinationFloor());
            System.out.println("Elevator " + id + " reached floor " + currentFloor);
        }
        direction = Direction.IDLE;
        state = State.IDLE;
    }

    private void moveToFloor(int destinationFloor) {
        if(destinationFloor > currentFloor) {
            direction = Direction.UP;
            while(currentFloor < destinationFloor) {
                currentFloor++;
                System.out.println("Elevator "+ id + " -> Floor " + currentFloor);
            }
        }
        else if(destinationFloor < currentFloor) {
            direction = Direction.DOWN;
            while(currentFloor > destinationFloor) {
                currentFloor--;
                System.out.println("Elevator "  + id + " -> Floor " + currentFloor);
            }
        }
        direction = Direction.IDLE;
    }

    public int getId() {
        return id;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public Direction getDirection() {
        return direction;
    }

    public State getState() {
        return state;
    }

}


interface ElevatorDispatchStrategy {
    Elevator getElevator(ExternalRequest request, List<Elevator> elevators);
}


class NearestElevatorStrategy implements ElevatorDispatchStrategy {

    @Override
    public Elevator getElevator( ExternalRequest request,  List<Elevator> elevators) {
        Elevator nearest = null;
        int minimumDistance = Integer.MAX_VALUE;
        for(Elevator elevator : elevators) {
            if(elevator.getState() == State.OUT_OF_SERVICE)
                continue;
            int distance = Math.abs(elevator.getCurrentFloor()  - request.getFloor());
            if(distance < minimumDistance){
                minimumDistance = distance;
                nearest = elevator;
            }
        }
        return nearest;
    }
}

class ElevatorController {

    private List<Elevator> elevators;

    private ElevatorDispatchStrategy dispatchStrategy;

    public ElevatorController(ElevatorDispatchStrategy dispatchStrategy) {

        this.dispatchStrategy = dispatchStrategy;
        this.elevators = new ArrayList<>();
    }

    public void addElevator(Elevator elevator) {
        elevators.add(elevator);
    }
  
    public void removeElevator(Elevator elevator) {
        elevators.remove(elevator);
    }
  
    public void requestElevator(ExternalRequest request) {
        Elevator elevator = dispatchStrategy.getElevator( request, elevators);
        if(elevator == null){
            System.out.println("No elevator available.");
            return;
        }
        System.out.println(  "Elevator "    + elevator.getId()   + " selected.");
        elevator.addRequest( new InternalRequest(request.getFloor()));
        elevator.processRequests();
    }
    public List<Elevator> getElevators() {
        return elevators;
    }

}

class User {

    private final ElevatorController controller;

    public User(ElevatorController controller) {
        this.controller = controller;
    }

    public void pressButton(int floor, Direction direction) {
        System.out.println( "User pressed "   + direction + " at Floor " + floor);
        ExternalRequest request = new ExternalRequest( floor, direction );
        controller.requestElevator(request);
    }
}

public class Main {

    public static void main(String[] args) {

        System.out.println("Starting Elevator System");

        ElevatorDispatchStrategy strategy = new NearestElevatorStrategy();

        ElevatorController controller = new ElevatorController(strategy);

        Elevator e1 = new Elevator(1);
        Elevator e2 = new Elevator(2);
        Elevator e3 = new Elevator(3);

        controller.addElevator(e1);
        controller.addElevator(e2);
        controller.addElevator(e3);
      
        User yash = new User(controller);
        yash.pressButton(5, Direction.UP);

        yash.pressButton(8, Direction.DOWN);

        yash.pressButton(2, Direction.UP);
    }
}
