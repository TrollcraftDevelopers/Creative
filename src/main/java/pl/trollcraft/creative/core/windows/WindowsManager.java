package pl.trollcraft.creative.core.windows;

import org.bukkit.entity.Player;

import java.util.HashMap;

public class WindowsManager {

    private static HashMap<Player, Window> windowsOpened = new HashMap<>();

    public static Window find(Player player) {
        if (windowsOpened.containsKey(player))
            return windowsOpened.get(player);
        return null;
    }

    public static Window prepare(Player player) {
        if (windowsOpened.containsKey(player))
            return windowsOpened.get(player);

        Window window = new Window(player);
        windowsOpened.put(player, window);
        return window;
    }

    public static void dispose(Player player) {
        if (windowsOpened.containsKey(player))
            windowsOpened.remove(player);
    }

}
