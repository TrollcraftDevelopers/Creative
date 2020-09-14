package pl.trollcraft.creative.prefixes;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.user.User;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.prefixes.obj.Prefix;
import pl.trollcraft.creative.prefixes.obj.PrefixesComponent;
import pl.trollcraft.creative.prefixes.obj.PrefixesController;

public class PrefixesPlaceholder extends PlaceholderExpansion {

    private final String IDENTIFIER = "creative";

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

        if (identifier.equalsIgnoreCase("prefix")) {

            UsersController usersController = Creative.getPlugin().getUserController();
            User user = usersController.find(player.getName());
            PrefixesComponent prefixesComponent = (PrefixesComponent) user.getComponent(PrefixesComponent.COMP_NAME);
            PrefixesController prefixesController = Creative.getPlugin().getPrefixesController();

            if (prefixesComponent == null)
                return "";

            if (prefixesComponent.getCurrent() == null)
                return "";

            Prefix prefix = prefixesController.find(prefixesComponent.getCurrent());
            if (prefix != null)
                return prefix.getName();

            return "";

        }

        return "";

    }
}
