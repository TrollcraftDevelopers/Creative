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
        if (args.length > 2) {
            player.sendMessage(Colors.color("&cZa dużo argumentów."));
            return;
        }
        int homes = (int) new GroupValues<Integer>().add("Creative.mvip", 10).add("Creative.svip", 5).add("Creative.vip", 2).resolve(player);
        for (Home home : Objects.requireNonNull(Home.gethomes(player))) {
            if (Objects.requireNonNull(Home.gethomes(player)).size() >= homes) {
                player.sendMessage(Colors.color("&3 Nie mozesz miec wiecej domow"));
                return;
            }
            if (args[0].equalsIgnoreCase(home.getName())) {
                player.sendMessage(Colors.color("&3Dom o takiej nazwie już istnieje"));
                return;
            }
        }
        if (args.length == 0) {
            Home.save(player, "default");
            player.sendMessage(Colors.color("&1Utworzono dom"));
            return;
        }
        Home.save(player, args[0]);
        player.sendMessage(Colors.color("&1Utworzono dom o nazwie" + args[0]));
    }
}
