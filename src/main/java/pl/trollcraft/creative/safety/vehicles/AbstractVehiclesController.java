package pl.trollcraft.creative.safety.vehicles;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.help.Help;

import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.stream.Collectors;

public class AbstractVehiclesController extends Controller<AbstractVehicle, String> {

    @Override
    public AbstractVehicle find(String id) {
        return instances.stream()
                .filter( vehicle -> vehicle.getOwner().equals(id) )
                .findAny()
                .orElse(null);
    }

    public AbstractVehicle find(Location location) {
        return instances.stream()
                .filter( vehicle -> vehicle.getParts().contains(location) )
                .findAny()
                .orElse(null);
    }

    public List<AbstractVehicle> findAll(String id) {
        return instances.stream()
                .filter( vehicle -> vehicle.getOwner().equals(id) )
                .collect(Collectors.toList());
    }

    public boolean findInDistance(Location loc, int d) {
        return instances.stream()
                 .anyMatch( veh -> veh.getParts().get(veh.getParts().size()/2).distance(loc) <= d);
    }

    /**
     * Saves the vehicles.
     */
    public void save() {

        YamlConfiguration conf = Configs.load("abstractvehicles.yml");
        assert conf != null;

        conf.set("abstractvehicles", null);
        instances.forEach( vehicle -> {

            conf.set("abstractvehicles." + vehicle.getId() + ".owner", vehicle.getOwner());
            vehicle.getParts().forEach( part ->
                conf.set("abstractvehicles." + vehicle.getId() + ".parts", vehicle.getLocations())
            );

        } );

        Configs.save(conf, "abstractvehicles.yml");

    }

    public void load() {

        YamlConfiguration conf = Configs.load("abstractvehicles.yml");
        assert conf != null;

        if (!conf.contains("abstractvehicles")) return;

        conf.getConfigurationSection("abstractvehicles").getKeys(false).forEach( id -> {

            String owner = conf.getString("abstractvehicles." + id + ".owner");
            List<Location> parts = conf.getStringList("abstractvehicles." + id + ".parts")
                    .stream()
                    .map(Help::stringToLocation)
                    .collect(Collectors.toList());

            register(new AbstractVehicle(Integer.parseInt(id), parts, owner));
            Bukkit.getLogger().log(Level.INFO, "Registered new abstract vehicle - parts amount " + parts.size());

        } );

    }

    @Override
    public void unregister(AbstractVehicle vehicle) {

        vehicle.getParts()
                .forEach(loc -> Objects.requireNonNull(loc.getWorld()).getNearbyEntities(loc, .5, .5, .5).forEach(Entity::remove));

        instances.remove(vehicle);

    }
}
