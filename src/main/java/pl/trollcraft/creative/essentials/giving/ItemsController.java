package pl.trollcraft.creative.essentials.giving;

import org.bukkit.Bukkit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.help.Help;
import pl.trollcraft.creative.core.reloading.Reloadable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Scanner;
import java.util.logging.Level;

public class ItemsController extends Controller<Item, String> implements Reloadable {

    @Override
    public Item find(String id) {

        if (id.contains(":")) {

            String[] data = id.split(":");
            if (data.length > 2)
                return null;

            long i = Help.isLong(data[0], -1);
            if (i < 0)
                return null;

            long d = Help.isLong(data[1], -1);
            if (d < 0)
                return null;

            Bukkit.getLogger().log(Level.INFO, String.valueOf(d));

            return instances.stream()
                    .filter(item -> item.getId() == i)
                    .filter(item -> item.getData() == d)
                    .findAny()
                    .orElse(null);

        }
        else {

            long i = Help.isLong(id, -1);
            if (i < 0) {

                return instances.stream()
                        .filter(item -> item.getTypeName().equalsIgnoreCase(id))
                        .findAny()
                        .orElse(null);

            }

            return instances.stream()
                    .filter(item -> item.getId() == i)
                    .filter(item -> item.getData() == 0)
                    .findAny()
                    .orElse(null);

        }

    }

    public void load() {

        try {

            String out = new Scanner(new File(Creative.getPlugin().getDataFolder(), "ids.json"),
                    "UTF-8").useDelimiter("\\A").next();

            if (out.isEmpty()) return;

            JSONParser parser = new JSONParser();
            Object obj = parser.parse(out);
            JSONArray array = (JSONArray)obj;

            array.forEach( o -> {

                JSONObject jsonObject = (JSONObject) o;

                long id = (long) jsonObject.get("type");
                long data = (long) jsonObject.get("meta");
                String typeName = (String) jsonObject.get("name");

                register(new Item(typeName, id, data));

            } );

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }


    }

    // --------

    @Override
    public String name() {
        return "items";
    }

    @Override
    public boolean reload() {
        instances.clear();
        load();
        return true;
    }
}
