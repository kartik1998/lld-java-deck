package null_design_pattern;

public class VehicleFactory {
    public static VehicleInterface getVehicle(String type) {
        switch (type.toLowerCase()) {
            case "car":
                return new Car();
            case "bike":
                return new Bike();
            default:
                return new NullVehicle();
        }
    }
}
