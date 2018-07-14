package specials_summary;

import specials_summary.dto.Vehicle;

import java.util.LinkedList;
import java.util.List;

public class DefaultVehicleService implements VehiclesService {

    @Override
    public List<Vehicle> load(String webId, String locale) {

        List<Vehicle> vehicles = new LinkedList<>();
        {
            Vehicle vehicle = new Vehicle();
            vehicle.setCategory("new");
            vehicle.setYear("2018");
            vehicle.setMake("Chevrolet");
            vehicle.setModel("Camaro");
            vehicle.setTrim("LX");

            vehicles.add(vehicle);
        }
        {
            Vehicle vehicle = new Vehicle();
            vehicle.setCategory("new");
            vehicle.setYear("2017");
            vehicle.setMake("Chevrolet");
            vehicle.setModel("Camaro");
            vehicle.setTrim("LX");

            vehicles.add(vehicle);
        }
        {
            Vehicle vehicle = new Vehicle();
            vehicle.setCategory("new");
            vehicle.setYear("2017");
            vehicle.setMake("Chevrolet");
            vehicle.setModel("Spark");
            vehicle.setTrim("ST");

            vehicles.add(vehicle);
        }
        return vehicles;
    }
}
