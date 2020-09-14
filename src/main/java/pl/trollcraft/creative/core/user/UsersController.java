package pl.trollcraft.creative.core.user;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;

import java.util.logging.Level;

public class UsersController extends Controller<User, String> {

    private static final Creative plugin = Creative.getPlugin();
    private static final UserComponentsController componentsController = plugin.getComponentsController();

    public void save(User user) {
        YamlConfiguration conf = Configs.load("users.yml");
        Player player = Bukkit.getPlayer(user.getName());

        user.getComponents().forEach( comp -> {

            if (!comp.isEmpty())
                comp.save(conf, String.format("users.%s.%s", user.getName(), comp.getName()), player);

        }  );

        Configs.save(conf, "users.yml");
    }

    public User load(Player player) {

        String name = player.getName();
        YamlConfiguration conf = Configs.load("users.yml");

        Bukkit.getServer().getLogger().log(Level.INFO, "Instantiated user and registered.");

        User user = new User(name);
        register(user);

        if (conf.contains(String.format("users.%s", name))){

            ConfigurationSection components
                    = conf.getConfigurationSection(String.format("users.%s", name));

            components.getKeys(false).forEach( compName -> {

                Bukkit.getLogger().log(Level.INFO, compName);

                Class clazz = componentsController.getComponentClass(compName);
                UserComponent component = null;

                if (clazz == null) {
                    Bukkit.getLogger().log(Level.WARNING, "No such class: " + compName);
                    return;
                }

                try {

                    component = (UserComponent) clazz.newInstance();

                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

                Bukkit.getLogger().log(Level.INFO, String.format("users.%s.%s", name, compName));
                component.load(conf, String.format("users.%s.%s", name, compName), player);

                user.addComponent(component);
            } );

        }

        return user;

    }

}
