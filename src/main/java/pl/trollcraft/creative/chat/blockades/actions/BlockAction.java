package pl.trollcraft.creative.chat.blockades.actions;

import org.bukkit.event.Cancellable;

public class BlockAction implements BlockadeAction{

    @Override
    public String id() {
        return "block";
    }

    @Override
    public void act(Cancellable cancellable) {
        cancellable.setCancelled(true);
    }

    @Override
    public boolean equals(Object obj) {
        String id = (String) obj;
        return id().equals(id);
    }
}
