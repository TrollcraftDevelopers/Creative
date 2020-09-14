package pl.trollcraft.creative.essentials.spawn;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class SpawnCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy.");
            return;
        }

        Player player = (Player) sender;
        player.teleport(player.getWorld().getSpawnLocation());

        player.sendMessage(Colors.color("&aWitaj na spawn'ie."));
        return;
    }
}
