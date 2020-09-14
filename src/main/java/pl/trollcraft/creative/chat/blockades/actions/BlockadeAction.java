package pl.trollcraft.creative.chat.blockades.actions;

import org.bukkit.event.Cancellable;

public interface BlockadeAction {

    String id();
    void act(Cancellable cancellable);

}
