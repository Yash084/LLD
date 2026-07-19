import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

// ---------- Enums ----------

enum VehicleType {
    TWO_WHEELER, FOUR_WHEELER
}

// ---------- Vehicle ----------

class Vehicle {
    int vehicleNumber;
    VehicleType vehicleType;

    Vehicle(int vehicleNumber, VehicleType vehicleType) {
        this.vehicleNumber = vehicleNumber;
        this.vehicleType = vehicleType;
    }
}

// ---------- Parking Spot ----------

class ParkingSpot {
    int id;
    VehicleType vehicleType; // type of spot this slot is built for
    boolean isEmpty;
    Vehicle vehicle;

    ParkingSpot(int id, VehicleType vehicleType) {
        this.id = id;
        this.vehicleType = vehicleType;
        this.isEmpty = true;
        this.vehicle = null;
    }

    public void parkVehicle(Vehicle vehicle) {
        this.isEmpty = false;
        this.vehicle = vehicle;
    }

    public void removeVehicle() {
        this.isEmpty = true;
        this.vehicle = null;
    }
}

class TwoWheelerParkingSpot extends ParkingSpot {
    TwoWheelerParkingSpot(int id) {
        super(id, VehicleType.TWO_WHEELER);
    }
}

class FourWheelerParkingSpot extends ParkingSpot {
    FourWheelerParkingSpot(int id) {
        super(id, VehicleType.FOUR_WHEELER);
    }
}

// ---------- Parking Spot Manager ----------

class ParkingSpotManager {

    protected final List<ParkingSpot> parkingSpotList;

    ParkingSpotManager(List<ParkingSpot> parkingSpotList) {
        this.parkingSpotList = parkingSpotList;
    }

    public ParkingSpot findParkingSpace() {
        for (ParkingSpot spot : parkingSpotList) {
            if (spot.isEmpty) {
                return spot;
            }
        }
        return null;
    }

    public void addParkingSpot(ParkingSpot spot) {
        parkingSpotList.add(spot);
    }

    public void removeParkingSpot(ParkingSpot spot) {
        parkingSpotList.remove(spot);
    }

    public void parkVehicle(ParkingSpot spot, Vehicle vehicle) {
        spot.parkVehicle(vehicle);
    }

    public void removeVehicle(ParkingSpot spot) {
        spot.removeVehicle();
    }
}

class TwoWheelerParkingSpotManager extends ParkingSpotManager {
    TwoWheelerParkingSpotManager(List<ParkingSpot> spots) {
        super(spots);
    }
}

class FourWheelerParkingSpotManager extends ParkingSpotManager {
    FourWheelerParkingSpotManager(List<ParkingSpot> spots) {
        super(spots);
    }
}

// ---------- Parking Spot Manager Factory ----------

class ParkingSpotManagerFactory {

    private final ParkingSpotManager twoWheelerManager;
    private final ParkingSpotManager fourWheelerManager;

    ParkingSpotManagerFactory() {
        this.twoWheelerManager = new TwoWheelerParkingSpotManager(new ArrayList<>());
        this.fourWheelerManager = new FourWheelerParkingSpotManager(new ArrayList<>());
    }

    public ParkingSpotManager getParkingSpotManager(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER:
                return twoWheelerManager;
            case FOUR_WHEELER:
                return fourWheelerManager;
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
}

// ---------- Ticket ----------

class Ticket {
    int id;
    VehicleType vehicleType;
    LocalDateTime entryTime;
    ParkingSpot parkingSpot;
    Vehicle vehicle;

    Ticket(int id, Vehicle vehicle, ParkingSpot parkingSpot) {
        this.id = id;
        this.vehicle = vehicle;
        this.vehicleType = vehicle.vehicleType;
        this.parkingSpot = parkingSpot;
        this.entryTime = LocalDateTime.now();
    }
}

// ---------- Pricing Strategy ----------

interface PricingStrategy {
    double getPrice(LocalDateTime entryTime, LocalDateTime exitTime);
}

class HourlyPricingStrategy implements PricingStrategy {
    private static final double RATE_PER_HOUR = 20.0;

    @Override
    public double getPrice(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
        long hours = (long) Math.ceil(minutes / 60.0);
        hours = Math.max(hours, 1);
        return hours * RATE_PER_HOUR;
    }
}

class MinuteWisePricingStrategy implements PricingStrategy {
    private static final double RATE_PER_MINUTE = 1.5;

    @Override
    public double getPrice(LocalDateTime entryTime, LocalDateTime exitTime) {
        long minutes = ChronoUnit.MINUTES.between(entryTime, exitTime);
        minutes = Math.max(minutes, 1);
        return minutes * RATE_PER_MINUTE;
    }
}

// ---------- Cost Computation ----------

abstract class CostComputation {
    protected PricingStrategy pricingStrategy;

    CostComputation(PricingStrategy pricingStrategy) {
        this.pricingStrategy = pricingStrategy;
    }

