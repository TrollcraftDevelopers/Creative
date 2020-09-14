package pl.trollcraft.creative.shops;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.core.Configs;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.items.ItemStackDeserializer;
import pl.trollcraft.creative.services.Service;
import pl.trollcraft.creative.services.ServiceManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ShopManager {

    private Shop root;

    public void init() {
        YamlConfiguration conf = Configs.load("shop.yml");
        root = load(conf, "shop");
    }

    private Shop load(YamlConfiguration conf, String path) {

        ItemStackDeserializer deserializer;
        ItemStack iconItemStack;
        int slot;
        Service service;
        float price;

        // NO RECURSION;
        if (conf.contains(path + ".items")) {

            ProductsShop shop;

            slot = conf.getInt(path + ".slot");

            deserializer = new ItemStackDeserializer(conf, path + ".iconItemStack");
            iconItemStack = deserializer.getItemStack();

            ArrayList<Product> products = new ArrayList<>();
            Set<String> items = conf.getConfigurationSection(path + ".items").getKeys(false);

            for (String item : items)
                products.add(loadProduct(conf, path + ".items." + item));

            shop = new ProductsShop(iconItemStack, products, slot);
            return shop;

        }
        else if (conf.contains(path + ".categories")) {

            CategoriesShop shop;

            if (conf.contains(path + ".iconItemStack")) {
                deserializer = new ItemStackDeserializer(conf, path + ".iconItemStack");
                iconItemStack = deserializer.getItemStack();
            }
            else iconItemStack = null;

            slot = conf.getInt(path + ".slot");

            ArrayList<Shop> shops = new ArrayList<>();
            Set<String> categories = conf.getConfigurationSection(path + ".categories").getKeys(false);

            for (String category : categories)
                shops.add(load(conf, path + ".categories." + category));

            shop = new CategoriesShop(iconItemStack, shops, slot);
            return shop;

        }

        throw new IllegalStateException("Missing 'categories' or 'items' in " + path);

    }

    private Product loadProduct(YamlConfiguration conf, String path) {
        ItemStackDeserializer deserializer = new ItemStackDeserializer(conf, path + ".iconItemStack");
        ItemStack iconItemStack = deserializer.getItemStack();

        String title = Colors.color(conf.getString(path + ".title"));

        List<String> lore = new ArrayList<>();
        conf.getStringList(path + ".lore").forEach( line -> lore.add(Colors.color(line)) );

        int slot = conf.getInt(path + ".slot");
        float price = (float) conf.getDouble(path + ".price");
        String[] serviceName = conf.getString(path + ".service").split("/");
        Service service = ServiceManager.find(serviceName[0], serviceName[1]);

        if (service == null)
            throw new NullPointerException("Service " + serviceName[1] + " of " + serviceName[0] + " does not exist.");

        else
            return new Product(iconItemStack, title, lore, slot, service, price);
    }

    public Shop getRoot() {
        return root;
    }

}
