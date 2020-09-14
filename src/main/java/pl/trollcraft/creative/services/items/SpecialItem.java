package pl.trollcraft.creative.services.items;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.services.Service;

public class SpecialItem implements Service {

    private static final String GROUP_NAME = "specialitems";
    private static final Plugin plugin = Creative.getPlugin();

    private String id;
    private int code;
    private ItemStack itemStack;
    private PotionEffect effect;

    public SpecialItem(String id, int code, ItemStack itemStack, PotionEffect effect) {
        this.id = id;
        this.code = code;
        this.itemStack = itemStack;
        this.effect = effect;
    }

    @Override
    public String getId() {
        return id;
    }

    public int getCode() {
        return code;
    }

    @Override
    public String getName() {
        return itemStack.getItemMeta().getDisplayName();
    }

    @Override
    public String getGroup() {
        return GROUP_NAME;
    }

    @Override
    public boolean isAllowedFor(Player player) {
        return false;
    }

    @Override
    public void allow(Player player) {
        NamespacedKey key = new NamespacedKey(plugin, plugin.getDescription().getName());
        SpecialEnchantment specialEnchantment = new SpecialEnchantment(key);
        ItemStack is = new ItemStack(itemStack);
        ItemMeta itemMeta = is.getItemMeta();
        itemMeta.addEnchant(specialEnchantment, code, true);
        is.setItemMeta(itemMeta);
        player.getInventory().addItem(is);
    }

    @Override
    public void serve(Player player) {
        player.addPotionEffect(effect);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof String)
            return ((String) obj).equals(id);
        return false;
    }
}
