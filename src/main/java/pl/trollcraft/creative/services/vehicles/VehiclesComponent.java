package pl.trollcraft.creative.services.vehicles;

import es.pollitoyeye.vehicles.enums.VehicleType;
import es.pollitoyeye.vehicles.interfaces.VehicleSubType;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.user.UserComponent;

import java.util.ArrayList;
import java.util.List;

public class VehiclesComponent implements UserComponent {

    public static final String COMP_NAME = "VehiclesComponent";

    private List<String> vehiclesAvailable;

    // -------- ------- -------- -------- -------- --------

    public VehiclesComponent() {
        vehiclesAvailable = new ArrayList<>();
    }

    // -------- ------- -------- -------- -------- --------

    public List<String> getVehiclesAvailable() {
        return vehiclesAvailable;
    }

    public void addVehicle(String vehicleAvailable) {
        this.vehiclesAvailable.add(vehicleAvailable);
    }

    public boolean canUse(VehicleType type, String subType) {
        VehiclesController vehiclesController = Creative.getPlugin().getVehiclesController();
        return vehiclesAvailable.stream()
                .map(vehiclesController::find)
                .filter( v -> v.getType().equalsIgnoreCase(type.name()) )
                .anyMatch(v -> v.getSubType().equalsIgnoreCase(subType) );
    }

    // -------- ------- -------- -------- -------- --------

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path) {
        if (!isEmpty())
            conf.set(String.format("%s.vehiclesAvailable", path), vehiclesAvailable);
    }

    @Override
    public void load(YamlConfiguration conf, String path) {
        if (conf.contains(String.format("%s.vehiclesAvailable", path)))
            vehiclesAvailable = conf.getStringList(String.format("%s.vehiclesAvailable", path));
    }

    @Override
    public boolean isEmpty() {
        return vehiclesAvailable.isEmpty();
    }

}
