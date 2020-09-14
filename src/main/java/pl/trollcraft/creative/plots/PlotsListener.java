package pl.trollcraft.creative.plots;

import com.github.intellectualsites.plotsquared.bukkit.events.PlayerEnterPlotEvent;
import com.github.intellectualsites.plotsquared.bukkit.events.PlayerLeavePlotEvent;
import com.github.intellectualsites.plotsquared.plot.object.PlotId;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.plots.metadata.PlotMetaData;

public class PlotsListener implements Listener {

    @EventHandler
    public void onPlotEnter (PlayerEnterPlotEvent event) {

        PlotId plotId = event.getPlot().getId();
        PlotMetaData plotMetaData = Creative.getPlugin().getPlotsMetaDataController().find(plotId);

        if (plotMetaData == null) return;

        GameMode gameMode = plotMetaData.getGameMode();
        if (gameMode != GameMode.CREATIVE) {
            Player player = event.getPlayer();
            player.setGameMode(gameMode);
            player.sendMessage(Colors.color("&6&lUWAGA, &ena tej dzialce ustawiony jest inny tryb niz kreatywny."));
        }

    }

    @EventHandler
    public void onPlotLeave (PlayerLeavePlotEvent event) {

        Player player = event.getPlayer();
        GameMode gameMode = player.getGameMode();

        if (gameMode != GameMode.CREATIVE){
            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Colors.color("&6&lUWAGA, &etryb kreatywny zostal przywrocony."));
        }

    }

}
