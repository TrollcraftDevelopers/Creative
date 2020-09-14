package pl.trollcraft.creative.safety.blocks;

import org.bukkit.Material;

public class Limit {

    private Material type;
    private int amount;

    public Limit(Material type, int amount) {
        this.type = type;
        this.amount = amount;
    }

    public Material getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    @Override
    public boolean equals(Object obj) {
        Material material = (Material) obj;
        return material == type;
    }
}
