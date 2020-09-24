package pl.trollcraft.creative.essentials.adminchat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class AdminChatCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy");
            return;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("creative.adminchat")){
            player.sendMessage(Colors.color("&cBrak uprawnien"));
            return;
        }

        if(args.length >= 1){
            StringBuilder builder = new StringBuilder();
            for(int i = 0; i < args.length; i++){
                builder.append(args[i] + " ");
            }
            for(Player target : Bukkit.getOnlinePlayers()){
                if(!target.hasPermission("creative.adminchat")){
                    continue;
                }
                target.sendMessage(Colors.color("&2[AChat] " + player.getName() + ": &7" + builder));
            }
            return;
        }

        sender.sendMessage(Colors.color("&7/" + label + " <wiadomosc>"));
    }
}
