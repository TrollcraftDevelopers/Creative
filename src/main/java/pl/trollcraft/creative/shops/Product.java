package pl.trollcraft.creative.shops;

import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.services.Service;

import java.util.List;

public class Product {

    private ItemStack iconItemStack;

    private String title;
    private List<String> lore;
    private int slot;

    private Service service;
    private float price;

    public Product(ItemStack iconItemStack, String title,
                   List<String> lore, int slot, Service service, float price) {

        this.iconItemStack = iconItemStack;

        this.title = title;
        this.lore = lore;
        this.slot = slot;

        this.service = service;
        this.price = price;
    }

    public ItemStack getIconItemStack() {
        return iconItemStack;
    }

    public String getTitle() {
        return title;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getSlot() {
        return slot;
    }

    public Service getService() {
        return service;
    }

    public float getPrice() {
        return price;
    }

    public boolean purchase(Player player) {

        Economy economy = Creative.getPlugin().getEconomy();
        EconomyResponse res = economy.withdrawPlayer(player, price);

        if (res.transactionSuccess()) {
            service.allow(player);
            return true;
        }

        return false;
    }

}
