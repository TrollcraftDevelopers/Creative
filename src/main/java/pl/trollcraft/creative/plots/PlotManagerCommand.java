package pl.trollcraft.creative.plots;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.object.PlotArea;
import com.github.intellectualsites.plotsquared.plot.object.PlotPlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.plots.pages.PlotPagesManager;

public class PlotManagerCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;
        Plot plot = getPlot(player);

        if (plot == null)
            player.sendMessage(Colors.color("&cTo nie jest dzialka, lub nie masz do niej uprawnien."));

        else
            PlotPagesManager.getMainPage(player, plot).open(player);

    }

    private Plot getPlot(Player player) {
        PlotPlayer plotPlayer = PlotPlayer.get(player.getName());
        PlotArea plotArea = plotPlayer.getApplicablePlotArea();
        Plot plot =  plotArea.getPlot(plotPlayer.getLocation());

        if (plot == null)
            return null;

        if (plot.isOwner(plotPlayer.getUUID()))
            return plot;
        return null;
    }

}
