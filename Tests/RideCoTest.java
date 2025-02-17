import Models.Driver;
import Models.Rider;
import Models.Ride;
import Services.BillingService;
import Services.RideService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RideCo application.
 */
public class RideCoTest {
    private RideService rideService; // Instance of RideService for testing

    @BeforeEach
    void setUp() {
        rideService = new RideService();
        rideService.addDriver("D1", 1, 1);
        rideService.addDriver("D2", 4, 5);
    }

    // Test adding a driver
    @Test
    void testAddDriver() {
        rideService.addDriver("D1", 2, 3);
        List<Driver> drivers = rideService.findNearestDrivers("R1");
        assertTrue(drivers.isEmpty(), "No riders exist yet, should return empty list");
    }

    // Test adding a rider
    @Test
    void testGetRider() {
        rideService.addRider("R1", 0, 0);
        Rider rider = rideService.getRider("R1");

        assertNotNull(rider, "Rider should exist");
        assertEquals("R1", rider.id, "Rider ID should match");
    }

    // Test finding nearest drivers
    @Test
    void testFindNearestDrivers() {
        rideService.addDriver("D1", 1, 1);
        rideService.addDriver("D2", 4, 5);
        rideService.addDriver("D3", 2, 2);
        rideService.addRider("R1", 0, 0);

        List<Driver> matchedDrivers = rideService.findNearestDrivers("R1");
        assertEquals(2, matchedDrivers.size(), "Should return 2 drivers within 5km range");
    }

    // Test starting a ride successfully
    @Test
    void testStartRide() {
        rideService.addDriver("D1", 1, 1);
        rideService.addRider("R1", 0, 0);
        rideService.findNearestDrivers("R1");

        boolean rideStarted = rideService.startRide("RIDE-001", 1, "R1");
        assertTrue(rideStarted, "Ride should start successfully");
    }

    // Test stopping a ride successfully
    @Test
    void testStopRide() {
        rideService.addDriver("D1", 1, 1);
        rideService.addRider("R1", 0, 0);
        rideService.startRide("RIDE-001", 1, "R1");

        boolean rideStopped = rideService.stopRide("RIDE-001", 3, 4, 15);
        assertTrue(rideStopped, "Ride should stop successfully");
    }

    // Test fare calculation (BillingService)
    @Test
    void testCalculateFare() {
        Rider rider = new Rider("R1", 0, 0);
        Driver driver = new Driver("D1", 1, 1);
        Ride ride = new Ride("RIDE-001", rider, driver);

        ride.endRide(3, 4, 20); // (3,4) is destination, 20 minutes

        double fare = BillingService.calculateFare(ride);
        assertEquals(147.0, fare, 0.1, "Fare calculation should be correct");
    }

    // Test fare when rider doesn't move (0 km)
    @Test
    void testZeroDistanceFare() {
        Rider rider = new Rider("R1", 0, 0);
        Driver driver = new Driver("D1", 0, 0);
        Ride ride = new Ride("RIDE-002", rider, driver);

        ride.endRide(0, 0, 10); // No movement, 10 minutes

        double fare = BillingService.calculateFare(ride);
        assertEquals(84.0, fare, 0.1, "Fare should include only time charge and tax");
    }

    // Test fare for a long-distance ride
    @Test
    void testLongDistanceFare() {
        Rider rider = new Rider("R1", 0, 0);
        Driver driver = new Driver("D1", 10, 10);
        Ride ride = new Ride("RIDE-003", rider, driver);

        ride.endRide(50, 50, 50); // Long distance ride

        double fare = BillingService.calculateFare(ride);
        assertTrue(fare > 700, "Fare should be high for long distances");
    }

    // Test stopping an invalid ride
    @Test
    void testStopInvalidRide() {
        boolean rideStopped = rideService.stopRide("RIDE-999", 5, 5, 20);
        assertFalse(rideStopped, "Should return false because the ride does not exist");
    }

    // Test adding a preferred driver successfully
    @Test
    void testAddPreferredDriver() {
        rideService.addDriver("D1", 1, 1);
        Driver driver = new Driver("D2", 2, 2);
        driver.addRating(3);
        driver.addRating(2);

        rideService.addDriver(driver.id, driver.x, driver.y); // Add D2 to service
        rideService.addRider("R1", 0, 0);
        rideService.preferDriver("R1", "D1");

        Rider rider = rideService.riders.get("R1");
        assertFalse(rider.isPreferredDriver("D2"), "Driver D2 should NOT be preferred");
    }

    // Test if preferred driver is prioritized
    @Test
    void testPreferredDriverMatching() {
        rideService.addDriver("D1", 1, 1);
        rideService.addDriver("D2", 2, 2);
        rideService.addRider("R1", 0, 0);

        Driver driverD1 = new Driver("D1", 2, 2);
        driverD1.addRating(5);
        driverD1.addRating(4);

        rideService.preferDriver("R1", "D1");

        List<Driver> matchedDrivers = rideService.findNearestDrivers("R1");
        assertFalse(matchedDrivers.isEmpty(), "Matched drivers should not be empty");
        assertEquals("D1", matchedDrivers.get(0).id, "Preferred driver D1 should be the first match");
    }

    // Test rejecting a driver with a low rating
    @Test
    void testRejectLowRatedDriverAsPreferred() {
        rideService.addDriver("D1", 1, 1);
        rideService.addRider("R1", 0, 0);

        Driver driver = new Driver("D1", 1, 1);
        driver.addRating(2);
        driver.addRating(3);

        rideService.preferDriver("R1", "D1");
        Rider rider = rideService.riders.get("R1");
        assertFalse(rider.isPreferredDriver("D1"), "Low-rated driver D1 should NOT be preferred");
    }
}
