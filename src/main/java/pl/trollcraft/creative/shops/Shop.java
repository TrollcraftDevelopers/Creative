package pl.trollcraft.creative.shops;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Shop {

    int getSlot();
    ItemStack getIconItemStack();
    void open(Player player);

}
