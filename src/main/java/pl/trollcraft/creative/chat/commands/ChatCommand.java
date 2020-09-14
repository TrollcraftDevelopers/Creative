package pl.trollcraft.creative.chat.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.chat.ChatProcessor;
import pl.trollcraft.creative.chat.ChatProfile;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class ChatCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.admin") && !sender.hasPermission("creative.vip")){
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length == 0){
            sender.sendMessage(Colors.color("&7Zarzadzanie chat'em"));
            sender.sendMessage("");
            sender.sendMessage(Colors.color("&e/chat toggle - &7wylacza Twoj chat,"));

            if (sender.hasPermission("creative.admin"))
                sender.sendMessage(Colors.color("&e/chat gtoggle - &7wylacza chat globalnie."));

            return;
        }

        if (args[0].equalsIgnoreCase("gtoggle")) {

            if (!sender.hasPermission("creative.admin")){
                sender.sendMessage(Colors.color("&cBrak uprawnien."));
                return;
            }

            ChatProcessor.switchChat();

            String message = Colors.color("&7Administrator &awlaczyl chat.");
            if (!ChatProcessor.isChatEnabled())
                message = Colors.color("&7Administrator &cwylaczyl chat.");

            final String msg = message;
            Bukkit.getOnlinePlayers().forEach( player -> player.sendMessage(msg) );
            return;
        }

        if (args[0].equalsIgnoreCase("toggle")) {

            if (!(sender instanceof Player)){
                sender.sendMessage("Komenda jedynie dla graczy online.");
                return;
            }

            if (!sender.hasPermission("creative.vip")){
                sender.sendMessage(Colors.color("&cKomenda ta dostepna jest dla graczy &eVIP i SVIP."));
                return;
            }

            Player player = (Player) sender;
            ChatProfile profile = ChatProfile.get(player);

            boolean chat = !profile.hasChatEnabled();
            profile.setChatEnabled(chat);

            if (chat)
                player.sendMessage(Colors.color("&aWlaczono chat."));
            else
                player.sendMessage(Colors.color("&aWylaczono chat."));

            return;
        }

        return;
    }
}
