package pl.trollcraft.creative.prefixes.obj;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.event.UserCreateEvent;

public class PrefixesListener implements Listener {

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {

        User user = event.getUser();

        if (!user.hasComponent(PrefixesComponent.COMP_NAME))
            user.addComponent(new PrefixesComponent());

    }

}
