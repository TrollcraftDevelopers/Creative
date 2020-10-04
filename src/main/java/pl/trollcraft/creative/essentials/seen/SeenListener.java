package pl.trollcraft.creative.essentials.seen;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.event.UserCreateEvent;
import pl.trollcraft.creative.core.user.event.UserDeleteEvent;

import java.util.Optional;

public class SeenListener implements Listener {

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {
        User user = event.getUser();
        if (!user.hasComponent(SeenComponent.COMP_NAME))
            user.addComponent(new SeenComponent());
    }

    @EventHandler
    public void onUserDelete (UserDeleteEvent event) {

        User user = event.getUser();
        Optional<Player> opt = user.getPlayer();

        if (opt.isPresent()) {
            Player player = opt.get();
            updateSeenComponent(player);
        }

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
