package pl.trollcraft.creative.core.commands;

import org.bukkit.plugin.java.JavaPlugin;

public class CommandsManager {

    private JavaPlugin plugin;

    public CommandsManager(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void bind(String commandName, Class<? extends CommandController> controller) {

        try {

            CommandController commandController = controller.newInstance();
            plugin.getCommand(commandName).setExecutor(commandController);

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

}
