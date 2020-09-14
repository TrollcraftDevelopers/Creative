package pl.trollcraft.creative.services.items;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.enchantments.EnchantmentTarget;
import org.bukkit.inventory.ItemStack;

public class SpecialEnchantment extends Enchantment {

    public SpecialEnchantment(NamespacedKey key) {
        super(key);
    }

    @Override
    public String getName() {
        return "Special";
    }

    @Override
    public int getMaxLevel() {
        return 100000;
    }

    @Override
    public int getStartLevel() {
        return 1;
    }

    @Override
    public EnchantmentTarget getItemTarget() {
        return EnchantmentTarget.ALL;
    }

    @Override
    public boolean conflictsWith(Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean canEnchantItem(ItemStack itemStack) {
        return true;
    }

    @Override
    public boolean isTreasure() {
        return false;
    }

    @Override
    public NamespacedKey getKey() {
        return super.getKey();
    }

    @Override
    public boolean isCursed() {
        return false;
    }
}
