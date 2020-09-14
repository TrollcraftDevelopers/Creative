package pl.trollcraft.creative.core.particles;

import org.bukkit.Particle;

public class ParticleType {

    public enum Standard {

        HEART (Particle.HEART),
        WATER_BUBBLE (Particle.WATER_BUBBLE),
        CLOUD (Particle.CLOUD),
        LAVA (Particle.LAVA),
        FLAME (Particle.FLAME);

        private Particle particle;

        Standard(Particle particle) {
            this.particle = particle;
        }

        public Particle getParticle() {
            return particle;
        }

        public static Standard find(String name) {
            for (Standard c : values())
                if (c.name().equals(name))
                    return c;
            return null;
        }

    }

    public enum Colored {

        REDSTONE (Particle.REDSTONE),
        DUST (Particle.BLOCK_DUST);

        private Particle particle;

        Colored(Particle particle) {
            this.particle = particle;
        }

        public Particle getParticle() {
            return particle;
        }

        public static Colored find(String name) {
            for (Colored c : values())
                if (c.name().equals(name))
                    return c;
            return null;
        }

    }

}
