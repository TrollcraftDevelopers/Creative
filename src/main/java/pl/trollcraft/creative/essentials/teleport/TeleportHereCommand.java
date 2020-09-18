package pl.trollcraft.creative.essentials.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class TeleportHereCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (!sender.hasPermission("creative.admin")){
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        if (args.length != 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/tphere <kogo>"));
            return;
        }

        String targetName = args[0];
        Player target = Bukkit.getPlayer(targetName);
        if (target == null || !target.isOnline()){
            sender.sendMessage(Colors.color("&cBrak gracza " + targetName));
            return;
        }

        Player player = (Player) sender;
        target.teleport(player);

        player.sendMessage(Colors.color("&aTeleportowano gracza."));

    }
}
