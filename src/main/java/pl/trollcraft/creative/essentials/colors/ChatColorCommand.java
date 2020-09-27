package pl.trollcraft.creative.essentials.colors;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;

public class ChatColorCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!sender.hasPermission("creative.vip")) {

        }

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
        }

    }

}
