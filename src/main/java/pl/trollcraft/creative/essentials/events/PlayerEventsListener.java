package pl.trollcraft.creative.essentials.events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;

public class PlayerEventsListener implements Listener {

    private static PlayerEventsController controller
            = Creative.getPlugin().getPlayerEventsController();

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {

        Player player = event.getPlayer();
        PlayerEvent playerEvent = controller.findByOwner(player);

        if (playerEvent != null)
            controller.finish(playerEvent);

        else {

            playerEvent = controller.findByParticipator(player);

            if (playerEvent != null)
                playerEvent.leave(player);

        }

    }

}
