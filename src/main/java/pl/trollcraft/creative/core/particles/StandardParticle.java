package pl.trollcraft.creative.core.particles;

import com.comphenix.protocol.wrappers.WrappedParticle;
import org.bukkit.Location;
import org.bukkit.util.Vector;

public class StandardParticle extends Particles {

    public StandardParticle(ParticleType.Standard particles, Location location, Vector offset, int amount) {
        super(location, offset, amount);
        getPacket().setParticleType(WrappedParticle.create(particles.getParticle(), null));
    }

    public StandardParticle(ParticleType.Standard particles, Vector offset, int amount) {
        super(offset, amount);
        getPacket().setParticleType(WrappedParticle.create(particles.getParticle(), null));
    }

    public StandardParticle(ParticleType.Standard particles, int amount) {
        super(amount);
        getPacket().setParticleType(WrappedParticle.create(particles.getParticle(), null));
    }

}
