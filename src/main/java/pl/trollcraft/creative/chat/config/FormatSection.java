package pl.trollcraft.creative.chat.config;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.entity.Player;

public class FormatSection {

    private String format;
    private DisplayCondition condition;

    public FormatSection(String format, DisplayCondition condition) {
        this.format = format;
        this.condition = condition;
    }

    public boolean canBeDisplayed(Player player) {
        if (condition != null && !condition.verify(player))
            return false;

        return true;
    }

    public String getFormat(Player player) {
        return PlaceholderAPI.setBracketPlaceholders(player, format);
    }

}
