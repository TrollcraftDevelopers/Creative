package pl.trollcraft.creative.chat.blockades;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import pl.trollcraft.creative.Creative;

public class ChatBlockadesListener implements Listener {

    private ChatBlockadesController controller = Creative.getPlugin().getChatBlockadesController();

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        ChatBlockade blockade = controller.find(message.toLowerCase());

        if (blockade != null)
            blockade.getAction().act(event);

    }

}
