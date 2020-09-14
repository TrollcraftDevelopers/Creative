package pl.trollcraft.creative.essentials.teleport;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class InstantTeleport extends Teleport {

    private Location location;

    public InstantTeleport(Player player, Location location) {
        super(player);
        this.location = location;
    }

    @Override
    public boolean teleport() {
        return getPlayer().teleport(location);
    }
}
