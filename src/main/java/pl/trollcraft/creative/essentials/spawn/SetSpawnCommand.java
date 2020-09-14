package pl.trollcraft.creative.essentials.spawn;

import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class SetSpawnCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy.");
            return;
        }

        if (!sender.hasPermission("creative.admin")) {
            sender.sendMessage(Colors.color("&cBrak uprawnien."));
            return;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        world.setSpawnLocation(player.getLocation());
        player.sendMessage(Colors.color("&aUstawiono nowy spawn dla swiata &e" + world.getName()));

        return;

    }
}
