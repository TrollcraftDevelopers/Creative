package pl.trollcraft.creative.plots.pages;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.plots.options.PlotOption;

import java.util.ArrayList;
import java.util.List;

public class PlotPage {

    private String name;
    private List<PlotOption> options;
    private PlotPage previous;
    private Plot plot;

    public PlotPage (String name, Plot plot) {
        this.name = Colors.color(name);
        options = new ArrayList<>();
        previous = null;
        this.plot = plot;
    }

    public void addOption(PlotOption option) {
        options.add(option);
    }

    public void setPrevious(PlotPage previous) {
        this.previous = previous;
    }

    public String getName() {
        return name;
    }

    public PlotPage getPrevious() {
        return previous;
    }

    public List<PlotOption> getOptions() {
        return options;
    }

    public void open(Player player) {

        int minmax = previous == null ? options.size() : options.size() + 2;
        int slots = Help.getSlots(minmax);
        int start = 0;

        GUI gui = new GUI(slots, name);

        // Adding return button if previous page is present.
        if (previous != null) {

            start = 2;
            gui.addItem(0, new ItemStack(Material.RED_WOOL), e -> {
                e.setCancelled(true);
                previous.open(player);
            });

        }

        for (int i = 0 ; i < options.size() ; i++) {
            PlotOption option = options.get(i);
            gui.addItem(i + start, option.getItemStack(), e -> {

                e.setCancelled(true);

                switch (option.click(plot)) {

                    case RELOAD:
                        open(player);
                        break;

                    default:
                        break;

                }

            });
        }

        Creative.getPlugin().getGuiManager().open(player, gui);
    }

}
