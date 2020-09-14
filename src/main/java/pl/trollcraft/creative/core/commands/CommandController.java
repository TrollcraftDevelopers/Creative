package pl.trollcraft.creative.core.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public abstract class CommandController implements CommandExecutor {

    public abstract void command(CommandSender sender, String label, String[] args);

    // -------- -------- -------- -------- --------

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        command(sender, label, args);
        return true;
    }
}
