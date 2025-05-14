package Day1.LearnAbstract;

public class Main {
    public static void main(String[] args){
        Vehicle car = new Car();
        Vehicle bike = new Bike();
        System.out.println(car.vehicleAverage());
        System.out.println(car.startEngine());
        System.out.println(car.stopEngine());
        System.out.println(bike.vehicleAverage());
        System.out.println(bike.startEngine());
        System.out.println(bike.stopEngine());
    }
}
