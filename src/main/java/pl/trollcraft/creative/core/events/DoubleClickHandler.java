package pl.trollcraft.creative.core.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;

public class DoubleClickHandler implements Listener {

    private static final long CLICK_FRAME = 500;

    private class Interaction {
        long lastClick;
        Action action;

        Interaction(long lastClick, Action action){
            this.lastClick = lastClick;
            this.action = action;
        }
    }

    private HashMap<Integer, Interaction> playersInteractions;

    public DoubleClickHandler (Plugin plugin) {
        playersInteractions = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {

        Player player = event.getPlayer();

        int id = player.getEntityId();
        Action action = event.getAction();
        long now = System.currentTimeMillis();

        if (playersInteractions.containsKey(id)){

            Interaction interaction = playersInteractions.get(id);

            if (interaction.action == action) {

                if (now - interaction.lastClick <= CLICK_FRAME) {
                    Bukkit.getPluginManager().callEvent(new DoubleInteractEvent(player, action, event.getClickedBlock()));

                }

                playersInteractions.remove(id);
            }

        }
        else
            playersInteractions.put(id, new Interaction(now, action));


    }

}
