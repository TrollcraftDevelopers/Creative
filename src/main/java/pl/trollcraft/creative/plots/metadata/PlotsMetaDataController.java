package pl.trollcraft.creative.plots.metadata;

import com.github.intellectualsites.plotsquared.plot.PlotSquared;
import com.github.intellectualsites.plotsquared.plot.object.Location;
import com.github.intellectualsites.plotsquared.plot.object.PlotArea;
import com.github.intellectualsites.plotsquared.plot.object.PlotId;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;

import java.util.Optional;

public class PlotsMetaDataController extends Controller<PlotMetaData, PlotId> {

    private static final String WORLD = "plotworld";

    private PlotArea plotArea;

    public PlotsMetaDataController() {
        super();

        Location loc = new Location(WORLD, 0, 60, 0, 0, 0);
        plotArea = PlotSquared.get().getPlotAreaManager().getApplicablePlotArea(loc);
    }

    @Override
    public PlotMetaData find(PlotId id) {

        Optional<PlotMetaData> opt = instances.stream()
                .filter( plot -> plot.getPlot().getId().x == id.x && plot.getPlot().getId().y == id.y )
                .findFirst();

        return opt.orElse(null);
    }

    public void save() {

        YamlConfiguration conf = Configs.load("plots.yml");

        instances.forEach(plotMetaData -> {
            conf.set("plots." + plotMetaData.getPlot().getId().x + ";" + plotMetaData.getPlot().getId().y + ".gameMode" , plotMetaData.getGameMode().name());
        });

        Configs.save(conf, "plots.yml");

    }


}
