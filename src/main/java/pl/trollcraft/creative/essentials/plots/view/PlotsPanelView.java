package pl.trollcraft.creative.essentials.plots.view;

import com.plotsquared.core.PlotSquared;
import com.plotsquared.core.command.Auto;
import com.plotsquared.core.command.SubCommand;
import com.plotsquared.core.configuration.Captions;
import com.plotsquared.core.database.DBFunc;
import com.plotsquared.core.events.PlotMergeEvent;
import com.plotsquared.core.events.Result;
import com.plotsquared.core.location.Direction;
import com.plotsquared.core.location.Location;
import com.plotsquared.core.player.PlotPlayer;
import com.plotsquared.core.plot.Plot;
import com.plotsquared.core.plot.PlotArea;
import com.plotsquared.core.plot.PlotId;
import com.plotsquared.core.util.task.AutoClaimFinishTask;
import com.plotsquared.core.util.task.RunnableVal;
import com.plotsquared.core.util.task.TaskManager;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.items.ItemStackBuilder;
import pl.trollcraft.creative.essentials.plots.PlotsData;

import java.util.UUID;

public class PlotsPanelView {

    private final Economy economy = Creative.getPlugin().getEconomy();
    private final PlotsData plotsData = Creative.getPlugin().getPlotsData();

    private GUI gui;
    private Player player;

    public PlotsPanelView(Player player) {
        gui = new GUI(3*9, Colors.color("&2&lWiecej dzialek."));
        this.player = player;
    }

    private void prepare() {

        // Claims a plot the player is currently standing on.
        ItemStack claimLoc = ItemStackBuilder.init(Material.STONE)
                .name(Colors.color("&aZajmij dzialke"))
                .lore(Colors.color("//&eZajmuje dzialke, //&ena ktorej obecnie stoisz."))
                .build();

        gui.addItem(10, claimLoc, e -> {

            e.setCancelled(true);

            PlotPlayer<?> plotPlayer = PlotPlayer.wrap(player);
            int plots = plotPlayer.getPlotCount();

            if (plots >= plotsData.getMaxPlots()) {
                player.closeInventory();
                player.sendMessage(Colors.color("&cPosiadasz zbyt wiele dzialek."));
                return;
            }

            Location location = plotPlayer.getLocation();
            Plot plot = location.getPlotAbs();

            if (plot == null) {
                player.sendMessage(Colors.color("&cTo nie dzialka."));
                return;
            }

            if (!plot.canClaim(plotPlayer)) {
                player.sendMessage(Colors.color("&cNie mozesz zajac tej dzialki."));
                return;
            }

            double price = plotsData.getPrices().resolve(player);
            EconomyResponse res = economy.withdrawPlayer(player, price);

            if (!res.transactionSuccess()){
                player.closeInventory();
                player.sendMessage(Colors.color("&cBrak srodkow. Cena zakupu kolejnej dzialki to &e" + price + "TC."));
                return;
            }

            claim(plot, plotPlayer);

            player.sendMessage(Colors.color("&aDzialka zostala zajeta."));

        });

        ItemStack claimAuto = ItemStackBuilder.init(Material.LEAD)
                .name(Colors.color("&aZajmij dzialke"))
                .lore(Colors.color("//&eZajmuje automatycznie dzialke.//&eWybor losowy."))
                .build();

        gui.addItem(16, claimAuto, e -> {

            e.setCancelled(true);

            PlotPlayer<?> plotPlayer = PlotPlayer.wrap(player);
            int plots = plotPlayer.getPlotCount();

            if (plots >= plotsData.getMaxPlots()) {
                player.closeInventory();
                player.sendMessage(Colors.color("&cPosiadasz zbyt wiele dzialek."));
                return;
            }

            double price = plotsData.getPrices().resolve(player);
            EconomyResponse res = economy.withdrawPlayer(player, price);

            if (!res.transactionSuccess()){
                player.closeInventory();
                player.sendMessage(Colors.color("&cBrak srodkow. Cena zakupu kolejnej dzialki to &e" + price + "TC."));
                return;
            }

            Location location = plotPlayer.getLocation();
            PlotArea plotArea = location.getPlotArea();

            autoClaimSafe(plotPlayer, plotArea, null, null);
            player.sendMessage(Colors.color("&aNadano automatycznie dzialke."));

        });

    }

    public void display() {
        prepare();
        Creative.getPlugin().getGuiManager().open(player, gui);
    }

    private static void autoClaimSafe(final PlotPlayer var0, final PlotArea var1, PlotId var2, final String var3) {
        var0.setMeta(Auto.class.getName(), true);
        autoClaimFromDatabase(var0, var1, var2, new RunnableVal<Plot>() {
            public void run(Plot var1x) {
                TaskManager.IMP.sync(new AutoClaimFinishTask(var0, var1x, var1, var3));
            }
        });
    }

    private static void autoClaimFromDatabase(PlotPlayer var0, PlotArea var1, PlotId var2, RunnableVal<Plot> var3) {
        Plot var4 = var1.getNextFreePlot(var0, var2);
        if (var4 == null) {
            var3.run(null);
        } else {
            var3.value = var4;
            var4.setOwnerAbs(var0.getUUID());
            DBFunc.createPlotSafe(var4, var3, () -> {
                autoClaimFromDatabase(var0, var1, var4.getId(), var3);
            });
        }
    }

    private static void claim(Plot plot, PlotPlayer<?> plotPlayer) {

        plot.setOwnerAbs(plotPlayer.getUUID());
        DBFunc.createPlotSafe(plot, () -> {
            TaskManager.IMP.sync(new RunnableVal<Object>() {
                public void run(Object var1x) {
                    if (!plot.claim(plotPlayer, true, null, false))
                        plot.setOwnerAbs(null);

                }
            });
        }, () -> {
            plot.setOwnerAbs(null);
        });

    }

}
