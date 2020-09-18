package pl.trollcraft.creative.safety.blocks;

import org.bukkit.Material;

import java.util.List;

public class Limit {

    private Material type;
    private List<String> aliases;
    private int amount;

    public Limit(Material type, List<String> aliases, int amount) {
        this.type = type;
        this.aliases = aliases;
        this.amount = amount;
    }

    public Material getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public boolean contains(String exp) {
        if (exp.contains(type.name().toLowerCase()))
                return true;

        if (aliases == null) return false;

        for (String a : aliases)
            if (exp.contains(a))
                return true;
        return false;
    }

    @Override
    public boolean equals(Object obj) {
        Material material = (Material) obj;
        return material == type;
    }
}
