package Day1.LearnAbstract;

public class Car extends Vehicle{
    @Override
    public String vehicleAverage() {
        return "Car : Gives an Average of 20km/L";
    }

    @Override
    public String startEngine() {
        return "Car : Engine Starts";
    }

    @Override
    public String stopEngine() {
        return "Car : Engine Stop";
    }
}
