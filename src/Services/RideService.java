package Services;

import Models.Driver;
import Models.Rider;
import Models.Ride;

import java.util.*;

/**
 * Service class to manage rides, drivers, and riders in the ride-sharing application.
 */
public class RideService {
    private final Map<String, Driver> drivers = new HashMap<>(); // Map to store drivers
    private final Map<String, Rider> riders = new HashMap<>(); // Map to store riders
    private final Map<String, Ride> rides = new HashMap<>(); // Map to store rides

    /**
     * Adds a new driver to the service.
     *
     * @param id Unique identifier for the driver
     * @param x  X-coordinate of the driver's location
     * @param y  Y-coordinate of the driver's location
     */
    public void addDriver(String id, int x, int y) {
        drivers.put(id, new Driver(id, x, y));
    }

    /**
     * Adds a new rider to the service.
     *
     * @param id Unique identifier for the rider
     * @param x  X-coordinate of the rider's location
     * @param y  Y-coordinate of the rider's location
     */
    public void addRider(String id, int x, int y) {
        riders.put(id, new Rider(id, x, y));
    }

    /**
     * Finds the nearest available drivers to a given rider.
     *
     * @param riderId The ID of the rider
     * @return A list of nearby available drivers
     */
    public List<Driver> findNearestDrivers(String riderId) {
        Rider rider = riders.get(riderId);
        if (rider == null) {
            return Collections.emptyList(); // Return empty list if rider not found
        }

        List<Driver> nearbyDrivers = new ArrayList<>();
        for (Driver driver : drivers.values()) {
            double distance = driver.distanceTo(rider);
            if (driver.isAvailable && distance <= 5.0) { // Include drivers exactly at 5km
                nearbyDrivers.add(driver);
            }
        }

        // Sort drivers by distance and then by ID
        nearbyDrivers.sort(Comparator
                .comparingDouble((Driver d) -> d.distanceTo(rider))
                .thenComparing(d -> d.id));

        return nearbyDrivers.subList(0, Math.min(5, nearbyDrivers.size())); // Return up to 5 nearest drivers
    }

    /**
     * Starts a ride for a given rider with a specified driver.
     *
     * @param rideId   Unique identifier for the ride
     * @param n        The index of the driver to choose from the nearest drivers
     * @param riderId  The ID of the rider
     * @return True if the ride started successfully, false otherwise
     */
    public boolean startRide(String rideId, int n, String riderId) {
        List<Driver> matchedDrivers = findNearestDrivers(riderId);
        if (matchedDrivers.size() < n || rides.containsKey(rideId)) {
            System.out.println("INVALID_RIDE");
            return false; // Invalid ride if not enough drivers or ride already exists
        }

        Driver chosenDriver = matchedDrivers.get(n - 1);
        chosenDriver.isAvailable = false; // Mark driver as unavailable
        Ride ride = new Ride(rideId, riders.get(riderId), chosenDriver);
        rides.put(rideId, ride); // Store the ride
        System.out.println("RIDE_STARTED " + rideId);
        return true; // Ride started successfully
    }

    /**
     * Stops a ride and marks the driver as available again.
     *
     * @param rideId Unique identifier for the ride
     * @param x      Ending X-coordinate of the ride
     * @param y      Ending Y-coordinate of the ride
     * @param duration Duration of the ride in minutes
     * @return True if the ride stopped successfully, false otherwise
     */
    public boolean stopRide(String rideId, int x, int y, int duration) {
        Ride ride = rides.get(rideId);
        if (ride == null || !ride.isActive) {
            System.out.println("INVALID_RIDE");
            return false; // Invalid ride if not found or already completed
        }

        ride.endRide(x, y, duration); // End the ride
        ride.driver.isAvailable = true; // Mark driver as available
        System.out.println("RIDE_STOPPED " + rideId);
        return true; // Ride stopped successfully
    }

    /**
     * Generates a bill for a completed ride.
     *
     * @param rideId Unique identifier for the ride
     */
    public void generateBill(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null || ride.isActive) {
            System.out.println("INVALID_RIDE");
            return; // Invalid ride if not found or still active
        }

        double fare = BillingService.calculateFare(ride); // Calculate fare
        System.out.printf("BILL %s %s %.2f%n", rideId, ride.driver.id, fare); // Print bill
    }
}