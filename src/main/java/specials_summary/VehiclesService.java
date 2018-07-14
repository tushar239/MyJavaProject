package specials_summary;

import specials_summary.dto.Vehicle;

import java.util.List;

public interface VehiclesService {

    List<Vehicle> load(String webId, String locale);

}
