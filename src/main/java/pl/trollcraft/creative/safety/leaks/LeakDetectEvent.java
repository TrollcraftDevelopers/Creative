package pl.trollcraft.creative.safety.leaks;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class LeakDetectEvent extends Event {

    private SafetyLeak leak;

    public LeakDetectEvent(SafetyLeak leak) {
        this.leak = leak;
    }

    public SafetyLeak getLeak() {
        return leak;
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
