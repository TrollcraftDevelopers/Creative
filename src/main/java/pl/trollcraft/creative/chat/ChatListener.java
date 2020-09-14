package pl.trollcraft.creative.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import pl.trollcraft.creative.Creative;

public class ChatListener implements Listener {

    @EventHandler
    public void onChat (AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = ChatProcessor.deflood(event.getMessage());

        ChatProcessor.Response res = ChatProcessor.process(player, message);
        if (res != ChatProcessor.Response.OK){
            event.setCancelled(true);
            player.sendMessage(res.getMessage());
            return;
        }

        event.getRecipients().removeAll(ChatProfile.getChatOff());

        String format = Creative.getPlugin().getChatConfig().format(player);

        event.setFormat(format);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ChatProfile.load(event.getPlayer());
    }

}
