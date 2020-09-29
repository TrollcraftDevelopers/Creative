package pl.trollcraft.creative;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import pl.trollcraft.creative.chat.AutoMessages;
import pl.trollcraft.creative.chat.ChatListener;
import pl.trollcraft.creative.chat.blockades.ChatBlockadesController;
import pl.trollcraft.creative.chat.blockades.ChatBlockadesListener;
import pl.trollcraft.creative.chat.blockades.actions.BlockAction;
import pl.trollcraft.creative.chat.blockades.actions.BlockadeAction;
import pl.trollcraft.creative.chat.commands.BroadcastCommand;
import pl.trollcraft.creative.chat.commands.ChatCommand;
import pl.trollcraft.creative.chat.commands.MessageCommand;
import pl.trollcraft.creative.chat.commands.ResponseCommand;
import pl.trollcraft.creative.chat.config.ChatConfig;
import pl.trollcraft.creative.core.commands.CommandsManager;
import pl.trollcraft.creative.core.Perspectivum;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.gui.GUIManager;
import pl.trollcraft.creative.core.gui.InventoryListener;
import pl.trollcraft.creative.core.help.WorldLoader;
import pl.trollcraft.creative.core.help.blocksdetector.BlockDetector;
import pl.trollcraft.creative.core.help.chatcreator.ChatCreator;
import pl.trollcraft.creative.core.help.demands.DemandsController;
import pl.trollcraft.creative.core.user.UserComponentsController;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.core.user.UsersListener;
import pl.trollcraft.creative.essentials.adminchat.AdminChatCommand;
import pl.trollcraft.creative.essentials.colors.ChatColorCommand;
import pl.trollcraft.creative.essentials.colors.ColorsListener;
import pl.trollcraft.creative.essentials.commands.*;
import pl.trollcraft.creative.essentials.commands.naming.RenamePlayerCommand;
import pl.trollcraft.creative.essentials.events.PlayerEventsCommand;
import pl.trollcraft.creative.essentials.events.PlayerEventsController;
import pl.trollcraft.creative.essentials.events.PlayerEventsListener;
import pl.trollcraft.creative.essentials.money.MoneySignListener;
import pl.trollcraft.creative.essentials.money.ScheduledMoneyManager;
import pl.trollcraft.creative.essentials.poses.SitCommand;
import pl.trollcraft.creative.essentials.poses.SittingListener;
import pl.trollcraft.creative.essentials.homes.DelHomeCommand;
import pl.trollcraft.creative.essentials.homes.HomeCommand;
import pl.trollcraft.creative.essentials.homes.SetHomeCommand;
import pl.trollcraft.creative.essentials.seen.SeenCommand;
import pl.trollcraft.creative.essentials.seen.SeenListener;
import pl.trollcraft.creative.essentials.spawn.SetSpawnCommand;
import pl.trollcraft.creative.essentials.spawn.SpawnCommand;
import pl.trollcraft.creative.essentials.teleport.InstantTeleportCommand;
import pl.trollcraft.creative.essentials.teleport.TeleportHereCommand;
import pl.trollcraft.creative.essentials.teleport.demanded.AcceptTeleportCommand;
import pl.trollcraft.creative.essentials.teleport.demanded.DemandedTeleportCommand;
import pl.trollcraft.creative.essentials.teleport.demanded.RefuseTeleportCommand;
import pl.trollcraft.creative.essentials.warps.DelWarpCommand;
import pl.trollcraft.creative.essentials.warps.SetWarpCommand;
import pl.trollcraft.creative.essentials.warps.Warp;
import pl.trollcraft.creative.essentials.warps.WarpCommand;
import pl.trollcraft.creative.games.AttractionsController;
import pl.trollcraft.creative.games.AttractionsListener;
import pl.trollcraft.creative.games.GamesCommand;
import pl.trollcraft.creative.games.PlayablesController;
import pl.trollcraft.creative.games.parkour.Parkour;
import pl.trollcraft.creative.games.parkour.ParkourCommand;
import pl.trollcraft.creative.games.parkour.ParkourPersistenceManager;
import pl.trollcraft.creative.prefixes.PrefixesCommand;
import pl.trollcraft.creative.prefixes.obj.PrefixesComponent;
import pl.trollcraft.creative.prefixes.obj.PrefixesController;
import pl.trollcraft.creative.prefixes.obj.PrefixesListener;
import pl.trollcraft.creative.safety.blocks.BlocksController;
import pl.trollcraft.creative.safety.blocks.BlocksListener;
import pl.trollcraft.creative.safety.blocks.LimitsController;
import pl.trollcraft.creative.safety.leaks.SafetyCommand;
import pl.trollcraft.creative.safety.leaks.SafetyListener;
import pl.trollcraft.creative.safety.leaks.SafetyProviderController;
import pl.trollcraft.creative.safety.leaks.redstone.RedstoneListener;
import pl.trollcraft.creative.safety.worldedit.WorldEditCommandsController;
import pl.trollcraft.creative.safety.worldedit.WorldEditComponent;
import pl.trollcraft.creative.safety.worldedit.WorldEditDebugCommand;
import pl.trollcraft.creative.safety.worldedit.WorldEditListener;
import pl.trollcraft.creative.services.items.SpecialEnchantmentRegister;
import pl.trollcraft.creative.services.items.SpecialItemsController;
import pl.trollcraft.creative.services.items.SpecialItemsListener;
import pl.trollcraft.creative.services.pets.*;
import pl.trollcraft.creative.services.tails.Tail;
import pl.trollcraft.creative.services.tails.TailsCommand;
import pl.trollcraft.creative.services.tails.TailsComponent;
import pl.trollcraft.creative.services.tails.TailsManager;
import pl.trollcraft.creative.services.vehicles.VehiclesCommand;
import pl.trollcraft.creative.services.vehicles.VehiclesComponent;
import pl.trollcraft.creative.services.vehicles.VehiclesController;
import pl.trollcraft.creative.shops.ShopCommand;
import pl.trollcraft.creative.essentials.commands.naming.LoreItemController;
import pl.trollcraft.creative.essentials.commands.naming.RenameItemController;
import pl.trollcraft.creative.shops.ShopManager;
import pl.trollcraft.creative.shops.ShopSession;

