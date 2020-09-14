package pl.trollcraft.creative.services.items;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;

public class SpecialItemsListener implements Listener {

    private static SpecialItemsController specialItemsController
            = Creative.getPlugin().getSpecialItemsController();

    public static void listen() {

        new BukkitRunnable() {

            @Override
            public void run() {

                Bukkit.getOnlinePlayers().forEach( player -> {

                    ItemStack item = player.getInventory().getItemInMainHand();
                    if (item == null || item.getType() == Material.AIR) return;

                    if (item.getEnchantments().containsKey(SpecialEnchantmentRegister.getSpecialEnchantment())) {
                        int code = item.getEnchantmentLevel(SpecialEnchantmentRegister.getSpecialEnchantment());
                        SpecialItem specialItem = specialItemsController.find(code);
                        specialItem.serve(player);
                    }

                } );

            }

        }.runTaskTimer(Creative.getPlugin(), 20, 20);

    }

    /*@EventHandler
    public void onInteract (PlayerItemHeldEvent event) {

        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItem(event.getNewSlot());

        if (item == null || item.getType() == Material.AIR) return;

        if (item.getEnchantments().containsKey(SpecialEnchantmentRegister.getSpecialEnchantment())) {
            int code = item.getEnchantmentLevel(SpecialEnchantmentRegister.getSpecialEnchantment());
            player.sendMessage("serve");
            SpecialItem specialItem = specialItemsController.find(code);
            specialItem.serve(player);
        }

    }*/

}
