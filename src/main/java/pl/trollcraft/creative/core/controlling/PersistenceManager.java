package pl.trollcraft.creative.core.controlling;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.core.Configs;

import java.util.logging.Level;

/**
 * A class responsible for managing
 * certain object's loading and saving
 * process.
 *
 * It should be used when you are
 * loading or saving all data on certain event
 * like for example - server start or stop.
 *
 * @param <T>
 */
public abstract class PersistenceManager<T, Z> {

    /**
     * Name of the manager.
     * Used for logging.
     */
    private String name;

    /**
     * Resource file name.
     */
    private String resourceName;

    /**
     * A configuration file from where
     * the data is stored.
     */
    private YamlConfiguration configuration;

    /**
     * A root node is yml resource
     * configuration file.
     */
    private String root;

    /**
     * Controller responsible for
     * controlling the data model.
     */
    private Controller<T, Z> controller;

    /**
     * Seconds before the manager
     * should run loading process.
     */
    private long waitTimeBeforeLoad;

    /**
     * Defines a resource file and root.
     *
     * An inheriting class should
     * implement a newInstance static
     * function
     *
     * @param name - name of the loader,
     * @param resourceName - name of resource file,
     * @param root - root node in yml resource file,
     * @param controller - controller controlling models.
     */
    protected PersistenceManager(String name, String resourceName, String root, Controller<T, Z> controller) {
        this.name = name;
        this.resourceName = resourceName;
        this.root = root;
        this.controller = controller;
        waitTimeBeforeLoad = 0;
    }

    /**
     * Starts loading the collection of
     */
    public final void beginLoading() {

        Bukkit.getLogger().log(Level.INFO, "PersistenceManager " + name + " is beginning to load...");

        configuration = Configs.load(resourceName);
        assert configuration != null;

        configuration.getConfigurationSection(root).getKeys(false).forEach(key -> {

            T obj = load(root, key);
            boolean reg = register(obj);

            if (!reg)
                Bukkit.getLogger().log(Level.WARNING, "PersistenceManager " + name + " returned an error while loading data.");

        } );

        Bukkit.getLogger().log(Level.INFO, "PersistenceManager " + name + " has completed loading the data.");
    }

    /**
     * Starts saving the data.
     */
    public final void beginSaving() {
        Bukkit.getLogger().log(Level.INFO, "PersistenceManager " + name + " is beginning to save...");
        controller.getInstances().forEach( obj -> save(obj) );
        Configs.save(configuration, resourceName);
        Bukkit.getLogger().log(Level.INFO, "PersistenceManager " + name + " is has completed saving the data.");
    }

    /**
     * Loads the object from data
     * available at configuration.
     *
     * @param root - path to model's configuration section,
     * @param key - key in model's configuration section.
     * @return T - newly constructed object.
     */
    protected abstract T load(String root, String key);

    /**
     * Registers the object to a
     * certain controller.
     *
     * @param obj to load.
     * @return boolean indicating if register went ok.
     */
    protected abstract boolean register(T obj);

    /**
     * Saves the object to
     * the file.
     *
     * @param obj - object to be saved.
     */
    protected abstract void save(T obj);

    /**
     * Configuration file getter.
     *
     * @return configuration - configuration file.
     */
    public YamlConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * Gets the root of the configuration
     * file.
     *
     * @return root - root of configuration file.
     */
    public String getRoot() {
        return root;
    }

    /**
     * Gets the controller responsible
     * for controlling the model.
     *
     * @return controller - controller responsible for
     * controlling the model.
     */
    public Controller<T, Z> getController() {
        return controller;
    }

    /**
     * Sets wait time before load.
     *
     * @param waitTimeBeforeLoad
     */
    public void setWaitTimeBeforeLoad(long waitTimeBeforeLoad) {
        this.waitTimeBeforeLoad = waitTimeBeforeLoad;
    }

    /**
     * Gets time before manager should
     * start loading.
     *
     * @return waitTimeBeforeLoad
     */
    public long getWaitTimeBeforeLoad() {
        return waitTimeBeforeLoad;
    }
}
