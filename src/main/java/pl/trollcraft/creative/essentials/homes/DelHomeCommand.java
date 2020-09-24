package pl.trollcraft.creative.essentials.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.Objects;

public class DelHomeCommand extends CommandController {
    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy");
        }

        Player player = (Player) sender;

        if(args.length == 1){
            String name = args[0];

            if(name.equalsIgnoreCase("default")){
                sender.sendMessage(Colors.color("&cNie mozesz tego zrobic!"));
                return;
            }

            if(!Home.doesPlayerHasHomes(player)){
                sender.sendMessage(Colors.color("Nie posiadasz home'ow!"));
                return;
            }

            if(!Home.doesHomeExist(player, name)){
                sender.sendMessage(Colors.color("&cTen home nie istnieje!"));
                return;
            }

            Home home = Home.getHomeByName(player, name);
            home.delHome(player, name);
            sender.sendMessage(Colors.color("&cUsunieto home o nazwie " + name));
            return;
        }

        sender.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
