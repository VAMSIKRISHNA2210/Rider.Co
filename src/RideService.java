import java.util.*;

class RideService {
    private final Map<String, Driver> drivers = new HashMap<>();
    private final Map<String, Rider> riders = new HashMap<>();
    private final Map<String, Ride> rides = new HashMap<>();

    public void addDriver(String id, int x, int y) {
        drivers.put(id, new Driver(id, x, y));
    }

    public void addRider(String id, int x, int y) {
        riders.put(id, new Rider(id, x, y));
    }

    public List<Driver> findNearestDrivers(String riderId) {
        Rider rider = riders.get(riderId);
        if (rider == null) {
            return Collections.emptyList();
        }

        List<Driver> nearbyDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            if (driver.isAvailable && driver.distanceTo(rider) <= 5) {
                nearbyDrivers.add(driver);
            }
        }

        nearbyDrivers.sort(Comparator
                .comparingDouble((Driver d) -> d.distanceTo(rider))
                .thenComparing(d -> d.id));

        return nearbyDrivers.subList(0, Math.min(5, nearbyDrivers.size()));
    }

    public boolean startRide(String rideId, int n, String riderId) {
        List<Driver> matchedDrivers = findNearestDrivers(riderId);
        if (matchedDrivers.size() < n || rides.containsKey(rideId)) {
            System.out.println("INVALID_RIDE");
            return false;
        }

        Driver chosenDriver = matchedDrivers.get(n - 1);
        chosenDriver.isAvailable = false;
        Ride ride = new Ride(rideId, riders.get(riderId), chosenDriver);
        rides.put(rideId, ride);
        System.out.println("RIDE_STARTED " + rideId);
        return true;
    }

    public boolean stopRide(String rideId, int x, int y, int duration) {
        Ride ride = rides.get(rideId);
        if (ride == null || !ride.isActive) {
            System.out.println("INVALID_RIDE");
            return false;
        }

        ride.endRide(x, y, duration);
        ride.driver.isAvailable = true;
        System.out.println("RIDE_STOPPED " + rideId);
        return true;
    }

    public void generateBill(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null || ride.isActive) {
            System.out.println("INVALID_RIDE");
            return;
        }

        double fare = BillingService.calculateFare(ride);
        System.out.printf("BILL %s %s %.2f%n", rideId, ride.driver.id, fare);
    }
}
