package pl.trollcraft.creative.chat.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.core.Configs;

import java.util.Stack;
import java.util.logging.Level;

public class ChatConfig {

    private Stack<FormatSection> sections;

    public ChatConfig() {
        sections = new Stack<>();
    }

    public void load() {

        YamlConfiguration conf = Configs.load("chat.yml");

        conf.getConfigurationSection("chat").getKeys(false).forEach( section -> {

            String format = conf.getString("chat." + section + ".format");

            DisplayCondition condition = null;
            if (conf.contains("chat." + section + ".condition"))
                condition = new DisplayCondition(conf.getString("chat." + section + ".condition"));

            registerSection(new FormatSection(format, condition));

            Bukkit.getLogger().log(Level.INFO, "Registered section " + section + ". " + format);

        } );

        Bukkit.getLogger().log(Level.INFO, "Loaded chat format.");

    }

    public void registerSection(FormatSection section) {
        sections.push(section);
    }

    public String format(Player player) {

        StringBuilder chatFormat = new StringBuilder();

        for (FormatSection section : sections) {
            if (section.canBeDisplayed(player)) {
                chatFormat.append(section.getFormat(player));
                chatFormat.append(" ");
            }
        }

        return chatFormat.toString();

    }

}
