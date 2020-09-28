package pl.trollcraft.creative.core.user;

import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;

import javax.annotation.Nullable;
import java.util.logging.Level;

public class UsersController extends Controller<User, String> {

    private static final Creative plugin = Creative.getPlugin();
    private static final UserComponentsController componentsController = plugin.getComponentsController();

    public void save(User user) {
        YamlConfiguration conf = Configs.load("users.yml");

        user.getComponents().forEach( comp -> {

            if (!comp.isEmpty())
                comp.save(conf, String.format("users.%s.%s", user.getName(), comp.getName()));

        }  );

        Configs.save(conf, "users.yml");
    }

    public User load(Player player) {

        String name = player.getName();
        YamlConfiguration conf = Configs.load("users.yml");

        User user = new User(name);
        register(user);

        assert conf != null;
        if (conf.contains(String.format("users.%s", name))){

            ConfigurationSection components
                    = conf.getConfigurationSection(String.format("users.%s", name));

            assert components != null;
            components.getKeys(false).forEach(compName -> {

                Class<? extends UserComponent> clazz = componentsController.getComponentClass(compName);
                UserComponent component = null;

                if (clazz == null) {
                    Bukkit.getLogger().log(Level.WARNING, "No such class: " + compName);
                    return;
                }

                try {

                    component = clazz.newInstance();

                } catch (InstantiationException | IllegalAccessException e) {
                    e.printStackTrace();
                }

                assert component != null;
                component.load(conf, String.format("users.%s.%s", name, compName));

                user.addComponent(component);
            } );

        }

        return user;

    }

    @Nullable
    public <T extends UserComponent> T loadComponent(String componentName, String userName) {

        YamlConfiguration conf = Configs.load("users.yml");

        assert conf != null;
        if (!conf.contains("users." + userName)) return null;
        if (!conf.contains("users." + userName + "." + componentName)) return null;

        Class<? extends UserComponent> clazz = componentsController.getComponentClass(componentName);
        UserComponent component = null;

        if (clazz == null) {
            Bukkit.getLogger().log(Level.WARNING, "No such class: " + componentName);
            return null;
        }

        try {

            component = clazz.newInstance();

        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

        assert component != null;
        component.load(conf, String.format("users.%s.%s", userName, componentName));

        return (T) component;
    }

}
