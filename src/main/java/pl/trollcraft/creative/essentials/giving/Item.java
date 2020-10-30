package pl.trollcraft.creative.essentials.giving;

import org.bukkit.Material;

public class Item {
    
    private String typeName;
    private long id;
    private long data;

    public Item(String typeName, long id, long data) {
        this.typeName = typeName;
        this.id = id;
        this.data = data;
    }

    public Material getType() {

        String typeName = this.typeName
                .replace(" ", "_")
                .toUpperCase();

        Material material = Material.getMaterial(typeName + "_BLOCK");
        if (material != null)
            return material;

        material = Material.getMaterial(typeName);
        if (material != null)
            return material;

        return null;
    }

    public String getTypeName() {
        return typeName;
    }

    public long getId() {
        return id;
    }

    public long getData() {
        return data;
    }
}
