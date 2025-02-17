package Services;

import Models.Ride;

public class BillingService {
    private static final double BASE_FARE = 50.0; // Base fare for the ride
    private static final double PER_KM_RATE = 6.5; // Rate per kilometer
    private static final double PER_MIN_RATE = 2.0; // Rate per minute
    private static final double TAX_RATE = 0.2; // Tax rate

    // Calculates the fare for a given ride
    public static double calculateFare(Ride ride) {
        double distance = Math.sqrt(Math.pow(ride.endX - ride.startX, 2) + Math.pow(ride.endY - ride.startY, 2));
        distance = Math.round(distance * 100.0) / 100.0; // Round to 2 decimal places

        // Ensure that distance is not negative
        if (distance < 0.01) {
            distance = 0;
        }

        // Calculate fare components
        double distanceCharge = distance * PER_KM_RATE;
        double timeCharge = ride.duration * PER_MIN_RATE;
        double fareBeforeTax = BASE_FARE + distanceCharge + timeCharge;

        // Round before tax
        fareBeforeTax = Math.round(fareBeforeTax * 100.0) / 100.0;

        // Apply tax
        double totalFare = fareBeforeTax * (1 + TAX_RATE);
        totalFare = Math.round(totalFare * 100.0) / 100.0;

        return totalFare; // Return the total fare
    }
}
