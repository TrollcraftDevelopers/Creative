package pl.trollcraft.creative.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.windows.Window;
import pl.trollcraft.creative.core.windows.WindowsManager;

import java.util.function.Consumer;
import java.util.logging.Level;

public class InventoryListener implements Listener {

    @EventHandler
    public void onClick (InventoryClickEvent event) {

        if (event.getClickedInventory() == null) return;

        GUIManager guiManager = Creative.getPlugin().getGuiManager();

        Player player = (Player) event.getWhoClicked();
        GUI gui = guiManager.find(player);

        if (gui == null) return;

        Consumer<InventoryClickEvent> click = gui.getClick(event.getSlot());

        if (click != null)
            click.accept(event);
    }

    @EventHandler
    public void onClose (InventoryCloseEvent event) {
        GUIManager guiManager = Creative.getPlugin().getGuiManager();

        Player player = (Player) event.getPlayer();
        GUI gui = guiManager.find(player);

        if (gui == null) return;

        if (!gui.isTransacting())
            guiManager.close(player);
    }

}
