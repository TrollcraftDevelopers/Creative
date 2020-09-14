package pl.trollcraft.creative.plots.pages;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.plots.options.*;

public class PlotPagesManager {

    public static PlotPage getMainPage(Player player, Plot plot) {
        PlotPage plotPage = new PlotPage("&2&lZarzadzanie dzialka", plot);
        plotPage.addOption(new SwitchGameModeOption(plot));
        plotPage.addOption(new OpenManageMembersOption(plotPage, player));
        plotPage.addOption(new OpenManageTrustedOption(plotPage, player));
        plotPage.addOption(new OpenManageOwnersOption(plotPage, player));
        return plotPage;
    }

    public static PlotPage getMembersPage(Plot plot, PlotPage previous) {
        PlotPage plotPage = new PlotPage("&2&lCzlonkowie dzialki", plot);
        plot.getMembers().forEach( member -> plotPage.addOption(new ManageMemberOption(member)));
        plotPage.setPrevious(previous);
        return plotPage;
    }

    public static PlotPage getTrustedPage(Plot plot, PlotPage previous) {
        PlotPage plotPage = new PlotPage("&2&lZaufani dzialki", plot);
        plot.getTrusted().forEach( member -> plotPage.addOption(new ManageTrustedOption(member)));
        plotPage.setPrevious(previous);
        return plotPage;
    }

    public static PlotPage getOwnersPage(Plot plot, PlotPage previous) {
        PlotPage plotPage = new PlotPage("&2&lWlasciciele dzialki", plot);
        plot.getTrusted().forEach( member -> plotPage.addOption(new ManageOwnerOption(member)));
        plotPage.setPrevious(previous);
        return plotPage;
    }

}
