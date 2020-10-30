package pl.trollcraft.creative.games;

import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;

public class AttractionsListener implements Listener {

    private PlayablesController playablesController
            = Creative.getPlugin().getPlayablesController();

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Playable playable = playablesController.find(player);

        if (playable != null)
            playable.leave(player);
    }

    @EventHandler
    public void onGameModeChange (PlayerGameModeChangeEvent event) {

        Player player = event.getPlayer();
        Playable playable = playablesController.find(player);

        if (playable != null) {

            GameMode gameMode = event.getNewGameMode();

            if (!playable.getGameModesAllowed().contains(gameMode)) {
                event.setCancelled(true);
                player.sendMessage(Colors.color("&cTa gra nie zezwala na ten tryb gry!"));
            }

        }

    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onInteract (PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (player.getInventory().getItemInMainHand().getType() != Material.COMPASS)
            return;

        Playable playable = playablesController.find(player);

        if (playable != null) {
            event.setCancelled(true);
            player.sendMessage(Colors.color("&cPodczas gry nie mozesz uzywac kompasu."));
        }

    }

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();
        Playable playable = playablesController.find(player);
        
        if (playable != null) {
            
            String command = event.getMessage();
            
            if (!(command.contains("/gry") ||
                command.contains("/gra") ||
                command.contains("/games") ||
                command.contains("/game") )) {

                event.setCancelled(true);
                player.sendMessage(Colors.color("&cPodczas gry mozesz uzywac tylko komendy &e/gra."));

            }
            
        }

    }

}
