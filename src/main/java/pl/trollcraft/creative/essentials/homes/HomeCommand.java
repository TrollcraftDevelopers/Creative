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

        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy");
        }

        Player player = (Player) sender;


        if(!Home.doesPlayerHasHomes(player)){
            sender.sendMessage(Colors.color("&cNie posiadasz zadnych home'ow"));
            return;
        }

        if(args.length == 0){
            if(Home.getHomesAmount(player) == 1){
                if(!Home.doesHomeExist(player, "default")){
                    sender.sendMessage(Colors.color("&cNie posiadasz podstawowego home'a"));
                    return;
                }
                Home home = Home.getHomeByName(player, "default");
                player.teleport(home.getLocation());
                return;
            }
            else{
                StringBuilder builder = new StringBuilder();
                List<Home> list = Home.getHomeListByPlayer(player);
                for(Home home : list){
                    builder.append(home.getName() + ", ");
                }
                sender.sendMessage(Colors.color("&aDostepne home: &7" + builder));
                return;
            }
        }
        if(args.length == 1){
            String name = args[0];

            if(!Home.doesHomeExist(player, name)){
                sender.sendMessage(Colors.color("&cTen home nie istnieje!"));
                return;
            }

            Home home = Home.getHomeByName(player,name);
            player.teleport(home.getLocation());
            return;
        }

        sender.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
