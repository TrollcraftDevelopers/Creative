package pl.trollcraft.creative.essentials.teleport;

import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.essentials.teleport.demanded.TeleportToggleCommand;
import pl.trollcraft.creative.essentials.teleport.obj.InstantTeleport;

public class InstantTeleportCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        if (args.length < 1) {
            sender.sendMessage(Colors.color("&eUzycie: &7/tp <do kogo> LUB /tp <kto> <do kogo> LUB /tp <x> <y> <z>"));
            return;
        }

        InstantTeleport instantTeleport;
        Player player = (Player) sender;

        switch (args.length) {

            case 1:

                if (!sender.hasPermission("creative.tp.others")){
                    sender.sendMessage(Colors.color("&cBrak uprawnien"));
                    return;
                }

                String targetName = args[0];

                Player target = Bukkit.getPlayer(targetName);
                if (target == null || !target.isOnline()) {
                    sender.sendMessage(Colors.color("&cBrak gracza " + targetName));
                    return;
                }

                if (VanishAPI.isInvisible(target)) {
                    sender.sendMessage(Colors.color("&cBrak gracza " + targetName));
                    return;
                }

                if(TeleportToggleCommand.players_toggles.containsKey(targetName)){
                    if(!TeleportToggleCommand.players_toggles.get(targetName)){
                        sender.sendMessage(Colors.color("&cTen gracz ma wylaczona mozliwosc teleportacji!"));
                        return;
                    }
                }

                instantTeleport = new InstantTeleport(player, target.getLocation());
                if (instantTeleport.teleport())
                    player.sendMessage(Colors.color("&aTeleportowano!"));
                else
                    player.sendMessage(Colors.color("&cBlad teleportacji."));

                break;

            case 2: // Permisja tpto zeby gracze sie nawzajem nie tepali do siebie.

                if (!sender.hasPermission("creative.tpto")){
                    sender.sendMessage(Colors.color("&cBrak uprawnien"));
                    return;
                }

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

                if (VanishAPI.isInvisible(to)) {
                    sender.sendMessage(Colors.color("&cBrak gracza " + toName));
                    return;
                }

                if(TeleportToggleCommand.players_toggles.containsKey(toName)){
                    if(!TeleportToggleCommand.players_toggles.get(toName)){
                        sender.sendMessage(Colors.color("&cGracz docelowy ma wylaczona mozliwosc teleportacji!"));
                        return;
                    }
                }

                instantTeleport = new InstantTeleport(who, to.getLocation());
                if (instantTeleport.teleport())
                    sender.sendMessage(Colors.color("&aTeleportowano!"));
                else
                    sender.sendMessage(Colors.color("&cBlad teleportacji."));

                break;

            case 3:

                if (!sender.hasPermission("creative.tp.location")){
                    sender.sendMessage(Colors.color("&cBrak uprawnien."));
                    return;
                }

                double x = Double.parseDouble(args[0]);
                double y = Double.parseDouble(args[1]);
                double z = Double.parseDouble(args[2]);

                player.teleport(new Location(player.getWorld(), x, y, z));
                player.sendMessage(Colors.color("&aTeleportowano"));

        }

    }

}
