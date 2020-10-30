package pl.trollcraft.creative.safety.vehicles;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.world.World;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import es.pollitoyeye.vehicles.enums.VehicleType;
import es.pollitoyeye.vehicles.events.VehiclePickupEvent;
import es.pollitoyeye.vehicles.events.VehiclePlaceEvent;
import es.pollitoyeye.vehicles.events.VehicleSpawnedEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.services.vehicles.VehiclesComponent;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;

public class VehiclesListener implements Listener {

    private static final AbstractVehiclesController vehiclesController
            = Creative.getPlugin().getAbstractVehiclesController();

    @EventHandler (priority = EventPriority.LOWEST)
    public void onVehiclePlaceOnSpawn (VehiclePlaceEvent event) {

        /*if (event.getOwner().hasPermission("creative.vehicles.admin"))
            return;*/

        Player player = event.getOwner();
        World world = BukkitAdapter.adapt(player.getWorld());
        Location location = event.getLocation();

        RegionManager regionManager = WorldGuard.getInstance()
                .getPlatform()
                .getRegionContainer()
                .get(world);

        assert regionManager != null;
        ProtectedRegion set = regionManager
                .getRegion("spawn_");

        assert set != null;
        boolean test = set.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());


        if (test) {
            event.setCancelled(true);
            player.sendMessage(Colors.color("&cNie mozesz postawic tu pojazdu."));
        }

        set = regionManager
                .getRegion("nowosci");

        assert set != null;
        test = set.contains(location.getBlockX(), location.getBlockY(), location.getBlockZ());

        if (test) {
            event.setCancelled(true);
            player.sendMessage(Colors.color("&cNie mozesz postawic tu pojazdu."));
        }

    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onVehiclePlace (VehiclePlaceEvent event) {

        /*if (event.getOwner().hasPermission("creative.vehicles.admin"))
            return;*/

        VehicleType type = event.getType();
        String subType = event.getSubType();

        Player player = event.getOwner();

        if (vehiclesController.findInDistance(event.getLocation(), 2)) {
            event.setCancelled(true);
            player.sendMessage(Colors.color("&cNie mozesz polozyc tutaj pojazdu." +
                    "\n&cDrugi znajduje sie w zasiegu &e5 blokow."));
            return;
        }

        if (!canUse(player, type, subType))
            event.setCancelled(true);

    }

    @EventHandler (priority = EventPriority.NORMAL)
    public void onVehiclePickup (VehiclePickupEvent event) {

        /*if (event.getPlayer().hasPermission("creative.vehicles.admin"))
            return;*/

        Player player = event.getPlayer();

        VehicleType type = event.getVehicleType();
        String subType = event.getVehicleSubType();

        if (!canUse(player, type, subType))
            event.setCancelled(true);

        Entity e = player.getWorld()
                .getNearbyEntities(event.getBlockLocation(), .5, .5, .5)
                .stream()
                .findFirst().get();

        if (e instanceof ArmorStand) {

            AbstractVehicle v = vehiclesController.find(e.getLocation());

            if (v != null) {

                if (!v.getOwner().equals(player.getUniqueId().toString())) {
                    event.setCancelled(true);
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(v.getOwner()));
                    player.sendMessage(Colors.color("&cTo nie Twoj pojazd. Nalezy do &e" + offlinePlayer.getName()));
                    return;
                }

                vehiclesController.unregister(v);
                Bukkit.getConsoleSender().sendMessage("Removed vehicle");
            }
        }

    }

    // ----

    @EventHandler
    public void onVehiclePlaced (VehiclePlaceEvent event) {

        /*if (event.getOwner().hasPermission("creative.vehicles.admin"))
            return;*/

        String name = event.getOwner().getUniqueId().toString();
        int s = vehiclesController.findAll(name).size();

        Bukkit.getConsoleSender().sendMessage("" + s);
        if (s > 3) {
            event.setCancelled(true);
            event.getOwner().sendMessage(Colors.color("&cPosiadasz zbyt wiele pojazdow."));
        }

    }

    @EventHandler
    public void onVehicleSpawned (VehicleSpawnedEvent event) {
        int id = new Random().nextInt(999999999);

        List<Location> partsLocations = event.getVehicleParts().stream()
                .map(Entity::getLocation)
                .collect(Collectors.toList());

        Bukkit.getConsoleSender().sendMessage(partsLocations.size() + "");

        AbstractVehicle veh = new AbstractVehicle(id, partsLocations, event.getOwner());
        vehiclesController.register(veh);
        Bukkit.getConsoleSender().sendMessage("Registered for " + event.getOwner() + ", parts amount " + partsLocations.size());
    }

    // ----

    private boolean canUse(Player player, VehicleType type, String subType) {
        User user = Creative.getPlugin().getUserController().find(player.getName());
        VehiclesComponent vehiclesComponent = user.findComponent(VehiclesComponent.COMP_NAME);

        assert vehiclesComponent != null;
        if (!vehiclesComponent.canUse(type, subType)) {
            player.sendMessage(Colors.color("&cNie posiadasz zakupionego tego pojazdu."));
            return false;
        }
        return true;
    }

}
