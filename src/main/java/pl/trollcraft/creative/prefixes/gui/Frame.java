package pl.trollcraft.creative.prefixes.gui;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.core.gui.GUI;
import pl.trollcraft.creative.core.help.Colors;
import pl.trollcraft.creative.core.items.ItemStackBuilder;
import pl.trollcraft.creative.prefixes.obj.Prefix;
import pl.trollcraft.creative.prefixes.obj.PrefixesComponent;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

/**
 * A GUI frame containing data
 * displayed to the player.
 */
public class Frame{

    private static final int ELEMENTS_PER_PAGE = 21;

    /**
     * GUI abstract object
     * responsible for managing
     * the core GUI element.
     */
    private GUI gui;

    /**
     * Services list, e.x a list of
     * prefixes.
     */
    private List<Prefix> prefixes;

    /**
     * A consumer of services.
     */
    private Player player;
    private PrefixesComponent component;

    /**
     * An action ran when switcher
     * button is interacted with.
     */
    private Consumer<InventoryClickEvent> switcherAction;

    /**
     * Title of switcher.
     */
    private String switcherName;

    /**
     * Switcher lore.
     */
    private String switcherLore;

    /**
     * Current page for data chunking.
     */
    private int page;

    public Frame(String title, List<Prefix> prefixes, Player player) {
        gui = new GUI(54, title);

        this.prefixes = prefixes;

        this.player = player;
        component = (PrefixesComponent) Creative.getPlugin().getUserController()
                .find(player.getName()).getComponent(PrefixesComponent.COMP_NAME);

        page = 0;
    }

    /**
     * Sets the switcher action.
     *
     * @param switcherAction
     */
    public void setSwitcherAction(Consumer<InventoryClickEvent> switcherAction) {
        this.switcherAction = switcherAction;
    }

    /**
     * Inits switcher.
     *
     * @param switcherName
     * @param switcherLore
     */
    public void setSwitcher(String switcherName, String switcherLore) {
        this.switcherName = switcherName;
        this.switcherLore = switcherLore;
    }

    /**
     * Prepares top and bottom sides
     * of the frame plus sides.
     * buttons.
     */
    private void sides() {
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS);

        for (int i = 0 ; i < 9 ; i++) {
            gui.addItem(new Pos(i, 0).slot(), empty, e -> e.setCancelled(true) );
            gui.addItem(new Pos(i, 4).slot(), empty, e -> e.setCancelled(true) );
        }

