package pl.trollcraft.creative.essentials.plots;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.essentials.plots.view.PlotsPanelView;

public class MorePlotsCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla administracji.");
            return;
        }

        Player player = (Player) sender;
        new PlotsPanelView(player).display();

    }
}
