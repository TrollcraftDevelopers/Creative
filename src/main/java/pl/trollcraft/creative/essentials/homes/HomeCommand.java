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
        if(args.length == 0){
            if(Objects.requireNonNull(Home.gethomes(player)).size() > 1){
                StringBuilder homesListStr = new StringBuilder();
                for(Home home : Objects.requireNonNull(Home.gethomes(player))){
                    homesListStr.append(home.getName()).append(" ");
                }
                player.sendMessage(Colors.color("&2Posiadane home: " + homesListStr));
                return;
            }
        }
        for (Home home : Objects.requireNonNull(Home.gethomes(player))) {
            if (args.length == 0) {
                if (Home.gethomes(player).size() == 1 && home.getName().equals("default")) {
                    player.teleport(home.getLocation());
                    return;
                }
            } else {
                player.sendMessage(Colors.color("&cNie posiadasz żadnego home"));
                return;
            }
            if (args[0].equalsIgnoreCase(home.getName())) {
                player.teleport(home.getLocation());
                player.sendMessage(Colors.color("&3Teleportowano do home" + args[0]));
                return;
            } else {
                player.sendMessage(Colors.color("&3 Nie ma takiego domu o takiej nazwie."));
                return;
            }
        }
        if (args.length > 1) {
            player.sendMessage(Colors.color("&2Za duza ilosc argumentow"));
            return;
        }
    }
}
