package pl.trollcraft.creative.plots.metadata;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import org.bukkit.GameMode;

public class PlotMetaData {

    private Plot plot;
    private GameMode gameMode;

    public PlotMetaData (Plot plot, GameMode gameMode) {
        this.plot = plot;
        this.gameMode = gameMode;
    }

    public Plot getPlot() {
        return plot;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }

}
