package pl.trollcraft.creative.services.items;

import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.Plugin;

import java.lang.reflect.Field;
import java.util.logging.Level;

public class SpecialEnchantmentRegister {

    private static SpecialEnchantment specialEnchantment;

    public static void register(Plugin plugin) {

        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            plugin.getLogger().log(Level.WARNING, "Error occurred while registering SpecialEnchantment.");
            e.printStackTrace();
        }
        try {
            NamespacedKey key = new NamespacedKey(plugin, plugin.getDescription().getName());

            specialEnchantment = new SpecialEnchantment(key);
            Enchantment.registerEnchantment(specialEnchantment);
        }
        catch (IllegalArgumentException e){
            plugin.getLogger().log(Level.WARNING, "Error occurred while registering SpecialEnchantment.");
            e.printStackTrace();
        }

        catch(Exception e){
            e.printStackTrace();
        }

        plugin.getLogger().log(Level.INFO, "Successfully registered SpecialEnchantment.");

    }

    public static SpecialEnchantment getSpecialEnchantment() {
        return specialEnchantment;
    }
}
