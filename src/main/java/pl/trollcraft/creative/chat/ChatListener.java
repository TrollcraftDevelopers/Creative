package pl.trollcraft.creative.chat;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.colors.data.ChatColorDataController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ChatListener implements Listener {

    private final ChatColorDataController chatColorDataController = Creative.getPlugin()
            .getChatColorDataController();

    @EventHandler (
            ignoreCancelled = true,
            priority = EventPriority.LOWEST
    )
    public void onChat (AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        String message = event.getMessage();

        ChatProcessor.Response res = ChatProcessor.process(player, message);
        if (res != ChatProcessor.Response.OK){
            event.setCancelled(true);
            player.sendMessage(res.getMessage());
            return;
        }

        event.getRecipients().removeAll(ChatProfile.getChatOff());

        Iterator<Player> it = event.getRecipients().iterator();
        ArrayList<Player> toRemove = new ArrayList<>();
        while (it.hasNext()) {
            it.next();
            for (ChatProfile chatProfile : ChatProfile.getProfiles().values())
                if (chatProfile.getIgnored().contains(player.getName()))
                    toRemove.add(chatProfile.getPlayer());
        }
        event.getRecipients().removeAll(toRemove);

        String format = Creative.getPlugin().getChatConfig().format(player);
        event.setFormat(format);

        String regex = chatColorDataController.getRegex(player);
        message = message.replaceAll(regex, "");

        if (player.hasPermission("creative.vip"))
            event.setMessage(Colors.color(message));

        else
            event.setMessage(message);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        ChatProfile.load(event.getPlayer());
        event.setJoinMessage("");
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        ChatProfile.get(event.getPlayer()).save();
        event.setQuitMessage("");
    }

}
