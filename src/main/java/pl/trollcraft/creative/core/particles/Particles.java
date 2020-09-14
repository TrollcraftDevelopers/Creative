package pl.trollcraft.creative.core.particles;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;
import pl.trollcraft.creative.core.wrappers.WrapperPlayServerWorldParticles;

public abstract class Particles {

    private WrapperPlayServerWorldParticles packet;

    public Particles (Location location, Vector offset, int amount) {
        packet = new WrapperPlayServerWorldParticles();
        packet.setLongDistance(true);
        packet.setX((float) location.getX());
        packet.setY((float) location.getY());
        packet.setZ((float) location.getZ());
        packet.setOffsetX( (float) offset.getX());
        packet.setOffsetY( (float) offset.getY());
        packet.setOffsetZ( (float) offset.getZ());
        packet.setNumberOfParticles(amount);
    }

    public Particles (Vector offset, int amount) {
        packet = new WrapperPlayServerWorldParticles();
        packet.setLongDistance(true);
        packet.setOffsetX( (float) offset.getX());
        packet.setOffsetY( (float) offset.getY());
        packet.setOffsetZ( (float) offset.getZ());
        packet.setNumberOfParticles(amount);
    }

    public Particles (int amount) {
        packet = new WrapperPlayServerWorldParticles();
        packet.setLongDistance(true);
        packet.setNumberOfParticles(amount);
    }

    protected WrapperPlayServerWorldParticles getPacket() {
        return packet;
    }

    public void setLocation(Location location) {
        packet.setX((float) location.getX());
        packet.setY((float) location.getY());
        packet.setZ((float) location.getZ());
    }

    public void setOffset(Vector offset) {
        packet.setOffsetX( (float) offset.getX());
        packet.setOffsetY( (float) offset.getY());
        packet.setOffsetZ( (float) offset.getZ());
    }

    public void send(Player player) {
        packet.sendPacket(player);
    }

}
