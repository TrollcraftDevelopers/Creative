package pl.trollcraft.creative.safety.worldedit.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.safety.worldedit.event.WorldEditCommandEvent;
import pl.trollcraft.creative.safety.worldedit.model.Argument;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

public class WorldEditLimits implements Listener {

    @EventHandler (priority = EventPriority.LOWEST, ignoreCancelled = true)
    public void onWorldEditCommand (WorldEditCommandEvent event) {

        WorldEditCommand command = event.getCommand();
        Player player = event.getPlayer();
        String[] args = event.getArgs();

        if (args.length == 1) return;

        Argument arg;
        for (int i = 1 ; i < args.length ; i++) {

            arg = command.getArgument(i-1);

            //TODO add amount calculation
            if (arg.forbidden(args[i])) {
                event.setCancelled(true);
                player.sendMessage(Colors.color("&c&lUWAGA! &cKomenda zawiera ograniczone bloki."));
            }

        }

    }

}
