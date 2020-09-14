package pl.trollcraft.creative.core.user;

import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.logging.Level;

public class UserComponentsController {

    private HashMap<String, Class> componentsClasses;

    public UserComponentsController() {
        componentsClasses = new HashMap<>();
    }

    public void register(String componentName, Class clazz) {
        componentsClasses.put(componentName, clazz);
        Bukkit.getServer().getLogger().log(Level.INFO, "Registered new component: " + componentName);
    }

    public Class getComponentClass(String name) {
        if (componentsClasses.containsKey(name))
            return componentsClasses.get(name);
        return null;
    }

}