import java.util.logging.Level;

public final class Creative extends Perspectivum {

    private static Creative plugin;

    private Economy economy;

    private BlockDetector blockDetector;

    private CommandsManager commandsManager;
    private ProtocolManager protocolManager;
    private GUIManager guiManager;
    private ShopManager shopManager;
    private ChatConfig chatConfig;

    private DemandsController demandsController;
    private SafetyProviderController safetyProviderController;
    private UserComponentsController componentsController;
    private UsersController userController;
    private VehiclesController vehiclesController;
    private SpecialItemsController specialItemsController;
    private PlayerEventsController playerEventsController;
    private ChatBlockadesController chatBlockadesController;
    private Controller<BlockadeAction, String> blockadeActionsController;
    private LimitsController limitsController;
    private BlocksController blocksController;
    private WorldEditCommandsController worldEditCommandsController;
    private PrefixesController prefixesController;

    private AttractionsController attractionsController;
    private PlayablesController playablesController;

    private Controller<ShopSession, Player> shopSessionsController;
    private Controller<Parkour, String> parkoursController;
    private PetsController petsController;
    private PetSessionsController petSessionsController;

    private ChatCreator chatCreator;

    private RedstoneListener redstoneListener;

    @Override
    public void init() {
        plugin = this;
        SpecialEnchantmentRegister.register(this);
        WorldLoader.load();
        blockDetector = new BlockDetector(this);
        blockDetector.monitor();
    }

    @Override
    public void dependencies() {
        new CreativePlaceholders().register();

        if (!setupEconomy()){
            getLogger().log(Level.SEVERE, "Failed to setup economy system.");
            setEnabled(false);
        }

    }

