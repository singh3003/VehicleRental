package model;

public class Vehicle {

    public String id;
    Double price;
    String vehicleType;


    public Vehicle(String id, Double price, String vehicleType) {
        this.id = id;
        this.price = price;
        this.vehicleType = vehicleType;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


}
