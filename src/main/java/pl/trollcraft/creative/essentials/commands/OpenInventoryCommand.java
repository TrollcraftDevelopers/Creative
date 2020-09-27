package pl.trollcraft.creative.essentials.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class OpenInventoryCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy!");
            return;
        }

        if(args.length == 1){
            Player player = (Player) sender;

            if(!player.hasPermission("creative.seen")){
                sender.sendMessage(Colors.color("&cNie masz dostepu do tej komendy!"));
                return;
            }

            Player target = Bukkit.getPlayer(args[0]);

            if(target == null || !target.isOnline()){
                sender.sendMessage(Colors.color("&cGracz " + args[0] + " jest offline!"));
                return;
            }

            player.openInventory(target.getInventory());
        }

        sender.sendMessage(Colors.color("&7/" + label + " <nick>"));
    }
}
