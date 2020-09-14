package pl.trollcraft.creative.services.items;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.controller.Controller;
import pl.trollcraft.creative.core.items.ItemStackDeserializer;
import pl.trollcraft.creative.services.ServiceManager;

import java.util.Optional;

public class SpecialItemsController extends Controller<SpecialItem, String> {

    public void load() {

        YamlConfiguration conf = Configs.load("specialitems.yml");

        conf.getConfigurationSection("specialitems").getKeys(false).forEach( id -> {

            int code = conf.getInt("specialitems." + id + ".code");
            ItemStack itemStack = new ItemStackDeserializer(conf, "specialitems." + id + ".itemStack")
                    .getItemStack();

            PotionEffectType type
                    = PotionEffectType.getByName(conf.getString("specialitems." + id + ".effect.type"));

            int amplifier = conf.getInt("specialitems." + id + ".effect.amplifier");

            PotionEffect effect = new PotionEffect(type, 2*20, amplifier );

            SpecialItem specialItem = new SpecialItem(id, code, itemStack, effect);

            register(specialItem);
            ServiceManager.register(specialItem);
        } );

    }

    public SpecialItem find(int id) {

        Optional<SpecialItem> opt = instances.stream()
                .filter(specialItem -> specialItem.getCode() == id)
                .findAny();

        return opt.orElse(null);

    }
}
