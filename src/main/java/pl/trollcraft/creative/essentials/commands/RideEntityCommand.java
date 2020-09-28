package pl.trollcraft.creative.essentials.commands;

import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class RideEntityCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;
        World world = player.getWorld();

        Vector start = new Vector(
                player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ());

        BlockIterator iterator = new BlockIterator(world, start, player.getLocation().getDirection(), 1.5, 5);
        Block block;
        Entity entity;

        while (iterator.hasNext()) {

            block = iterator.next();
            entity = world.getNearbyEntities(block.getLocation(), 1.5D, 1.5D, 1.5D).stream()
                    .filter(en -> en instanceof LivingEntity)
                    .findFirst()
                    .orElse(null);

            if (entity != null && entity.getEntityId() != player.getEntityId()) {
                entity.addPassenger(player);
                player.sendMessage(Colors.color("&aJazda!"));
                return;
            }

        }

        player.sendMessage(Colors.color("&cBrak mob'ow."));

    }

}
