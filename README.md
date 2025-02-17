# RideCo - Ride-Sharing Application

## Table of Contents

- [Features](#features)
- [Technologies Used](#technologies-used)
- [Project Structure](#project-structure)
- [Code Overview](#code-overview)
  - [Models](#models)
  - [Services](#services)
  - [Main Application](#main-application)
  - [Testing](#testing)
- [Usage](#usage)
- [Testing](#testing-1)
- [Contributing](#contributing)
- [License](#license)

## Features

- **Driver Management**: Add drivers with unique IDs and coordinates, and manage their availability.
- **Rider Management**: Add riders with unique IDs and coordinates, and allow them to prefer certain drivers.
- **Ride Matching**: Find the nearest available drivers for a rider based on their location.
- **Ride Management**: Start and stop rides, tracking the duration and coordinates.
- **Fare Calculation**: Calculate the fare based on distance traveled and duration of the ride, including tax.
- **Bill Generation**: Generate a detailed bill for completed rides.

## Technologies Used

- **Java**: The primary programming language used for the application.
- **JUnit**: A testing framework used for unit testing the application.

## Project Structure
. ├── Models │ ├── Driver.java │ ├── Rider.java │ └── Ride.java ├── Services │ ├── BillingService.java │ └── RideService.java ├── Main │ └── RideCo.java └── RideCoTest.java


- **Models**: Contains the core classes representing the entities in the application.
  - `Driver.java`: Represents a driver with attributes like ID, coordinates, availability, and ratings.
  - `Rider.java`: Represents a rider with attributes like ID, coordinates, and preferred drivers.
  - `Ride.java`: Represents a ride with attributes like ride ID, associated rider and driver, coordinates, duration, and status.

- **Services**: Contains the business logic for managing rides and billing.
  - `BillingService.java`: Contains methods for calculating the fare for a ride based on distance and duration.
  - `RideService.java`: Manages the overall ride-sharing logic, including adding drivers and riders, matching them, and handling ride operations.

- **Main**: Contains the main class to run the application.
  - `RideCo.java`: The entry point of the application, handling user input and executing commands.

- **RideCoTest.java**: Contains unit tests for the application, ensuring the functionality works as expected.

## Code Overview

### Models

1. **Driver**
   - Represents a driver with a unique ID, coordinates (x, y), availability status, and a list of ratings.
   - Methods:
     - `addRating(int rating)`: Adds a rating to the driver if it's between 1 and 5.
     - `getAverageRating()`: Calculates and returns the average rating of the driver.
     - `distanceTo(Rider rider)`: Calculates the distance from the driver to a specified rider.

2. **Rider**
   - Represents a rider with a unique ID, coordinates (x, y), and a set of preferred driver IDs.
   - Methods:
     - `addPreferredDriver(String driverId)`: Adds a driver to the rider's preferred list.
     - `isPreferredDriver(String driverId)`: Checks if a driver is in the rider's preferred list.

3. **Ride**
   - Represents a ride with a unique ride ID, associated rider and driver, starting and ending coordinates, duration, and status.
   - Methods:
     - `endRide(int endX, int endY, int duration)`: Ends the ride and sets the ending coordinates and duration.

### Services

1. **BillingService**
   - Contains methods for calculating the fare for a ride based on distance, duration, and tax.
   - Method:
     - `calculateFare(Ride ride)`: Calculates the total fare for a completed ride.

2. **RideService**
   - Manages the overall ride-sharing logic, including adding drivers and riders, finding nearest drivers, starting and stopping rides, and generating bills.
   - Methods:
     - `addDriver(String id, int x, int y)`: Adds a new driver to the service.
     - `addRider(String id, int x, int y)`: Adds a new rider to the service.
     - `findNearestDrivers(String riderId)`: Finds and returns a list of the nearest available drivers for a rider.
     - `startRide(String rideId, int n, String riderId)`: Starts a ride for a rider with a specified driver.
     - `stopRide(String rideId, int x, int y, int duration)`: Stops a ride and marks the driver as available.
     - `generateBill(String rideId)`: Generates a bill for a completed ride.

### Main Application

- **RideCo**
  - The main class that runs the application, processes user commands, and interacts with the `RideService`.
  - Commands include adding drivers and riders, matching drivers, starting and stopping rides, and generating bills.

