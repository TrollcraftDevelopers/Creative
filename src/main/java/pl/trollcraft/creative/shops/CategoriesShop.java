package pl.trollcraft.creative.shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.gui.GUIManager;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;

public class CategoriesShop implements Shop {

    private ItemStack iconItemStack;
    private ArrayList<Shop> categories;
    private int slot;

    public CategoriesShop (ItemStack iconItemStack, ArrayList<Shop> categories, int slot) {
        this.iconItemStack = iconItemStack;
        this.categories = categories;
        this.slot = slot;
    }

    public void addCategory(Shop category) {
        categories.add(category);
    }

    @Override
    public ItemStack getIconItemStack() {
        return iconItemStack;
    }

    @Override
    public void open(Player player) {

        GUI gui = new GUI(9*4, Colors.color("&2&lSKLEP Z DODATKAMI"));

        ShopSession shopSession
                = Creative.getPlugin().getShopSessionsController().find(player);

        Shop shop;
        int len = categories.size();
        for (int i = 0 ; i < len ; i++){

            shop = categories.get(i);
            Shop finalShop = shop;
            gui.addItem(shop.getSlot(), finalShop.getIconItemStack(), e -> {

                e.setCancelled(true);
                gui.setTransacting(true);
                shopSession.addPrevious(this);
                finalShop.open(player);

            });

        }

        for (int i = 27 ; i < 36 ; i++ ) {

            if (shopSession.hasPrevious() && i == 31) {

                gui.addItem(i, new ItemStack(Material.RED_STAINED_GLASS), e -> {
                    e.setCancelled(true);
                    shopSession.openPrevious();
                });

            }

            else
                gui.addItem(i, new ItemStack(Material.GRAY_STAINED_GLASS), e -> e.setCancelled(true));

        }

        GUIManager guiManager = Creative.getPlugin().getGuiManager();
        guiManager.open(player, gui);

    }

    @Override
    public int getSlot() {
        return slot;
    }
}
