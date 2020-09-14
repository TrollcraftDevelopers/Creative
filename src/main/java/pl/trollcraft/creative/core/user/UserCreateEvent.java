package pl.trollcraft.creative.core.user;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserCreateEvent extends Event {

    private User user;

    public UserCreateEvent(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    private static final HandlerList HANDLERS_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return HANDLERS_LIST;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS_LIST;
    }

}
