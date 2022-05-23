package interfaces;

import model.Vehicle;

import java.util.List;

public interface RentalPriceStrategy {


    int getVehicle(List<Vehicle> vehicles);
}