        gui.addItem(new Pos(0, 1).slot(), empty, e -> e.setCancelled(true) );
        gui.addItem(new Pos(8, 1).slot(), empty, e -> e.setCancelled(true) );
        gui.addItem(new Pos(0, 3).slot(), empty, e -> e.setCancelled(true) );
        gui.addItem(new Pos(8, 3).slot(), empty, e -> e.setCancelled(true) );
    }

    /**
     * Determines if navigation buttons
     * are necessary and adds them.
     */
    private void navigation() {
        ItemStack noPage = ItemStackBuilder.init(Material.RED_STAINED_GLASS).build();
        ItemStack nextPage = ItemStackBuilder.init(Material.GREEN_STAINED_GLASS).name(Colors.color("&a&lNastepna strona")).build();
        ItemStack prevPage = ItemStackBuilder.init(Material.RED_STAINED_GLASS).name(Colors.color("&c&lPoprzednia strona")).build();

        Consumer<InventoryClickEvent> nextPageClick = e -> {
            e.setCancelled(true);
            page++;
            display();
        };
        Consumer<InventoryClickEvent> prevPageClick = e -> {
            e.setCancelled(true);
            page--;
            display();
        };

        if (this.page == 0)
            gui.addItem(new Pos(0, 2).slot(), noPage, e -> e.setCancelled(true) );
        else
            gui.addItem(new Pos(0, 2).slot(), prevPage, prevPageClick );

        if (prefixes.size() > this.page*21 + 21)
            gui.addItem(new Pos(8, 2).slot(), nextPage, nextPageClick );
        else
            gui.addItem(new Pos(8, 2).slot(), noPage, e -> e.setCancelled(true) );
    }

    /**
     * Prepares the core of the frame.
     * Prefixes.
     */
    private void prefixes() {
        Pos pos = new Pos(0, 0);
        int slot;
        Prefix prefix;
        ItemStack itemStack;
        ItemMeta itemMeta;

        for (int i = 21*page ; i < 21*page+21 ; i++) {

            if (i >= prefixes.size()) break;

            slot = pos.translated(1, 1).slot();

            prefix = prefixes.get(i);
            Prefix finalPrefix = prefix;

            itemStack = new ItemStack(Material.CHEST);
            itemMeta = itemStack.getItemMeta();
            itemMeta.setDisplayName(prefix.getName());

            Consumer<InventoryClickEvent> click = null;

            if (prefix.getId().equals(component.getCurrent())) {
                itemMeta.setLore(Arrays.asList(new String[]{"", Colors.color("&eW uzyciu."), Colors.color("&eKliknij, by odpiac.")}));

                click = e -> {
                    e.setCancelled(true);
                    PrefixesComponent comp = (PrefixesComponent) Creative.getPlugin().getUserController()
                            .find(player.getName())
                            .getComponent(PrefixesComponent.COMP_NAME);

                    comp.setCurrent(null);
                    display();
                };
            }

            else if (component.hasPrefix(prefix.getId()) || player.hasPermission("creative.prefix." + prefix.getId())) {
                itemMeta.setLore(Arrays.asList(new String[]{"", Colors.color("&aKliknij, by uzyc.")}));

                click = e -> {
                    e.setCancelled(true);
                    PrefixesComponent comp = (PrefixesComponent) Creative.getPlugin().getUserController()
                            .find(player.getName())
                            .getComponent(PrefixesComponent.COMP_NAME);

                    comp.setCurrent(finalPrefix.getId());
                    display();
                };
            }

            else if (prefix.isAvailable()) {
                itemMeta.setLore(Arrays.asList(
                        new String[]{"", Colors.color("&aKliknij, by zakupic."), Colors.color("&eCena: &e&l" + prefix.getPrice() + "TC.")}));

                click = e -> {
                    e.setCancelled(true);
                    PrefixesComponent comp = (PrefixesComponent) Creative.getPlugin().getUserController()
                            .find(player.getName())
                            .getComponent(PrefixesComponent.COMP_NAME);

                    //BUY LOGIC

                    comp.addPrefix(finalPrefix.getId());
                    display();
                };

            }
            else if (player.hasPermission("creative.mvip")) {

                itemMeta.setLore(Arrays.asList(new String[]{"", Colors.color("&aTen prefix jest dostepny"),
                    Colors.color("&adopoki posiadasz range MVIP."), "", Colors.color("&aKliknij, by uzyc.")}));

                click = e -> {
                    e.setCancelled(true);
                    PrefixesComponent comp = (PrefixesComponent) Creative.getPlugin().getUserController()
                            .find(player.getName())
                            .getComponent(PrefixesComponent.COMP_NAME);

                    comp.setCurrent(finalPrefix.getId());
                    display();
                };

            }

            else {
                itemMeta.setLore(Arrays.asList(
                        new String[]{"", Colors.color("&cNiedostepny."), Colors.color("&eWymagana ranga MVIP.")}));

                click = e -> e.setCancelled(true);

            }

            itemStack.setItemMeta(itemMeta);

            gui.addItem(slot, itemStack, click);

            pos.x ++;

            if (i % 7 == 6) {
                pos.x = 0;
                pos.y++;
            }

        }
    }

    /**
     * Prepares the bottom bar of
     * the frame. Where switching button
     * is located.
     */
    private void bottom() {
        ItemStack empty = new ItemStack(Material.GRAY_STAINED_GLASS);
        ItemStack switcher = ItemStackBuilder.init(Material.ORANGE_STAINED_GLASS).name(Colors.color(switcherName)).lore(Colors.color(switcherLore)).build();

        for (int i = 0 ; i < 9 ; i++){

            if (i == 4)
                gui.addItem(new Pos(i, 5).slot(), switcher, switcherAction);

            else
                gui.addItem(new Pos(i, 5).slot(), empty, e -> e.setCancelled(true));

        }
    }

    /**
     * Prepares the whole GUI
     * and opens it up for
     * the player.
     */
    public void display() {
        sides();
        navigation();
        prefixes();
        bottom();

        Creative.getPlugin().getGuiManager().open(player, gui);
    }

}
