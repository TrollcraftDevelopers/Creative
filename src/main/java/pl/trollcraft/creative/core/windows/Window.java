package pl.trollcraft.creative.core.windows;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class Window {

    private Player player;
    private HashMap<String, Page> pages;
    private Page current;

    public Window(Player player) {
        this.player = player;
        pages = new HashMap<>();
    }

    public Window page(String id, Page page) {
        pages.put(id, page);
        return this;
    }

    public Page getCurrentPage() {
        return current;
    }

    // -------- -------- -------- --------

    public void open(String id) {
        Page page;

        if (pages.containsKey(id))
            page = pages.get(id);
        else
            page = pages.get(0);

        current = page;
        player.openInventory(page.export());
    }

}
