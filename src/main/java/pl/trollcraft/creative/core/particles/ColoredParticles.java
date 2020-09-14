package pl.trollcraft.creative.core.particles;

import com.comphenix.protocol.wrappers.WrappedParticle;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.util.Vector;

public class ColoredParticles extends Particles{

    public ColoredParticles(ParticleType.Colored particles, Color color, int size, Location location, Vector offset, int amount) {
        super(location, offset, amount);
        getPacket().setParticleType(WrappedParticle.create(particles.getParticle(), new Particle.DustOptions(color, size)));
    }

    public ColoredParticles(ParticleType.Colored particles, Color color, int size, Vector offset, int amount) {
        super(offset, amount);
        getPacket().setParticleType(WrappedParticle.create(particles.getParticle(), new Particle.DustOptions(color, size)));
    }

    public ColoredParticles(ParticleType.Colored particles, Color color, int size, int amount) {
        super(amount);
        getPacket().setParticleType(WrappedParticle.create(particles.getParticle(), new Particle.DustOptions(color, size)));
    }

}
