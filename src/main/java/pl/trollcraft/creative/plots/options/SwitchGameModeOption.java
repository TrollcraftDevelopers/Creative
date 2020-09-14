package pl.trollcraft.creative.plots.options;

import com.github.intellectualsites.plotsquared.plot.object.Plot;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.plots.metadata.PlotMetaData;

import java.util.ArrayList;
import java.util.List;

public class SwitchGameModeOption implements PlotOption {

    private enum LocatedGameMode {

        SURVIVAL (GameMode.SURVIVAL, Colors.color("&2&lPrzetrwanie")),
        CREATIVE (GameMode.CREATIVE, Colors.color("&e&lKreatywny")),
        ADVENTURE (GameMode.ADVENTURE, Colors.color("&6&lPrzygodowy")),
        SPECTATOR (GameMode.SPECTATOR, Colors.color("&8&lSpektator"));

        private GameMode gameMode;
        private String locatedName;

        LocatedGameMode(GameMode gameMode, String locatedName) {
            this.gameMode = gameMode;
            this.locatedName = locatedName;
        }

        public String getLocatedName() {
            return locatedName;
        }

        public static LocatedGameMode locate(GameMode gameMode){
            for (LocatedGameMode lgm : values())
                if (lgm.gameMode == gameMode)
                    return lgm;
            return null;
        }


    }

    private PlotMetaData plotMetaData;

    private GameMode selectedGameMode;

    public SwitchGameModeOption (Plot plot) {

        plotMetaData = Creative.getPlugin()
                .getPlotsMetaDataController().find(plot.getId());

        if (plotMetaData == null) {
            plotMetaData = new PlotMetaData(plot, GameMode.CREATIVE);
            Creative.getPlugin().getPlotsMetaDataController().register(plotMetaData);
        }

        this.selectedGameMode = plotMetaData.getGameMode();
    }

    @Override
    public ItemStack getItemStack() {

        ItemStack itemStack;

        if (selectedGameMode == GameMode.SURVIVAL)
            itemStack = new ItemStack(Material.BLACK_WOOL);
        else if (selectedGameMode == GameMode.SURVIVAL)
            itemStack = new ItemStack(Material.CYAN_WOOL);
        else if (selectedGameMode == GameMode.ADVENTURE)
            itemStack = new ItemStack(Material.RED_WOOL);
        else
            itemStack = new ItemStack(Material.BLUE_WOOL);

        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(getName());
        itemMeta.setLore(getLore());
        itemStack.setItemMeta(itemMeta);

        return itemStack;
    }

    @Override
    public String getName() {
        StringBuilder builder = new StringBuilder();
        builder.append(Colors.color("&aTryb gry: "))
                .append(LocatedGameMode.locate(selectedGameMode).locatedName);
        return builder.toString();
    }

    @Override
    public List<String> getLore() {
        ArrayList<String> lore = new ArrayList<>();
        lore.add("");
        lore.add(Colors.color("&aUstawia tryb,"));
        lore.add(Colors.color("&ana ktory zostana"));
        lore.add(Colors.color("&aprzelaczeni wszyscy gracze"));
        lore.add(Colors.color("&awchodzacy na dzialke."));
        return lore;
    }

    @Override
    public ClickResponse click(Plot plot) {

        if (selectedGameMode == GameMode.SURVIVAL)
            selectedGameMode = GameMode.CREATIVE;

        else if (selectedGameMode == GameMode.CREATIVE)
            selectedGameMode = GameMode.ADVENTURE;

        else if (selectedGameMode == GameMode.ADVENTURE)
            selectedGameMode = GameMode.SPECTATOR;

        else if (selectedGameMode == GameMode.SPECTATOR)
            selectedGameMode = GameMode.SURVIVAL;

        plotMetaData.setGameMode(selectedGameMode);

        return ClickResponse.RELOAD;
    }

}
