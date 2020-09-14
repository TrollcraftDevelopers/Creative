package pl.trollcraft.creative.plots.options;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import com.github.intellectualsites.plotsquared.plot.util.UUIDHandler;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.core.help.Colors;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ManageOwnerOption implements PlotOption {

    private UUID uuid;

    public ManageOwnerOption(UUID uuid) {
        this.uuid = uuid;
    }

    @Override
    public ItemStack getItemStack() {
        ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(getName());
        itemMeta.setLore(getLore());
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    @Override
    public String getName() {
        String playerName = UUIDHandler.getName(uuid);

        if (playerName == null || playerName.equals("null"))
            playerName = "Nieznany";

        return Colors.color("&e&l" + playerName);
    }

    @Override
    public List<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Colors.color("&eKliknij, by usunac z"));
        lore.add(Colors.color("&elisty wlascicieli tej dzialki."));
        return lore;
    }

    @Override
    public ClickResponse click(Plot plot) {
        plot.getOwners().remove(uuid);
        return ClickResponse.RELOAD;
    }

}
