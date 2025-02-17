package Services;

import Models.Driver;
import Models.Rider;
import Models.Ride;

import java.util.*;

public class RideService {
    public final Map<String, Driver> drivers = new HashMap<>(); // Map of drivers
    public final Map<String, Rider> riders = new HashMap<>(); // Map of riders
    public final Map<String, Ride> rides = new HashMap<>(); // Map of rides

    // Adds a new driver to the service
    public void addDriver(String id, int x, int y) {
        drivers.put(id, new Driver(id, x, y));
    }

    // Adds a new rider to the service
    public void addRider(String id, int x, int y) {
        riders.put(id, new Rider(id, x, y));
    }

    // Retrieves a rider by ID
    public Rider getRider(String riderId) {
        return riders.get(riderId);
    }

    // Finds the nearest available drivers to a rider
    public List<Driver> findNearestDrivers(String riderId) {
        Rider rider = riders.get(riderId);
        if (rider == null) {
            return Collections.emptyList();
        }

        // Lists to hold preferred and other drivers
        List<Driver> preferredDrivers = new ArrayList<>();
        List<Driver> otherDrivers = new ArrayList<>();

        // Loop through all available drivers to find those near the rider
        for (Driver driver : drivers.values()) {
            double distance = driver.distanceTo(rider);
            if (driver.isAvailable && distance <= 5.0) {
                if (rider.isPreferredDriver(driver.id)) {
                    preferredDrivers.add(driver);  // If the driver is a preferred driver for the rider, add to preferred list
                } else {
                    otherDrivers.add(driver);
                }
            }
        }

        // Sort preferred drivers by distance to the rider
        preferredDrivers.sort(Comparator.comparingDouble(d -> d.distanceTo(rider)));
        // Sort other drivers by distance to the rider
        otherDrivers.sort(Comparator.comparingDouble(d -> d.distanceTo(rider)));

        // Combine both lists: preferred drivers first, then others
        List<Driver> sortedDrivers = new ArrayList<>(preferredDrivers);
        sortedDrivers.addAll(otherDrivers);

        // Return a sublist of up to 5 nearest drivers
        return sortedDrivers.subList(0, Math.min(5, sortedDrivers.size()));
    }

    // Starts a ride for a rider with a specified driver
    public boolean startRide(String rideId, int n, String riderId) {
        List<Driver> matchedDrivers = findNearestDrivers(riderId);

        // Check if no drivers are available
        if (matchedDrivers.isEmpty()) {
            System.out.println("NO_DRIVERS_AVAILABLE");
            return false;
        }

        // Check if requested driver index is valid or ride already exists
        if (matchedDrivers.size() < n || rides.containsKey(rideId)) {
            System.out.println("INVALID_RIDE");
            return false;
        }

        // Select the driver based on the given index (n)
        Driver chosenDriver = matchedDrivers.get(n - 1);
        chosenDriver.isAvailable = false; // Mark the driver as unavailable
        Ride ride = new Ride(rideId, riders.get(riderId), chosenDriver);
        rides.put(rideId, ride); // Add the ride to the rides collection
        System.out.println("RIDE_STARTED " + rideId);
        return true;
    }

    // Adds a driver to a rider's preferred list if eligible
    public void preferDriver(String riderId, String driverId) {
        Rider rider = riders.get(riderId);
        Driver driver = drivers.get(driverId);

        // Check if both rider and driver exist
        if (rider != null && driver != null) {
            double averageRating = driver.getAverageRating();
            if (averageRating >= 4.0) {
                rider.addPreferredDriver(driverId); // Add driver to preferred list
                System.out.println("PREFERRED_DRIVER_ADDED " + driverId + " for " + riderId);
            } else {
                System.out.println("DRIVER_NOT_ELIGIBLE");
            }
        } else {
            System.out.println("INVALID_PREFERENCE");
        }
    }

    // Stops a ride and marks the driver as available
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

    // Generates a bill for a completed ride
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
