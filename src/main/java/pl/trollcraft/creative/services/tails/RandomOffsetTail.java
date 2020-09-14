package pl.trollcraft.creative.services.tails;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.betterparticles.Particles;

public class RandomOffsetTail extends Tail {

    public RandomOffsetTail(String id, String name, Particles particles) {
        super(id, name, particles);
    }

    @Override
    public Location prepare(Player player) {
        Location loc = player.getLocation();
        loc.add(player.getLocation().getDirection().multiply(-2));
        loc.add(0, 1, 0);
        particles.setOffset(Math.random(), Math.random(), Math.random());
        return loc;
    }
}
