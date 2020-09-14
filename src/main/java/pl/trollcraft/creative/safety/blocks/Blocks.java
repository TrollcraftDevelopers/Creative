package pl.trollcraft.creative.safety.blocks;

import org.bukkit.Material;

public class Blocks {

    private Material type;
    private int amount;

    public Blocks(Material type) {
        this.type = type;
        amount = 0;
    }

    public Material getType() {
        return type;
    }

    public int getAmount() {
        return amount;
    }

    public void add() {
        amount++;
    }

    public void remove() {

        if (amount > 0)
            amount--;
    }

}
