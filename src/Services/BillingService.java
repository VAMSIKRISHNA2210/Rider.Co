package Services;

import Models.Ride;

public class BillingService {
    private static final double BASE_FARE = 50.0;
    private static final double PER_KM_RATE = 6.5;
    private static final double PER_MIN_RATE = 2.0;
    private static final double TAX_RATE = 0.2;

    public static double calculateFare(Ride ride) {
        double distance = Math.sqrt(Math.pow(ride.endX - ride.startX, 2) + Math.pow(ride.endY - ride.startY, 2));
        double fare = BASE_FARE + (distance * PER_KM_RATE) + (ride.duration * PER_MIN_RATE);
        return fare + (fare * TAX_RATE);
    }
}
