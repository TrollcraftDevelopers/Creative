package pl.trollcraft.creative.safety.vehicles;

import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import pl.trollcraft.creative.core.help.Help;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class AbstractVehicle {

    /**
     * Identification number.
     */
    private int id;

    /**
     * Locations of parts of the vehicle.
     */
    private List<Location> parts;

    /**
     * Player name who owns the
     * vehicle.
     */
    private String owner;

    public AbstractVehicle(int id, List<Location> parts, String owner) {
        this.id = id;
        this.parts = parts;
        this.owner = owner;
    }

    /**
     * @return the identification number.
     */
    public int getId() {
        return id;
    }

    /**
     * @return a list of parts.
     */
    public List<Location> getParts() {
        return parts;
    }

    /**
     * @return a list of partss locations.
     */
    public List<String> getLocations() {
        return parts.stream()
                .map(Help::locationToString)
                .collect(Collectors.toList());
    }

    /**
     * @return the owner of the vehicle.
     */
    public String getOwner() {
        return owner;
    }

    /**
     * @return amount of parts.
     */
    public int getSize() {
        return parts.size();
    }

}
