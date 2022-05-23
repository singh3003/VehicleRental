package model;

import interfaces.RentalPriceStrategy;

import java.util.*;

public class Branch {
    public String branchId;
    public Map<String, Long> availableVehicleCount;

    public Map<String, List<Vehicle>> availableVehicleMap;
    public Map<String, List<Vehicle>> bookedVehicleMap;
    public Set<String> vehicleType;

    public Branch(String branchId) {
        this.branchId = branchId;
        this.availableVehicleCount = new LinkedHashMap<>();
        this.availableVehicleMap = new LinkedHashMap<>();
        this.bookedVehicleMap = new LinkedHashMap<>();
        this.vehicleType = new HashSet<>();
    }

    public void addVehicleType(List<String> vehicleList) {
        vehicleType.addAll(vehicleList);
        for(String name : vehicleList){
            availableVehicleCount.put(name, 0L);
            availableVehicleMap.put(name, new ArrayList<>());
            bookedVehicleMap.put(name, new ArrayList<>());
        }

    }


    public String addVehicle(String type, String id, Double price) {
        if(availableVehicleMap.containsKey(id) || !vehicleType.contains(type)){
            return "FALSE";
        }

        Long count = availableVehicleCount.get(type);
        count++;
        availableVehicleCount.put(type, count);
        Vehicle vehicle = new Vehicle(id, price, type);
        List<Vehicle> vehicleList =  availableVehicleMap.get(type);
        vehicleList.add(vehicle);
        return "true";
    }


    public Set<String> getVehicleTypes(){
        return vehicleType;
    }

    public int getAvailableVehicleMapByNameAndStrategy(String name, RentalPriceStrategy rentalPriceStrategy){
        List<Vehicle> vehicles = availableVehicleMap.get(name);
        return rentalPriceStrategy.getVehicle(vehicles);
    }

    public List<Vehicle> getAvailableVehicleMapByName(String name){
        return availableVehicleMap.get(name);
    }

    public Map<String, List<Vehicle>> getAvailableVehicleMap(){
        return availableVehicleMap;
    }

    public void setBookedVehicleMap(String type, Vehicle vehicle){
        List<Vehicle> vehicles = bookedVehicleMap.get(type);
        vehicles.add(vehicle);
        bookedVehicleMap.put(type, vehicles);
    }

    public boolean isVehicleAvailableForRent(String name) {
        if(!vehicleType.contains(name)){
            return false;
        }
        return availableVehicleCount.get(name) != 0;
    }

    public Long getVehicleCount(String vehicleType){
        return availableVehicleCount.get(vehicleType);
    }

    public void setVehicleCount(String vehicleType, Long count){
         availableVehicleCount.put(vehicleType, count);
    }

}
