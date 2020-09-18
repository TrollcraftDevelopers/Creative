package pl.trollcraft.creative.essentials.teleport;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.essentials.teleport.obj.InstantTeleport;

public class InstantTeleportCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (!sender.hasPermission("creative.admin")){
            sender.sendMessage(Colors.color("&cKomenda jedynie dla administracji."));
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/tp <do kogo> LUB /tp <kto> <do kogo>"));
            return;
        }

        InstantTeleport instantTeleport;
        switch (args.length) {

            case 1:

                String targetName = args[0];

                Player target = Bukkit.getPlayer(targetName);
                if (target == null || !target.isOnline()) {
                    sender.sendMessage(Colors.color("&cBrak gracza " + targetName));
                    return;
                }

                Player player = (Player) sender;

                instantTeleport = new InstantTeleport(player, target.getLocation());
                if (instantTeleport.teleport())
                    player.sendMessage(Colors.color("&aTeleportowano!"));
                else
                    player.sendMessage(Colors.color("&cBlad teleportacji."));

                break;

            case 2:

                String whoName = args[0];
                String toName = args[1];

                Player who = Bukkit.getPlayer(whoName);
                if (who == null || !who.isOnline()) {
                    sender.sendMessage(Colors.color("&cBrak gracza " + whoName));
                    return;
                }

                Player to = Bukkit.getPlayer(toName);
                if (to == null || !to.isOnline()) {
                    sender.sendMessage(Colors.color("&cBrak gracza " + toName));
                    return;
                }

                instantTeleport = new InstantTeleport(who, to.getLocation());
                if (instantTeleport.teleport())
                    sender.sendMessage(Colors.color("&aTeleportowano!"));
                else
                    sender.sendMessage(Colors.color("&cBlad teleportacji."));

                break;

        }

    }

}
