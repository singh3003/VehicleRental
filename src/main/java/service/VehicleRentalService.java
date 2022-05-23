package service;

import model.Branch;
import interfaces.RentalPriceStrategy;
import model.Vehicle;

import java.util.*;

public class VehicleRentalService {

    Map<String, Branch> branchMap;

    public VehicleRentalService() {
        this.branchMap = new LinkedHashMap<>();
    }


    public String onboardBranch(String branchName, List<String> vehicleList) {
        if (branchMap.containsKey(branchName)) {
            return "FALSE";
        }
        //adding new branch
        Branch branch = new Branch(branchName);
        branch.addVehicleType(vehicleList);
        branchMap.put(branchName, branch);
        return "TRUE";
    }


    public String onboardVehicle(String branchName, String vehicleType, Double price, String vehicleId) {
        //if branch doesn't exist
        if (!branchMap.containsKey(branchName)) return "FALSE";

        Branch branch = branchMap.get(branchName);

        //if vehicle type doesn't exist for that branch
        if (!branch.getVehicleTypes().contains(vehicleType)) return "FALSE";

        String isVehicleAdded = branch.addVehicle(vehicleType, vehicleId, price);
        if (isVehicleAdded.equals("TRUE")) {
            branchMap.put(branchName, branch);
        }
        return isVehicleAdded;
    }


    public Long bookVehicle(String branchName, String vehicleType, Long startTime, Long endTime, RentalPriceStrategy rentalPriceStrategy) {
        //if branch doesn't exist
        if (!branchMap.containsKey(branchName)) return (long) -1;

        Branch branch = branchMap.get(branchName);

        //if vehicle type doesn't exist for that branch || If vehicle is not available for rent of that particular type
        if (!branch.getVehicleTypes().contains(vehicleType) || !branch.isVehicleAvailableForRent(vehicleType))
            return (long) -1;

        // Dynamic pricing  demand vs supply. If 80% of cars in a particular branch are booked, increase the price by 10%.
        if (branch.availableVehicleCount.containsKey("CAR")) {
            long count = branch.availableVehicleCount.get("CAR");
            long totalCount = (long) (branch.availableVehicleMap.get("CAR").size() + branch.bookedVehicleMap.get("CAR").size());
            if ((totalCount - totalCount * 0.8) >= count) {
                Map<String, List<Vehicle>> availableVehicleMap = branch.availableVehicleMap;
                availableVehicleMap.forEach((key, vehicles) -> {
                    for (Vehicle vehicle : vehicles) {
                        vehicle.setPrice(vehicle.getPrice() * 0.1 + vehicle.getPrice());
                    }
                });
            }

        }


        //get the vehicle that need to be booked based on the strategy
        return getVehicleToBeBooked(vehicleType, branch,rentalPriceStrategy, endTime, startTime);


    }

    public Long getVehicleToBeBooked(String vehicleType, Branch branch, RentalPriceStrategy rentalPriceStrategy, Long endTime, Long startTime){
        int index = branch.getAvailableVehicleMapByNameAndStrategy(vehicleType, rentalPriceStrategy);
        List<Vehicle>  stringVehicleMap = branch.getAvailableVehicleMapByName(vehicleType);
        Vehicle vehicle = stringVehicleMap.get(index);
        double totalFare = (endTime - startTime) * vehicle.getPrice();
        stringVehicleMap.remove(vehicle);

        //decrementing available vehicle count
        markVehicleAsBooked(vehicle, vehicleType, branch);
        return (long)totalFare;
    }

    private void markVehicleAsBooked(Vehicle vehicle, String vehicleType, Branch branch) {
        branch.setBookedVehicleMap(vehicleType, vehicle);
        Long count = branch.getVehicleCount(vehicleType);
        count--;
        branch.setVehicleCount(vehicleType, count);
    }

    public Map<Double, String> displayVehicles(){
        Map<Double, String> vehicleMap = new TreeMap<>();
        branchMap.forEach((key, branch) -> {
            Map<String, List<Vehicle>> vehicles = branch.getAvailableVehicleMap();
            vehicles.forEach((key1, vehicleList) -> {
                for (Vehicle vehicle : vehicleList) {
                    vehicleMap.put(vehicle.getPrice(), vehicle.getId());
                }
            });

        });
        return vehicleMap;
    }

}
