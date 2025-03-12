package null_design_pattern;

public class Main {
    public static void main(String[] args) {
        VehicleInterface v1 = VehicleFactory.getVehicle("car");
        VehicleInterface v2 = VehicleFactory.getVehicle("bike");
        VehicleInterface v3 = VehicleFactory.getVehicle("");
        // don't have to worry about a null pointer exception here
        v3.printType();
        v1.printType();
    }
}
