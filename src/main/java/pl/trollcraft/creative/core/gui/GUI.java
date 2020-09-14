package pl.trollcraft.creative.core.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashMap;
import java.util.function.Consumer;

public class GUI {

    private int slots;
    private String title;

    private Inventory inventory;

    private boolean transacting;
    private HashMap<Integer, Consumer<InventoryClickEvent>> listener;

    public GUI (int slots, String title) {
        this.slots = slots;
        this.title = Colors.color(title);

        inventory = Bukkit.createInventory(null, slots, this.title);
        transacting = false;
        listener = new HashMap<>();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean isTransacting() {
        return transacting;
    }

    public void setTransacting(boolean transacting) {
        this.transacting = transacting;
    }

    public void setSlots(int slots) {
        this.slots = slots;
        inventory = Bukkit.createInventory(null, slots, title);
    }

    public void addItem(int slot, ItemStack itemStack, Consumer<InventoryClickEvent> click){
        listener.put(slot, click);
        inventory.setItem(slot, itemStack);
    }

    public void clear() {
        listener.clear();
        inventory.clear();
    }

    public Consumer<InventoryClickEvent> getClick(int slot){
        if (listener.containsKey(slot))
            return listener.get(slot);
        return null;
    }

    public void update() {
        inventory.getViewers().forEach( v -> {
            if (v instanceof Player)
                ((Player) v).updateInventory();
        } );
    }


}
