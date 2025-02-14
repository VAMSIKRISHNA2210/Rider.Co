import Models.Driver;
import Models.Rider;
import Models.Ride;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import Services.BillingService;
import Services.RideService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for the RideCo application.
 */
public class RideCoTest {
    private RideService rideService; // Instance of RideService for testing

    @BeforeEach
    void setUp() {
        rideService = new RideService(); // Initialize RideService before each test
    }

    // 1. Test adding a driver
    @Test
    void testAddDriver() {
        rideService.addDriver("D1", 2, 3);
        List<Driver> drivers = rideService.findNearestDrivers("R1");
        assertTrue(drivers.isEmpty(), "No riders exist yet, should return empty list");
    }

    // 2. Test adding a rider
    @Test
    void testAddRider() {
        rideService.addRider("R1", 0, 0);
        assertNotNull(rideService.findNearestDrivers("R1"), "Rider should be added successfully");
    }

    // 3. Test finding nearest drivers
    @Test
    void testFindNearestDrivers() {
        rideService.addDriver("D1", 1, 1);
        rideService.addDriver("D2", 4, 5);
        rideService.addDriver("D3", 2, 2);
        rideService.addRider("R1", 0, 0);

        List<Driver> matchedDrivers = rideService.findNearestDrivers("R1");
        assertEquals(2, matchedDrivers.size(), "Should return 2 drivers within 5km range");
    }

    // 4. Test starting a ride successfully
    @Test
    void testStartRide() {
        rideService.addDriver("D1", 1, 1);
        rideService.addRider("R1", 0, 0);
        rideService.findNearestDrivers("R1");

        boolean rideStarted = rideService.startRide("RIDE-001", 1, "R1");
        assertTrue(rideStarted, "Ride should start successfully");
    }

    // 5. Test starting a ride with no drivers (invalid case)
    @Test
    void testStartRideInvalid() {
        rideService.addRider("R1", 0, 0);
        boolean rideStarted = rideService.startRide("RIDE-001", 1, "R1");
        assertFalse(rideStarted, "Ride should fail because no drivers exist");
    }

    // 6. Test stopping a ride successfully
    @Test
    void testStopRide() {
        rideService.addDriver("D1", 1, 1);
        rideService.addRider("R1", 0, 0);
        rideService.startRide("RIDE-001", 1, "R1");

        boolean rideStopped = rideService.stopRide("RIDE-001", 3, 4, 15);
        assertTrue(rideStopped, "Ride should stop successfully");
    }

    // 7. Test fare calculation (BillingService)
    @Test
    void testCalculateFare() {
        Rider rider = new Rider("R1", 0, 0);
        Driver driver = new Driver("D1", 1, 1);
        Ride ride = new Ride("RIDE-001", rider, driver);

        ride.endRide(3, 4, 20);  // (3,4) is destination, 20 mins

        double fare = BillingService.calculateFare(ride);
        assertEquals(147.0, fare, 0.1, "Fare calculation should be correct");
    }

    // 8. Test fare when rider doesn't move (0 km)
    @Test
    void testZeroDistanceFare() {
        Rider rider = new Rider("R1", 0, 0);
        Driver driver = new Driver("D1", 0, 0);
        Ride ride = new Ride("RIDE-002", rider, driver);

        ride.endRide(0, 0, 10);  // No movement, 10 mins

        double fare = BillingService.calculateFare(ride);
        assertEquals(84.0, fare, 0.1, "Fare should include only time charge and tax");
    }

    // 9. Test fare for a long-distance ride
    @Test
    void testLongDistanceFare() {
        Rider rider = new Rider("R1", 0, 0);
        Driver driver = new Driver("D1", 10, 10);
        Ride ride = new Ride("RIDE-003", rider, driver);

        ride.endRide(50, 50, 50); // Long distance ride

        double fare = BillingService.calculateFare(ride);
        assertTrue(fare > 700, "Fare should be high for long distances");
    }

    // 10. Test stopping an invalid ride
    @Test
    void testStopInvalidRide() {
        boolean rideStopped = rideService.stopRide("RIDE-999", 5, 5, 20);
        assertFalse(rideStopped, "Should return false because the ride does not exist");
    }
}