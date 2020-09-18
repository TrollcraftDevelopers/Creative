package pl.trollcraft.creative.core.betterparticles;

import org.bukkit.Particle;

/**
 * String - Particles parser
 */
public final class ParticlesParser {

    /**
     * Parses string into particles object.
     *
     * @param s representing a particle
     * @return
     */
    public static Particles parse(String s) {
        String[] data = s.split(";");

        Particle particle = Particle.valueOf(data[0]);
        int count = Integer.parseInt(data[1]);
        double x = Double.parseDouble(data[2]);
        double y = Double.parseDouble(data[3]);
        double z = Double.parseDouble(data[4]);

        Particles particles = new Particles(particle);
        particles.setCount(count);
        particles.setOffset(x, y, z);

        return particles;
    }

}
