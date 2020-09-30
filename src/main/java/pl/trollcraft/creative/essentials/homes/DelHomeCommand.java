package pl.trollcraft.creative.essentials.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

import java.util.Objects;

public class DelHomeCommand extends CommandController {
    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }
        Player player = (Player) sender;

        if(args.length == 1){
            String home_name = args[0];

            if(home_name.equalsIgnoreCase("default")){
                player.sendMessage(Colors.color("&cNie mozesz usunac tego home'a!"));
                return;
            }

            if(!Home.doesHomeExist(player.getName(), home_name)){
                player.sendMessage(Colors.color("&cTen home nie istnieje!"));
                Home.showHomes(player);
                return;
            }

            Home.removeHome(player.getName(), home_name);
            player.sendMessage(Colors.color("&cUsunieto home " + home_name));
            return;
        }

        player.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
