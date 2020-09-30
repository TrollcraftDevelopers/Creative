package pl.trollcraft.creative.essentials.homes;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.GroupValues;

import java.util.Objects;

public class SetHomeCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }
        Player player = (Player) sender;

        int homes = (int) new GroupValues<Integer>().add("Creative.mvip", 10).add("Creative.svip", 5).add("Creative.vip", 2).add("Creative.player", 1).resolve(player);

        if(args.length == 0){
            Home.addHome(player.getName(), "default", player.getLocation());
            player.sendMessage(Colors.color("&aUstawiono dom!"));
            return;
        }

        if(args.length == 1){
            String home_name = args[0];
            if(Home.doesHomeExist(player.getName(), home_name)){
                Home.addHome(player.getName(), home_name, player.getLocation());
                player.sendMessage(Colors.color("&aUstawiono dom!"));
                return;
            }
            else{
                if(Home.getPlayerHomesNumber(player.getName()) >= homes){
                    player.sendMessage(Colors.color("Osiagnales maksymalna liczbe home'ow"));
                    return;
                }
                else{
                    Home.addHome(player.getName(), home_name, player.getLocation());
                    player.sendMessage(Colors.color("&aUstawiono dom o nazwie " + home_name));
                    return;
                }
            }
        }

        player.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
