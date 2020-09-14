package pl.trollcraft.creative.plots.options;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface PlotOption {

    ItemStack getItemStack();

    String getName();
    List<String> getLore();

    ClickResponse click(Plot plot);

}
