package pl.trollcraft.creative.essentials.colors;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.event.UserCreateEvent;

import java.util.Objects;

public class ColorsListener implements Listener {

    @EventHandler
    public void onSignEdit (SignChangeEvent event) {

        for (int i = 0 ; i < 4 ; i++) {
            if (event.getLine(i) != null && !Objects.requireNonNull(event.getLine(i)).isEmpty())
                event.setLine(i, Colors.color(Objects.requireNonNull(event.getLine(i))));
        }

    }

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {

        User user = event.getUser();

        if (!user.hasComponent(ChatColorsComponent.COMP_NAME))
            user.addComponent(new ChatColorsComponent());

    }

}
