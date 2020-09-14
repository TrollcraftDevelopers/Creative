package pl.trollcraft.creative.core;

import org.bukkit.plugin.java.JavaPlugin;

public class Perspectivum extends JavaPlugin {

    public void init() {}
    public void dependencies() {}

    public void data() {}
    public void managers() {}

    public void controllers() {}

    public void commands() {}
    public void events() {}

    @Override
    public void onEnable() {
        init();
        dependencies();

        data();

        controllers();
        managers();

        commands();
        events();
    }

    // --------

    public void save() {}

    @Override
    public void onDisable() {
        save();
    }
}
