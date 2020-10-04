package pl.trollcraft.creative.safety.worldedit;

import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.event.UserCreateEvent;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.safety.blocks.LimitsController;

public class WorldEditListener implements Listener {

    private WorldEditCommandsController controller = Creative.getPlugin().getWorldEditCommandsController();
    private UsersController usersController = Creative.getPlugin().getUserController();

    @EventHandler
    public void onCommand (PlayerCommandPreprocessEvent event) {

        Player player = event.getPlayer();

        if (player.hasPermission("creative.admin")) return;

        String[] command = event.getMessage().split(" ");
        WorldEditCommand worldEditCommand = controller.find(command[0]);

        if (worldEditCommand == null)
            return;

        User user = usersController.find(player.getName());
        WorldEditComponent component = (WorldEditComponent) user.getComponent(WorldEditComponent.COMP_NAME);

        int available = worldEditCommand.getAvailable().resolve(player);
        if (!component.hasPlayedFor(available)) {
            player.sendMessage(Colors.color("&c&lUWAGA! &cTej komendy bedziesz mogl zaczac uzywac za &e"
                    + component.getTimeToPlayFor(available)));
            return;
        }

        long cooldown = component.isOnCooldown(worldEditCommand);
        if (cooldown > 0) {
            event.setCancelled(true);
            String time = Help.parseTime(cooldown);
            player.sendMessage(Colors.color("&c&lUWAGA! &cTej komendy bedziesz mogl uzyc za &e" + time));
            return;
        }

        Argument arg;
        for (int i = 1 ; i < command.length ; i++) {

            arg = worldEditCommand.getArgument(i-1);
            if (arg == null) continue;

            if (arg.containsForbidden(command[i])) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Colors.color("&c&lUWAGA! &cKomenda zawiera zablokowane bloki."));
                return;
            }

            LimitsController limitsController = Creative.getPlugin().getLimitsController();
            if (limitsController.isLimited(command[i])) {
                event.setCancelled(true);
                event.getPlayer().sendMessage(Colors.color("&c&lUWAGA! &cKomenda zawiera bloki, ktore sa ograniczone."));
                return;
            }

        }

        Region region = getRegionSelected(player);

        if (region == null) {
            player.sendMessage(Colors.color("&cBrak zaznaczenia."));
            return;
        }

        long regionV = region.getVolume();
        Selection selection = worldEditCommand.getMaxSelections().resolve(player);

        if (regionV >= selection.getVolume() ) {

            event.setCancelled(true);

            int x = selection.getX();
            int y = selection.getY();
            int z = selection.getZ();

            player.sendMessage(Colors.color("&c&lZa duze zaaznaczenie! &cMax. " + x + "x" + y + "x" + z + " blokow."));
            return;
        }

        if (!checkRegionForBlocks(region)) {
            player.sendMessage(Colors.color("&c&lUWAGA! &cZaznaczenie zawiera zablokowane bloki."));
            return;
        }

        component.cooldown(worldEditCommand, player);

    }

    @EventHandler
    public void onUserCreate (UserCreateEvent event) {
        User user = event.getUser();
        if (user.getComponent(WorldEditComponent.COMP_NAME) == null)
            user.addComponent(new WorldEditComponent());
    }

    private static Region getRegionSelected(Player player) {
        Region region = WorldEdit.getInstance()
                .getSessionManager()
                .findByName(player.getName()).getSelection(BukkitAdapter.adapt(player.getWorld()));

        return region;
    }

    private static boolean checkRegionForBlocks(Region region) {
        return true;
    }

}
