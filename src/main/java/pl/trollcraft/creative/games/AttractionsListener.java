package pl.trollcraft.creative.games;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;

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

    @EventHandler
    public void onGameModeChange (PlayerGameModeChangeEvent event) {

        Player player = event.getPlayer();
        Attraction attraction = attractionsController.find(player);

        if (attraction != null) {

            GameMode gameMode = event.getNewGameMode();

            if (!attraction.getGameModesAllowed().contains(gameMode)) {
                event.setCancelled(true);
                player.sendMessage(Colors.color("&cTa gra nie zezwala na ten tryb gry!"));
            }

        }

    }

}
