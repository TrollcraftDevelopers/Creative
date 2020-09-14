package pl.trollcraft.creative.games;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;

public class AttractionsListener implements Listener {

    private AttractionsController attractionsController
            = Creative.getPlugin().getAttractionsController();

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Attraction attraction = attractionsController.find(player);

        if (attraction != null)
            attraction.leave(player);
    }

}
