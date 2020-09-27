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

        if(args.length == 0){
            Home.addHome(player, "default");
            sender.sendMessage(Colors.color("&aUtworzono dom"));
            return;
        }

        int homes = (int) new GroupValues<Integer>().add("Creative.mvip", 10).add("Creative.svip", 5).add("Creative.vip", 2).add("Creative.player", 1).resolve(player);

        if(Home.getHomesAmount(player) >= homes){
            sender.sendMessage(Colors.color("Osiagnales maksymalna liczbe home'ow"));
            return;
        }

        if(args.length == 1){
            Home.addHome(player, args[0]);
            sender.sendMessage(Colors.color("&aUtworzono dom o nazwie " + args[0]));
            return;
        }

        sender.sendMessage(Colors.color("&7/" + label + " <nazwa>"));
    }
}
