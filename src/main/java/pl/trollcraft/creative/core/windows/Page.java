package pl.trollcraft.creative.core.windows;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashMap;

public class Page {

    private Inventory inventory;
    private HashMap<Integer, Interaction> interactionsMap;

    public Page(int slots, String title) {
        inventory = Bukkit.createInventory(null, slots, Colors.color(title));
        interactionsMap = new HashMap<>();
    }

    public Page item(int slot, ItemStack itemStack, Interaction interaction) {
        inventory.setItem(slot, itemStack);
        interactionsMap.put(slot, interaction);
        return this;
    }

    public Interaction getInteraction(int slot) {
        if (interactionsMap.containsKey(slot))
            return interactionsMap.get(slot);
        return null;
    }

    public Inventory export() {
        return inventory;
    }

}
