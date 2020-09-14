package pl.trollcraft.creative.prefixes.obj;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;
import pl.trollcraft.creative.core.help.Colors;

public class PrefixesController extends Controller<Prefix, String> {

    public void load() {

        YamlConfiguration conf = Configs.load("prefixes.yml");
        conf.getConfigurationSection("prefixes").getKeys(false).forEach( id -> {
            String name = Colors.color(conf.getString("prefixes." + id + ".name"));
            PrefixType type = PrefixType.valueOf(conf.getString("prefixes." + id + ".type").toUpperCase());
            double price = conf.getDouble("prefixes." + id + ".price");
            boolean available = conf.getBoolean("prefixes." + id + ".available");
            register(new Prefix(id, name, type, price, available));
        } );

    }

}
