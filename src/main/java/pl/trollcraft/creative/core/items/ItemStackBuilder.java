package pl.trollcraft.creative.core.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.List;

public class ItemStackBuilder {

    private static final String ARR_SEPARATOR = "//";

    private ItemStack itemStack;

    private ItemStackBuilder(Material material) {
        itemStack = new ItemStack(material);
    }

    public static ItemStackBuilder init(Material material) {
        return new ItemStackBuilder(material);
    }

    public ItemStackBuilder amount(int amount) {
        itemStack.setAmount(amount);
        return this;
    }

    public ItemStackBuilder name(String name) {
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(name);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStackBuilder lore(String lore) {
        List<String> loreList = Arrays.asList(lore.split(ARR_SEPARATOR));
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setLore(loreList);
        itemStack.setItemMeta(itemMeta);
        return this;
    }

    public ItemStack build() {
        return itemStack;
    }
}
