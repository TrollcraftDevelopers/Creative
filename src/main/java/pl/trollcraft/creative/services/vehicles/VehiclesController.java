package pl.trollcraft.creative.services.vehicles;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;
import pl.trollcraft.creative.services.ServiceManager;

public class VehiclesController extends Controller<Vehicle, String> {

    public void load() {
        YamlConfiguration conf = Configs.load("vehicles.yml");
        conf.getConfigurationSection("vehicles").getKeys(false).forEach( id -> {
            String name = conf.getString(String.format("vehicles.%s.name", id));
            String type = conf.getString(String.format("vehicles.%s.type", id));
            String subType = conf.getString(String.format("vehicles.%s.subType", id));

            Vehicle vehicle = new Vehicle(id, type, subType, name);

            register(vehicle);
            ServiceManager.register(vehicle);
        } );
    }

}
