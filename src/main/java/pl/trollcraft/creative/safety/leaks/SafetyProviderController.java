package pl.trollcraft.creative.safety.leaks;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.safety.leaks.entities.EntitiesSafetyProvider;
import pl.trollcraft.creative.safety.leaks.redstone.RedstoneSafetyProvider;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;

public class SafetyProviderController extends Controller<SafetyProvider, Integer> {

    private BukkitTask runnable;

    public SafetyProviderController(Plugin plugin) {
        super();

        load();

        runnable = new BukkitRunnable() {

            @Override
            public void run() {

                instances.forEach( prov -> {

                    SafetyLeak leak = prov.scan();
                    if (leak != null)
                        Bukkit.getPluginManager().callEvent(new LeakDetectEvent(leak));

                });

            }

        }.runTaskTimer(plugin, 20, 20 * 60);

        plugin.getLogger().log(Level.INFO, "Safety Provider Controller initiated.");

    }

    private void load() {

        ArrayList<World> worlds = new ArrayList<>();
        ArrayList<EntityType> entities = new ArrayList<>();

        YamlConfiguration conf = Configs.load("safety.yml");
        conf.getStringList("safety.entities.worlds").forEach( worldName -> worlds.add(Bukkit.getWorld(worldName)) );
        conf.getStringList("safety.entities.remove").forEach( entityType -> entities.add(EntityType.valueOf(entityType)) );
        int redstoneLimit = conf.getInt("safety.redstone.limit");

        register(new EntitiesSafetyProvider(worlds, entities));
        register(new RedstoneSafetyProvider(redstoneLimit));

    }

    @Override
    public SafetyProvider find(Integer id) {
        Optional<SafetyProvider> opt = instances.stream()
                .filter( provider -> provider.getId() == id )
                .findFirst();

        return opt.orElse(null);
    }
}
