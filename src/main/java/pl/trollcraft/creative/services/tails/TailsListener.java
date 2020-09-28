package pl.trollcraft.creative.services.tails;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.user.User;

import java.util.Objects;

public class TailsListener implements Listener {

    @EventHandler
    public void onJoin (PlayerJoinEvent event) {
        Player player = event.getPlayer();
        User user = Objects.requireNonNull(
                Creative.getPlugin().getUserController().find(player.getName()));

        TailsComponent component = user.findComponent(TailsComponent.COMP_NAME);
        String selected = component.getSelectedTail();

        if (selected == null) return;

        Tail tail = Objects.requireNonNull(Tail.find(selected));
        Tail.runTail(player, tail);
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Tail.disableTail(player);
    }

}
