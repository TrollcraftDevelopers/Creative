package pl.trollcraft.creative.core.user;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.services.tails.TailsComponent;
import pl.trollcraft.creative.services.vehicles.VehiclesComponent;

public class UsersListener implements Listener {

    private final Creative plugin = Creative.getPlugin();
    private final UsersController controller = plugin.getUserController();

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = controller.load(player);
        Bukkit.getPluginManager().callEvent(new UserCreateEvent(user));
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String name = player.getName();
        User user = plugin.getUserController().find(name);

        if (user == null || user.isEmpty()) return;

        Bukkit.getConsoleSender().sendMessage("Saving " + name);

        controller.save(user);
        controller.unregister(user);
    }

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {
        User user = event.getUser();

        if (user.getComponent(TailsComponent.COMP_NAME) == null)
            user.addComponent(new TailsComponent());

        if (user.getComponent(VehiclesComponent.COMP_NAME) == null)
            user.addComponent(new VehiclesComponent());

    }

}
