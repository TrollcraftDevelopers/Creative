package pl.trollcraft.creative.safety.worldedit;

import org.bukkit.configuration.file.YamlConfiguration;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;
import pl.trollcraft.creative.core.user.GroupValues;

import java.util.ArrayList;

public class WorldEditCommandsController extends Controller<WorldEditCommand, String> {

    public void load() {

        YamlConfiguration conf = Configs.load("worldedit.yml");

        conf.getConfigurationSection("worldedit").getKeys(false).forEach( command -> {

            ArrayList<Argument> args = new ArrayList<>();
            conf.getConfigurationSection("worldedit." + command + ".arguments").getKeys(false).forEach( ordStr ->
                args.add(new Argument(Integer.parseInt(ordStr), conf.getString("worldedit." + command + ".arguments." + ordStr + ".forbidden").split(",")))
            );

            GroupValues<Selection> maxSelections = new GroupValues<>();
            GroupValues<Integer> cooldown = new GroupValues<>(),
                                 available = new GroupValues<>();

            maxSelections.add("creative.svip", new Selection(conf.getString("worldedit." + command + ".maxSelections.svip")));
            maxSelections.add("creative.vip", new Selection(conf.getString("worldedit." + command + ".maxSelections.vip")));
            maxSelections.add("creative.player", new Selection(conf.getString("worldedit." + command + ".maxSelections.default")));

            cooldown.add("creative.svip", conf.getInt("worldedit." + command + ".cooldown.svip"));
            cooldown.add("creative.vip", conf.getInt("worldedit." + command + ".cooldown.vip"));
            cooldown.add("creative.player", conf.getInt("worldedit." + command + ".cooldown.default"));

            available.add("creative.svip", conf.getInt("worldedit." + command + ".available.svip"));
            available.add("creative.vip", conf.getInt("worldedit." + command + ".available.vip"));
            available.add("creative.player", conf.getInt("worldedit." + command + ".available.default"));

            String[] aliases = conf.getString("worldedit." + command + ".aliases").split(",");

            WorldEditCommand worldEditCommand = new WorldEditCommand("//set", aliases, args,
                    maxSelections, cooldown, available);

            register(worldEditCommand);

        } );

        /*ArrayList<Argument> args = new ArrayList<>();
        args.add(new Argument(0, new String[] {"stone", "54"}));

        GroupValues<Integer> cooldown = new GroupValues<>()
                .add("creative.svip", 10)
                .add("creative.vip", 15)
                .add("creative.player", 20);

        GroupValues<Integer> available = new GroupValues<>()
                .add("creative.svip", 0)
                .add("creative.vip", 0)
                .add("creative.player", 3600);

        WorldEditCommand command = new WorldEditCommand("//set", new String[] {"/set"}, args,
                cooldown, available);

        register(command);*/

    }

}
