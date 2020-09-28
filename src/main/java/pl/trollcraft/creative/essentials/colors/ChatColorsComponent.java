package pl.trollcraft.creative.essentials.colors;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.user.UserComponent;

public class ChatColorsComponent implements UserComponent {

    public static final String COMP_NAME = "ChatColorsComponent";

    private String color;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public String getName() {
        return COMP_NAME;
    }

    @Override
    public void save(YamlConfiguration conf, String path) {
        conf.set(path + ".color", color);
    }

    @Override
    public void load(YamlConfiguration conf, String path) {
        color = conf.getString(path + ".color");
    }

    @Override
    public boolean isEmpty() {
        return color == null || color.isEmpty();
    }

}
