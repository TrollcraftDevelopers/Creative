package pl.trollcraft.creative.core.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.MaterialData;
import pl.trollcraft.creative.core.help.Colors;

import java.util.List;

public class ItemStackDeserializer {

    private YamlConfiguration conf;
    private String path;
    private ItemStack itemStack;

    public ItemStackDeserializer(YamlConfiguration conf, String path) {
        this.conf = conf;
        this.path = path;
        itemStack = new ItemStack(Material.AIR);
        deserialize();
    }

    private void deserialize() {

        Bukkit.getConsoleSender().sendMessage("Trying to: " + path);

        String sType = conf.getString(path + ".type");

        Bukkit.getConsoleSender().sendMessage(sType);

        Material type = Material.getMaterial(sType);

        byte data = (byte) conf.getInt(path + ".data");

        String name;
        if (conf.contains(path + ".name"))
            name = Colors.color(conf.getString(path + ".name"));
        else name = null;

        itemStack.setType(type);
        itemStack.getData().setData(data);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);

        if (conf.contains(path + ".lore")) {
            List<String> lore = conf.getStringList(path + ".lore");
            itemMeta.setLore(lore);
        }

        itemStack.setItemMeta(itemMeta);
    }

    public ItemStack getItemStack() {
        return itemStack;
    }

}
