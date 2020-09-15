package pl.trollcraft.creative.shops;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.gui.GUIManager;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;
import java.util.List;

public class ProductsShop implements Shop {

    private ItemStack iconItemStack;
    private ArrayList<Product> products;
    private int slot;

    public ProductsShop (ItemStack iconItemStack, ArrayList<Product> products, int slot) {
        this.iconItemStack = iconItemStack;
        this.products = products;
        this.slot = slot;
    }

    @Override
    public ItemStack getIconItemStack() {
        return iconItemStack;
    }

    @Override
    public void open(Player player) {

        GUI gui = new GUI(54, "SKLEP");

        ShopSession shopSession
                = Creative.getPlugin().getShopSessionsController().find(player);

        Product product;
        int len = products.size();
        for (int i = 0 ; i < len ; i++) {

            product = products.get(i);

            Product finalProduct = product;

            boolean allowed = product.getService().isAllowedFor(player);

            gui.addItem(product.getSlot(), prepareIconItemStack(product, player, allowed), e -> {

                e.setCancelled(true);

                if (!allowed) {

                    if (finalProduct.purchase(player))
                        open(player);
                    else
                        player.sendMessage(Colors.color("&cBrak srodkow do zakupu."));

                }

            });

        }

        for (int i = 45 ; i < 54 ; i++ ) {

            if (shopSession.hasPrevious() && i == 49) {

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

    private ItemStack prepareIconItemStack(Product product, Player player, boolean allowed) {

        ItemStack icon = product.getIconItemStack();
        ItemMeta itemMeta = icon.getItemMeta();

        itemMeta.setDisplayName(product.getTitle());

        List<String> lore = new ArrayList<>(product.getLore());
        lore.add("");

        if (allowed)
            lore.add(Colors.color("&a&lZAKUPIONO!"));
        else
            lore.add(Colors.color("&6&lZAKUP ZA &e&l" + product.getPrice() + " TC!"));

        itemMeta.setLore(lore);
        icon.setItemMeta(itemMeta);

        return icon;

    }

    @Override
    public int getSlot() {
        return slot;
    }
}
