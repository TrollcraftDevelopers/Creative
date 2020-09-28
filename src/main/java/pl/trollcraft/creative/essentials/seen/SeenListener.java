package pl.trollcraft.creative.essentials.seen;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UserCreateEvent;

public class SeenListener implements Listener {

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {
        User user = event.getUser();
        if (!user.hasComponent(SeenComponent.COMP_NAME))
            user.addComponent(new SeenComponent());
    }

    @EventHandler
    public void onQuit (PlayerQuitEvent event) {
        updateSeenComponent(event.getPlayer());
    }

    private void updateSeenComponent(Player player) {
        String name = player.getName();

        User user = Creative.getPlugin().getUserController()
                .find(name);

        SeenComponent seenComponent = user.findComponent(SeenComponent.COMP_NAME);

        assert seenComponent != null;
        seenComponent.update();

    }

}