    public double computeCost(LocalDateTime entryTime, LocalDateTime exitTime) {
        return pricingStrategy.getPrice(entryTime, exitTime);
    }
}

class TwoWheelerCostComputation extends CostComputation {
    TwoWheelerCostComputation() {
        super(new HourlyPricingStrategy());
    }
}

class FourWheelerCostComputation extends CostComputation {
    FourWheelerCostComputation() {
        super(new MinuteWisePricingStrategy());
    }
}

class CostComputationFactory {
    public CostComputation getCostComputationObject(VehicleType vehicleType) {
        switch (vehicleType) {
            case TWO_WHEELER:
                return new TwoWheelerCostComputation();
            case FOUR_WHEELER:
                return new FourWheelerCostComputation();
            default:
                throw new IllegalArgumentException("Unknown vehicle type: " + vehicleType);
        }
    }
}

// ---------- Payment Strategy ----------

interface PaymentStrategy {
    void makePayment(double amount);
}

class CreditCardPayment implements PaymentStrategy {
    @Override
    public void makePayment(double amount) {
        System.out.printf("Paid Rs.%.2f via Credit Card.%n", amount);
    }
}

// ---------- Entrance Gate ----------

class EntranceGate {
    private final ParkingSpotManagerFactory managerFactory;
    private int ticketCounter = 1;

    EntranceGate(ParkingSpotManagerFactory managerFactory) {
        this.managerFactory = managerFactory;
    }

    public ParkingSpot findParkingSpot(Vehicle vehicle) {
        ParkingSpotManager manager = managerFactory.getParkingSpotManager(vehicle.vehicleType);
        return manager.findParkingSpace();
    }

    public void bookParkingSpot(Vehicle vehicle, ParkingSpot spot) {
        ParkingSpotManager manager = managerFactory.getParkingSpotManager(vehicle.vehicleType);
        manager.parkVehicle(spot, vehicle);
    }

    public Ticket generateTicket(Vehicle vehicle, ParkingSpot spot) {
        return new Ticket(ticketCounter++, vehicle, spot);
    }
}

// ---------- Exit Gate ----------

class ExitGate {
    private final ParkingSpotManagerFactory managerFactory;
    private final CostComputationFactory costComputationFactory;

    ExitGate(ParkingSpotManagerFactory managerFactory, CostComputationFactory costComputationFactory) {
        this.managerFactory = managerFactory;
        this.costComputationFactory = costComputationFactory;
    }

    public double priceCalculation(Ticket ticket) {
        CostComputation costComputation = costComputationFactory.getCostComputationObject(ticket.vehicleType);
        LocalDateTime exitTime = LocalDateTime.now();
        return costComputation.computeCost(ticket.entryTime, exitTime);
    }

    public void makePayment(Ticket ticket, PaymentStrategy paymentStrategy) {
        double amount = priceCalculation(ticket);
        paymentStrategy.makePayment(amount);
        freeParkingSpot(ticket.parkingSpot, ticket.vehicleType);
    }

    public void freeParkingSpot(ParkingSpot spot, VehicleType vehicleType) {
        ParkingSpotManager manager = managerFactory.getParkingSpotManager(vehicleType);
        manager.removeVehicle(spot);
    }
}

// ---------- Main ----------

public class ParkingLotImpl {
    public static void main(String[] args) throws InterruptedException {

        ParkingSpotManagerFactory managerFactory = new ParkingSpotManagerFactory();

        // Set up parking spots
        ParkingSpotManager twoWheelerManager = managerFactory.getParkingSpotManager(VehicleType.TWO_WHEELER);
        ParkingSpotManager fourWheelerManager = managerFactory.getParkingSpotManager(VehicleType.FOUR_WHEELER);
        for (int i = 1; i <= 5; i++) {
            twoWheelerManager.addParkingSpot(new TwoWheelerParkingSpot(i));
        }
        for (int i = 1; i <= 5; i++) {
            fourWheelerManager.addParkingSpot(new FourWheelerParkingSpot(100 + i));
        }

        EntranceGate entranceGate = new EntranceGate(managerFactory);
        ExitGate exitGate = new ExitGate(managerFactory, new CostComputationFactory());

        // A four-wheeler arrives
        Vehicle car = new Vehicle(1234, VehicleType.FOUR_WHEELER);

        ParkingSpot spot = entranceGate.findParkingSpot(car);
        if (spot == null) {
            System.out.println("Sorry, no parking spot available.");
            return;
        }

        entranceGate.bookParkingSpot(car, spot);
        Ticket ticket = entranceGate.generateTicket(car, spot);
        System.out.println("Vehicle " + car.vehicleNumber + " parked at spot " + spot.id
                + ". Ticket #" + ticket.id + " issued at " + ticket.entryTime);

        // Simulate some time spent parked
        Thread.sleep(2000);

        // Vehicle goes to the exit gate and pays
        PaymentStrategy paymentStrategy = new CreditCardPayment();
        exitGate.makePayment(ticket, paymentStrategy);

        System.out.println("Spot " + spot.id + " is now free: " + spot.isEmpty);
    }
}
