package pl.trollcraft.creative.essentials.plots;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.user.GroupValues;

public class PlotsData {

    private int maxPlots;
    private GroupValues<Double> prices;

    public void load() {
        YamlConfiguration conf = Configs.load("plots.yml");
        maxPlots = conf.getInt("plots.max");

        prices = new GroupValues<>();
        prices.add("creative.mvip", conf.getDouble("plots.prices.mvip"));
        prices.add("creative.svip", conf.getDouble("plots.prices.svip"));
        prices.add("creative.vip", conf.getDouble("plots.prices.vip"));
        prices.add("creative.default", conf.getDouble("plots.prices.default"));
    }

    public int getMaxPlots() {
        return maxPlots;
    }

    public GroupValues<Double> getPrices() {
        return prices;
    }
}
