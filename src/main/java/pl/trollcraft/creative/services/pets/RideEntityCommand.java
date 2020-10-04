package pl.trollcraft.creative.services.pets;

import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.trait.Controllable;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.BlockIterator;
import org.bukkit.util.Vector;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.services.pets.model.PetSession;

import java.util.Optional;

public class RideEntityCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("Komenda jedynie dla graczy online.");
            return;
        }

        Player player = (Player) sender;
        Optional<Entity> entity = findEntity(player, 5);

        if (entity.isPresent()) {

            PetSession petSession = Creative.getPlugin()
                    .getPetSessionsController()
                    .find(player);

            if (petSession == null) {
                player.sendMessage(Colors.color("&cBrak pet'a."));
                return;
            }

            NPC npc = petSession.getNpc();

            if (npc.getId() != petSession.getNpc().getId()) {
                player.sendMessage(Colors.color("&cTo nie Twoj pet."));
                return;
            }

            npc.getNavigator().getLocalParameters().baseSpeed(1);
            npc.getNavigator().setTarget(null);

            if (!npc.hasTrait(Controllable.class))
                npc.addTrait(new Controllable());

            npc.getTrait(Controllable.class).setEnabled(true);
            npc.getTrait(Controllable.class).mount(player);

            player.sendMessage(Colors.color("&aJazda!"));

        }
        else
            player.sendMessage(Colors.color("&cBrak mob'ow."));

    }

    private Optional<Entity> findEntity(Player player, int radius) {

        World world = player.getWorld();
        Vector start = new Vector(
                player.getLocation().getX(),
                player.getLocation().getY(),
                player.getLocation().getZ());

        BlockIterator iterator = new BlockIterator(world, start, player.getLocation().getDirection(), 1.5, radius);
        Block block;
        Optional<Entity> entity = Optional.empty();

        while (iterator.hasNext()) {

            block = iterator.next();
            entity = world.getNearbyEntities(block.getLocation(), 1.5D, 1.5D, 1.5D).stream()
                    .filter(en -> en instanceof LivingEntity)
                    .findFirst();

            if (entity.isPresent())
                return entity;

        }

        return entity;

    }

}
