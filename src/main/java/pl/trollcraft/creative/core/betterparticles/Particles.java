package pl.trollcraft.creative.core.betterparticles;

import org.bukkit.Location;
import org.bukkit.Particle;

/**
 * Particles presets for
 * easy integration with plugins.
 */
public class Particles {

    private static final int X = 0;
    private static final int Y = 1;
    private static final int Z = 2;

    /**
     * Particle type to be spawned.
     */
    private Particle particle;

    /**
     * Amount of particles to spawn.
     */
    private int count;

    /**
     * x, y, z offset
     */
    private double[] offset;

    /**
     * Additional data necessary for
     * some particle types.
     */
    private Object data;

    /**
     * @param particle type of the preset
     */
    public Particles (Particle particle) {
        this.particle = particle;

        count = 1;
        offset = new double[] {0, 0, 0};
    }

    public void setCount(int count) {
        this.count = count;
    }

    public void setOffset(double x, double y , double z) {
        this.offset[X] = x;
        this.offset[Y] = y;
        this.offset[Z] = z;
    }

    /**
     * Sets additional data.
     *
     * @param data additional data
     * @param <T> particle data type.
     */
    public <T> void setData(T data) {
        this.data = data;
    }

    /**
     * Spawns the particles to all players.
     *
     * @param location to spawn particles.
     */
    public void spawn(Location location) {
        location.getWorld().spawnParticle(particle,
                location, count, offset[X], offset[Y], offset[Z], data);
    }

}
