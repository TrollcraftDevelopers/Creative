package pl.trollcraft.creative.core.gui;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class GUIManager {

    private HashMap<Integer, GUI> opened;

    public GUIManager() {
        opened = new HashMap<>();
    }

    public void open(Player player, GUI gui) {
        int id = player.getEntityId();
        player.openInventory(gui.getInventory());

        if (opened.containsKey(id))
            opened.replace(id, gui);
        else opened.put(id, gui);

    }

    public void close(Player player) {
        int id = player.getEntityId();
        if (opened.containsKey(id))
            opened.remove(id);
    }

    public GUI find(Player player) {
        int id = player.getEntityId();
        if (opened.containsKey(id))
            return opened.get(id);
        return null;
    }

}
