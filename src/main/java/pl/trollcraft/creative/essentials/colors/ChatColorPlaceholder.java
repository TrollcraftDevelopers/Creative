package pl.trollcraft.creative.essentials.colors;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.user.User;

@Deprecated
public class ChatColorPlaceholder extends PlaceholderExpansion {

    private final String IDENTIFIER = "chatcolor";

    @Override
    public boolean canRegister() {
        return true;
    }

    @Override
    public String getAuthor() {
        return "SkepsonSk";
    }

    @Override
    public String getIdentifier() {
        return IDENTIFIER;
    }

    @Override
    public String getVersion() {
        return "1.0";
    }

    /**
     * Main placeholder request.
     * Called when the plugin is requesting to use this
     * plugin's placeholders.
     *
     * @param player a player requesting placeholder.
     * @param identifier identifier requested.
     * @return
     */
    @Override
    public String onPlaceholderRequest(Player player, String identifier) {
        User user = Creative.getPlugin().getUserController().find(player.getName());
        ChatColorsComponent component = user.findComponent(ChatColorsComponent.COMP_NAME);

        assert component != null;
        if (component.isEmpty()) return "";

        return Colors.color(component.getColor());
    }

}
