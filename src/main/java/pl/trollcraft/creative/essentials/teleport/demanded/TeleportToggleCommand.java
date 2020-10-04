package pl.trollcraft.creative.essentials.teleport.demanded;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.HashMap;

public class TeleportToggleCommand extends CommandController {

    public static HashMap<String, Boolean> players_toggles = new HashMap<>();

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("creative.tptoggle")){
            player.sendMessage(Colors.color("&cNie masz dostepu do tej komendy!"));
            return;
        }

        String player_name = player.getName();

        if(args.length == 0){
            if(players_toggles.containsKey(player_name)){
                if(players_toggles.get(player_name)){
                    players_toggles.put(player_name, false);
                    player.sendMessage(Colors.color("&cZablokowano mozliwosc teleportacji"));
                }
                else{
                    players_toggles.put(player_name, true);
                    player.sendMessage(Colors.color("&aOdblokowano mozliwosc teleportacji"));
                }
                return;
            }
            else{
                players_toggles.put(player_name, false);
                player.sendMessage(Colors.color("&cZablokowano mozliwosc teleportacji"));
                return;
            }
        }

        player.sendMessage(Colors.color("&7/" + label));
    }
}
