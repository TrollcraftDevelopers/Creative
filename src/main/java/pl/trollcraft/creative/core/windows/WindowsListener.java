package pl.trollcraft.creative.core.windows;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.Creative;

public class WindowsListener implements Listener {

    @EventHandler
    public void onClick (InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        Player player = (Player) event.getWhoClicked();
        Window window = WindowsManager.find(player);

        if (window == null) return;

        event.setCancelled(true);

        int slot = event.getSlot();
        Interaction interaction = window.getCurrentPage().getInteraction(slot);

        if (interaction != null)
            interaction.interact(event);
    }

    @EventHandler
    public void onClose (InventoryCloseEvent event) {
        Player player = (Player) event.getPlayer();
        Window window = WindowsManager.find(player);

        if (window == null) return;

        new BukkitRunnable() {

            @Override
            public void run() {

                player.sendMessage("inv");

                if (player.getOpenInventory() == null) {
                    player.sendMessage("inv closed");
                }

            }

        }.runTaskLater(Creative.getPlugin(), 20);


    }

}
