package service;

import interfaces.RentalPriceStrategy;
import model.Vehicle;

import java.util.List;

public class LowestPriceRentalStrategy implements RentalPriceStrategy {


    public int getVehicle(List<Vehicle> val) {
        double min = Integer.MAX_VALUE;
        int index = 0;
        for(int i = 0;i< val.size();i++){
            double curr = Math.min(min, val.get(i).getPrice());
            if(min> curr){
                min = curr;
                index= i;
            }
        }
        return index;
    }
}