    @Override
    public void data() {
        AutoMessages.init();
        TailsManager.load();
        Warp.load();

        chatConfig = new ChatConfig();
        chatConfig.load();

        PetsPersistenceManager petsPersistenceManager = PetsPersistenceManager.newInstance();
        petsPersistenceManager.setShouldSave(false);
        registerPersistenceManager(petsPersistenceManager);

        ParkourPersistenceManager parkourPersistenceManager = ParkourPersistenceManager.newInstance(parkoursController);
        parkourPersistenceManager.setWaitTimeBeforeLoad(30);
        registerPersistenceManager(parkourPersistenceManager);
    }

    @Override
    public void controllers() {
        safetyProviderController = new SafetyProviderController(this);
        componentsController = new UserComponentsController();

        componentsController.register(TailsComponent.COMP_NAME, TailsComponent.class);
        componentsController.register(VehiclesComponent.COMP_NAME, VehiclesComponent.class);
        componentsController.register(PrefixesComponent.COMP_NAME, PrefixesComponent.class);
        componentsController.register(WorldEditComponent.COMP_NAME, WorldEditComponent.class);
        componentsController.register(PetsComponent.COMP_NAME, PetsComponent.class);

        demandsController = new DemandsController();
        userController = new UsersController();
        vehiclesController = new VehiclesController();
        specialItemsController = new SpecialItemsController();
        playerEventsController = new PlayerEventsController();
        blockadeActionsController = new Controller<>();
        blockadeActionsController.register(new BlockAction());
        chatBlockadesController = new ChatBlockadesController();
        limitsController = new LimitsController();
        blocksController = new BlocksController();
        worldEditCommandsController = new WorldEditCommandsController();
        prefixesController = new PrefixesController();

        attractionsController = new AttractionsController();
        playablesController = new PlayablesController();

        shopSessionsController = new Controller<>();
        parkoursController = new Controller<>();
        petsController = new PetsController();
        petSessionsController = new PetSessionsController();

        chatBlockadesController.load();
        vehiclesController.load();
        specialItemsController.load();
        limitsController.load();
        worldEditCommandsController.load();
        prefixesController.load();
    }

    @Override
    public void managers() {
        commandsManager = new CommandsManager(this);
        protocolManager = ProtocolLibrary.getProtocolManager();

        guiManager = new GUIManager();

        shopManager = new ShopManager();
        shopManager.init();

        chatCreator = new ChatCreator();

        Tail.enable();

        SpecialItemsListener.listen();

        ScheduledMoneyManager.init();
    }

    @Override
    public void commands() {
        commandsManager.bind("spawn", SpawnCommand.class);
        commandsManager.bind("setspawn", SetSpawnCommand.class);

        commandsManager.bind("seen", SeenCommand.class);

        commandsManager.bind("teleport", InstantTeleportCommand.class);
        commandsManager.bind("tphere", TeleportHereCommand.class);
        commandsManager.bind("tpa", DemandedTeleportCommand.class);
        commandsManager.bind("tpaccept", AcceptTeleportCommand.class);
        commandsManager.bind("tpdeny", RefuseTeleportCommand.class);

        commandsManager.bind("rename", RenameItemController.class);
        commandsManager.bind("lore", LoreItemController.class);
        commandsManager.bind("nick", RenamePlayerCommand.class);
        commandsManager.bind("jazda", RideEntityCommand.class);
        commandsManager.bind("speed", SpeedCommand.class);
        commandsManager.bind("colors", ChatColorCommand.class);

        commandsManager.bind("shop", ShopCommand.class);
        commandsManager.bind("tails", TailsCommand.class);
        commandsManager.bind("vehicles", VehiclesCommand.class);
        commandsManager.bind("safety", SafetyCommand.class);
        commandsManager.bind("gamemode", GamemodeCommand.class);
        commandsManager.bind("event", PlayerEventsCommand.class);
        commandsManager.bind("wed", WorldEditDebugCommand.class);
        commandsManager.bind("prefixes", PrefixesCommand.class);
        commandsManager.bind("parkour", ParkourCommand.class);
        commandsManager.bind("gry", GamesCommand.class);
        commandsManager.bind("pet", PetsCommand.class);
        commandsManager.bind("chat", ChatCommand.class);
        commandsManager.bind("broadcast", BroadcastCommand.class);
        commandsManager.bind("message", MessageCommand.class);
        commandsManager.bind("response", ResponseCommand.class);
        commandsManager.bind("sit", SitCommand.class);
        commandsManager.bind("home", HomeCommand.class);
        commandsManager.bind("sethome", SetHomeCommand.class);
        commandsManager.bind("delhome", DelHomeCommand.class);
        commandsManager.bind("warp", WarpCommand.class);
        commandsManager.bind("setwarp", SetWarpCommand.class);
        commandsManager.bind("delwarp", DelWarpCommand.class);
        commandsManager.bind("openenderchest", OpenEnderchestCommand.class);
        commandsManager.bind("openinventory", OpenInventoryCommand.class);
        commandsManager.bind("hat", HatCommand.class);
        commandsManager.bind("adminchat", AdminChatCommand.class);

    }

