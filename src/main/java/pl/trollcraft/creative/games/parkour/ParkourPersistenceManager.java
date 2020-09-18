package pl.trollcraft.creative.games.parkour;

import org.bukkit.Location;
import org.bukkit.Material;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.controlling.PersistenceManager;

import java.util.Objects;

public class ParkourPersistenceManager extends PersistenceManager<Parkour, String> {

    private static final String NAME = "Parkour";
    private static final String RESOURCE_NAME = "parkours.yml";
    private static final String ROOT = "parkours";

    /**
     * Defines a resource file and root.
     * <p>
     * An inheriting class should
     * implement a newInstance static
     * function
     *
     */
    protected ParkourPersistenceManager(Controller<Parkour, String> parkoursController) {
        super(NAME, RESOURCE_NAME, ROOT, parkoursController);
    }

    public static ParkourPersistenceManager newInstance(Controller<Parkour, String> parkoursController) {
        return new ParkourPersistenceManager(parkoursController);
    }

    /**
     * Loads the object from data
     * available at configuration.
     *
     * @param root - path to model's configuration section,
     * @param name - key in model's configuration section.
     * @return T - newly constructed object.
     */
    @Override
    protected Parkour load(String root, String name) {
        String path = String.format("%s.%s", root, name);

        String creator = getConfiguration().getString(String.format("%s.creator", path));
        Location startLocation = getConfiguration().getLocation(String.format("%s.locations.start", path));
        Material fallBlock = Material.getMaterial(Objects.requireNonNull(getConfiguration().getString(String.format("%s.blocks.fall", path))));
        Material checkpointBlock = Material.getMaterial(Objects.requireNonNull(getConfiguration().getString(String.format("%s.blocks.checkpoint", path))));
        Material endBlock = Material.getMaterial(Objects.requireNonNull(getConfiguration().getString(String.format("%s.blocks.end", path))));
        long creationTime = getConfiguration().getLong(String.format("%s.creationTime", path));
        long lastPlayed = getConfiguration().getLong(String.format("%s.lastPlayed", path));
        int playedBy = getConfiguration().getInt(String.format("%s.playedBy", path));
        int finishedBy = getConfiguration().getInt(String.format("%s.finishedBy", path));

        Parkour parkour = new Parkour(name, creator);
        parkour.setStartLocation(startLocation);
        parkour.setFallBlock(fallBlock);
        parkour.setCheckpointBlock(checkpointBlock);
        parkour.setEndBlock(endBlock);
        parkour.setCreated(creationTime);
        parkour.setLastPlayed(lastPlayed);
        parkour.setPlayedBy(playedBy);
        parkour.setFinishedBy(finishedBy);

        return parkour;
    }

    /**
     * Registers the object to a
     * certain controller(s).
     *
     * @param obj to load.
     * @return boolean indicating if register went ok.
     */
    @Override
    protected boolean register(Parkour obj) {
        Creative.getPlugin().getParkoursController().register(obj);
        Creative.getPlugin().getPlayablesController().register(obj);
        return true;
    }

    /**
     * Saves the object to
     * the file.
     *
     * @param obj - object to be saved.
     */
    @Override
    protected void save(Parkour obj) {
        String root = getRoot();
        String name = obj.getName();

        getConfiguration().set(String.format("%s.%s.creator", root, name), obj.getCreator());
        getConfiguration().set(String.format("%s.%s.locations.start", root, name), obj.getStartLocation());
        getConfiguration().set(String.format("%s.%s.blocks.fall", root, name), obj.getFallBlock().name());
        getConfiguration().set(String.format("%s.%s.blocks.checkpoint", root, name), obj.getCheckpointBlock().name());
        getConfiguration().set(String.format("%s.%s.blocks.end", root, name), obj.getEndBlock().name());

        getConfiguration().set(String.format("%s.%s.creationTime", root, name), obj.getCreationTime());
        getConfiguration().set(String.format("%s.%s.lastPlayed", root, name), obj.getLastPlayed());
        getConfiguration().set(String.format("%s.%s.playedBy", root, name), obj.getPlayedBy());
        getConfiguration().set(String.format("%s.%s.finishedBy", root, name), obj.getFinishedBy());

    }
}
