package Services;

import Models.Ride;

/**
 * Service class to handle billing calculations for rides.
 */
public class BillingService {
    private static final double BASE_FARE = 50.0; // Base fare for every ride
    private static final double PER_KM_RATE = 6.5; // Rate per kilometer
    private static final double PER_MIN_RATE = 2.0; // Rate per minute
    private static final double TAX_RATE = 0.2; // Tax rate

    /**
     * Calculates the fare for a given ride.
     *
     * @param ride The ride for which to calculate the fare
     * @return The total fare including base fare, distance, duration, and tax
     */
    public static double calculateFare(Ride ride) {
        // Calculate distance using the Euclidean formula
        double distance = Math.sqrt(Math.pow(ride.endX - ride.startX, 2) + Math.pow(ride.endY - ride.startY, 2));
        distance = Math.round(distance * 100.0) / 100.0; // Round to 2 decimal places

        // Calculate fare components
        double fare = BASE_FARE + (distance * PER_KM_RATE) + (ride.duration * PER_MIN_RATE);
        fare = Math.round(fare * 100.0) / 100.0; // Fix floating point precision

        // Apply tax
        double totalFare = fare * (1 + TAX_RATE);
        totalFare = Math.round(totalFare * 100.0) / 100.0; // Round total fare

        return totalFare; // Return the total fare
    }
}