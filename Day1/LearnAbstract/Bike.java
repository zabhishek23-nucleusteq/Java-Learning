package Day1.LearnAbstract;

public class Bike extends Vehicle {

    @Override
    public String vehicleAverage() {
        return "Bike : Gives an Average of 60km/L";
    }

    @Override
    public String startEngine() {
        return "Bike : Engine Starts";
    }

    @Override
    public String stopEngine() {
        return "Bike : Engine Stop";
    }
}
