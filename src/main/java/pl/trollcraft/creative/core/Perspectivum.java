package pl.trollcraft.creative.core;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.core.controlling.PersistenceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * A lifecycle manager.
 * A plugin engine.
 *
 * Responsible for making plugin creation
 * process a bit simpler.
 *
 * @author Jakub Zelmanowicz
 */
public class Perspectivum extends JavaPlugin {

    /**
     * A list of persistence manager
     * responsible for loading and saving data.
     *
     * @see PersistenceManager
     */
    private List<PersistenceManager> persistenceManagers;

    public void init() {}
    public void dependencies() {}

    public void data() {}

    public void managers() {}

    public void controllers() {}

    public void commands() {}
    public void events() {}

    // -------- -------- --------

    /**
     * Loads data from all persistence managers.
     * Including handling wait time if defined.
     */
    private final void loadData() {
        persistenceManagers.forEach(manager -> {

            if (manager.getWaitTimeBeforeLoad() > 0)

                new BukkitRunnable() {

                    @Override
                    public void run() {
                        manager.beginLoading();
                    }
                }.runTaskLater(this, 20 * manager.getWaitTimeBeforeLoad());

            else
                manager.beginLoading();

        });
    }

    /**
     * Saves data from all persistence managers.
     */
    private final void saveData() {
        persistenceManagers.forEach(manager -> manager.beginSaving());
    }

    /**
     * Prepares engine.
     * It's a simulated constructor.
     */
    private final void prepare() {
        persistenceManagers = new ArrayList<>();
    }

    // -------- -------- --------

    /**
     * Registers a new persistence manager.
     * It should be registered in managers
     * lifecycle function.
     *
     * @param manager a manager to be registered.
     * @throws IllegalStateException if manager is already registered.
     */
    public void registerPersistenceManager(PersistenceManager manager) {
        if (persistenceManagers.contains(manager))
            throw new IllegalStateException("Such PersistenceManager is already registered.");

        persistenceManagers.add(manager);
    }

    @Override
    public void onLoad() {
        prepare();
    }

    @Override
    public void onEnable() {
        init();
        dependencies();

        controllers();

        data();
        loadData();

        managers();

        commands();
        events();
    }

    // --------

    @Deprecated
    public void save() {}

    @Override
    public void onDisable() {
        save();
        saveData();
    }
}
