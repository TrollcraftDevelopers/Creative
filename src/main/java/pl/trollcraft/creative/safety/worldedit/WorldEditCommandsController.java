package pl.trollcraft.creative.safety.worldedit;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.core.user.GroupValues;
import pl.trollcraft.creative.safety.limits.file.LimitFile;
import pl.trollcraft.creative.safety.limits.file.LimitFilesController;
import pl.trollcraft.creative.safety.worldedit.model.Argument;
import pl.trollcraft.creative.safety.worldedit.model.Selection;
import pl.trollcraft.creative.safety.worldedit.model.WorldEditCommand;

import java.util.ArrayList;

public class WorldEditCommandsController extends Controller<WorldEditCommand, String> {

    private final LimitFilesController controller
            = Creative.getPlugin().getLimitFilesController();;

    @Override
    public WorldEditCommand find(String id) {

        return instances.stream()
                .filter(wec -> wec.getName().equalsIgnoreCase(id) || Help.isInArray(wec.getAliases(), id))
                .findAny()
                .orElse(null);

    }

    public void load() {

        YamlConfiguration conf = Configs.load("worldedit.yml");
        assert conf != null;

        conf.getConfigurationSection("worldedit").getKeys(false).forEach( command -> {

            ArrayList<Argument> args = new ArrayList<>();
            conf.getConfigurationSection("worldedit." + command + ".arguments").getKeys(false).forEach( ordStr -> {

                Argument arg = new Argument(Integer.parseInt(ordStr));
                String forbidden = conf.getString("worldedit." + command + ".arguments." + ordStr + ".forbidden");

                arg.addLimit(controller.find(forbidden));

                args.add(arg);

            });

            GroupValues<Selection> maxSelections = new GroupValues<>();
            GroupValues<Integer> cooldown = new GroupValues<>(),
                                 available = new GroupValues<>();

            maxSelections.add("creative.mvip", new Selection(conf.getString("worldedit." + command + ".maxSelections.mvip")));
            maxSelections.add("creative.svip", new Selection(conf.getString("worldedit." + command + ".maxSelections.svip")));
            maxSelections.add("creative.vip", new Selection(conf.getString("worldedit." + command + ".maxSelections.vip")));
            maxSelections.add("creative.player", new Selection(conf.getString("worldedit." + command + ".maxSelections.default")));

            cooldown.add("creative.mvip", conf.getInt("worldedit." + command + ".cooldown.mvip"));
            cooldown.add("creative.svip", conf.getInt("worldedit." + command + ".cooldown.svip"));
            cooldown.add("creative.vip", conf.getInt("worldedit." + command + ".cooldown.vip"));
            cooldown.add("creative.player", conf.getInt("worldedit." + command + ".cooldown.default"));

            available.add("creative.mvip", conf.getInt("worldedit." + command + ".available.mvip"));
            available.add("creative.svip", conf.getInt("worldedit." + command + ".available.svip"));
            available.add("creative.vip", conf.getInt("worldedit." + command + ".available.vip"));
            available.add("creative.player", conf.getInt("worldedit." + command + ".available.default"));

            String[] aliases = conf.getString("worldedit." + command + ".aliases").split(",");

            WorldEditCommand worldEditCommand = new WorldEditCommand("//set", aliases, args,
                    maxSelections, cooldown, available);

            register(worldEditCommand);

        } );

    }

}
