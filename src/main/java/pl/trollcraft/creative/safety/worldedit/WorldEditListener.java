package pl.trollcraft.creative.safety.worldedit;

import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.event.UserCreateEvent;
import pl.trollcraft.creative.safety.worldedit.event.WorldEditCommandEvent;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

public class WorldEditListener implements Listener {

    private final WorldEditCommandsController controller
            = Creative.getPlugin().getWorldEditCommandsController();

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();

        //if (player.hasPermission("creative.bypass.worldedit")) return;

        String[] command = event.getMessage().split(" ");
        WorldEditCommand worldEditCommand = controller.find(command[0]);

        if (worldEditCommand == null)
            return;

        Region region = getRegionSelected(player);
        if (region == null) {
            event.setCancelled(true);
            player.sendMessage(Colors.color("&cBrak zaznaczonego regionu."));
            return;
        }

        WorldEditCommandEvent worldEditCommandEvent = new WorldEditCommandEvent(worldEditCommand, player,
                event.getMessage().split(" "), region);
        Bukkit.getServer().getPluginManager().callEvent(worldEditCommandEvent);

        if (worldEditCommandEvent.isCancelled())
            event.setCancelled(true);

    }

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {
        User user = event.getUser();
        if (!user.hasComponent(WorldEditComponent.COMP_NAME))
            user.addComponent(new WorldEditComponent());
    }

    private static Region getRegionSelected(Player player) {
        LocalSession session = WorldEdit.getInstance()
                .getSessionManager()
                .findByName(player.getName());

        if (session == null) return null;

        return session.getSelection(BukkitAdapter.adapt(player.getWorld()));
    }

}
