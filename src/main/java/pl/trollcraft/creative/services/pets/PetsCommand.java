package pl.trollcraft.creative.services.pets;

import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.commands.CommandController;

public class PetsCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {

        Player player = (Player) sender;
        Location location = player.getLocation();

        NPC npc = CitizensAPI.getNPCRegistry().createNPC(EntityType.CHICKEN, "JA PIERDOLE");

        npc.spawn(location);

        npc.getNavigator().getLocalParameters().baseSpeed(2);
        npc.getNavigator().setTarget( (Entity) player, true);

    }
}