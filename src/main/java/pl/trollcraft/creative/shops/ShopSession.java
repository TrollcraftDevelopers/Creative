package pl.trollcraft.creative.shops;

import org.bukkit.entity.Player;

import java.util.Stack;

public class ShopSession {

    private Player player;

    private Stack<Shop> previous;

    public ShopSession(Player player) {
        this.player = player;
        this.previous = new Stack<>();
    }

    public boolean hasPrevious() {
        return !previous.isEmpty();
    }

    public void openPrevious() {
        previous.pop().open(player);
    }

    public void reset() {
        previous.clear();
    }

    public void addPrevious(Shop previous) {
        this.previous.push(previous);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player)
            return this.player.getEntityId() == ((Player) obj).getEntityId();
        return super.equals(obj);
    }
}