    @Override
    public void events() {
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SeenListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new UsersListener(), this);
        getServer().getPluginManager().registerEvents(new MoneySignListener(), this);
        getServer().getPluginManager().registerEvents(new SafetyListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);
        getServer().getPluginManager().registerEvents(new ColorsListener(), this);
        getServer().getPluginManager().registerEvents(new ChatBlockadesListener(), this);
        getServer().getPluginManager().registerEvents(new BlocksListener(), this);
        getServer().getPluginManager().registerEvents(new WorldEditListener(), this);
        getServer().getPluginManager().registerEvents(new PrefixesListener(), this);
        getServer().getPluginManager().registerEvents(new AttractionsListener(), this);
        getServer().getPluginManager().registerEvents(new PetsListener(), this);
        getServer().getPluginManager().registerEvents(new SittingListener(), this);
        getServer().getPluginManager().registerEvents(chatCreator, this);

        redstoneListener = new RedstoneListener();
        getServer().getPluginManager().registerEvents(redstoneListener, this);
    }

    // --------

    /**
     * Loads Vault economy environment.
     *
     * @return boolean status of Vault loading.
     */
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        economy = rsp.getProvider();
        return true;
    }

    // --------

    public static Creative getPlugin() {
        return plugin;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ChatConfig getChatConfig() {
        return chatConfig;
    }

    public BlockDetector getBlockDetector() {
        return blockDetector;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }

    public GUIManager getGuiManager() {
        return guiManager;
    }

    public ShopManager getShopManager() {
        return shopManager;
    }

    public DemandsController getDemandsController() {
        return demandsController;
    }

    public UsersController getUserController() {
        return userController;
    }

    public UserComponentsController getComponentsController() {
        return componentsController;
    }

    public VehiclesController getVehiclesController() {
        return vehiclesController;
    }

    public SpecialItemsController getSpecialItemsController() {
        return specialItemsController;
    }

    public PlayerEventsController getPlayerEventsController() {
        return playerEventsController;
    }

    public ChatCreator getChatCreator() {
        return chatCreator;
    }

    public RedstoneListener getRedstoneListener() {
        return redstoneListener;
    }

    public ChatBlockadesController getChatBlockadesController() {
        return chatBlockadesController;
    }

    public Controller<BlockadeAction, String> getBlockadeActionsController() {
        return blockadeActionsController;
    }

    public LimitsController getLimitsController() {
        return limitsController;
    }

    public BlocksController getBlocksController() {
        return blocksController;
    }

    public WorldEditCommandsController getWorldEditCommandsController() {
        return worldEditCommandsController;
    }

    public PrefixesController getPrefixesController() {
        return prefixesController;
    }

    public AttractionsController getAttractionsController() {
        return attractionsController;
    }

    public Controller<ShopSession, Player> getShopSessionsController() {
        return shopSessionsController;
    }

    public SafetyProviderController getSafetyProviderController() {
        return safetyProviderController;
    }

    public Controller<Parkour, String> getParkoursController() { return parkoursController; }

    public PetsController getPetsController() {
        return petsController;
    }

    public PetSessionsController getPetSessionsController() {
        return petSessionsController;
    }

    public PlayablesController getPlayablesController() {
        return playablesController;
    }
}

