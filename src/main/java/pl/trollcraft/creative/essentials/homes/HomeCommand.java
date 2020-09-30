package pl.trollcraft.creative.essentials.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.List;
import java.util.Objects;

public class HomeCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }
        Player player = (Player) sender;

        String player_name = player.getName();

        if(!Home.doesPlayerHasHomes(player_name)){
            player.sendMessage(Colors.color("&cNie posiadasz zadnych home'ow!"));
            return;
        }

        if(args.length == 0){
            if(Home.doesHomeExist(player_name, "default")){
                player.teleport(Home.getHomeLocationByName(player_name, "default"));
                player.sendMessage(Colors.color("&aTeleportowano!"));
            }
            else{
                Home.showHomes(player);
            }
            return;
        }
        if(args.length == 1){
            if(Home.doesHomeExist(player_name, args[0])){
                player.teleport(Home.getHomeLocationByName(player_name, args[0]));
                player.sendMessage(Colors.color("&aTeleportowano!"));
            }
            else{
                Home.showHomes(player);
            }
            return;
        }
    }
}
