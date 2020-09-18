package pl.trollcraft.creative.services.pets;

import org.bukkit.entity.EntityType;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.controlling.PersistenceManager;
import pl.trollcraft.creative.services.ServiceManager;
import pl.trollcraft.creative.services.pets.model.Pet;

public class PetsPersistenceManager extends PersistenceManager<Pet, String> {

    private static final String NAME = "Pets";
    private static final String RESOURCE_NAME = "pets.yml";
    private static final String ROOT = "pets";

    /**
     * Defines a resource file and root.
     * <p>
     * An inheriting class should
     * implement a newInstance static
     * function
     */
    protected PetsPersistenceManager() {
        super(NAME, RESOURCE_NAME, ROOT, Creative.getPlugin().getPetsController());
    }

    public static PetsPersistenceManager newInstance() {
        return new PetsPersistenceManager();
    }

    /**
     * Loads the object from data
     * available at configuration.
     *
     * @param root - path to model's configuration section,
     * @param id  - key in model's configuration section.
     * @return Pet - newly constructed pet object.
     */
    @Override
    protected Pet load(String root, String id) {
        String name = getConfiguration().getString(root + "." + id + ".name");
        EntityType type = EntityType.valueOf(getConfiguration().getString(root + "." + id + ".entityType").toUpperCase());
        return new Pet(id, name, type);
    }

    /**
     * Registers the object to a
     * certain controller.
     *
     * @param obj to load.
     * @return boolean indicating if register went ok.
     */
    @Override
    protected boolean register(Pet obj) {
        Creative.getPlugin().getPetsController().register(obj);
        ServiceManager.register(obj);
        return true;
    }

    /**
     * Saves the object to
     * the file.
     *
     * @param obj - object to be saved.
     */
    @Override
    protected void save(Pet obj) {
        String root = getRoot();
        String id = obj.getId();
        getConfiguration().set(String.format("%s.%s.name", root, id), obj.getName());
        getConfiguration().set(String.format("%s.%s.entityType", root, id), obj.getEntityType());
    }
}
