package pl.trollcraft.creative;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.scheduler.BukkitRunnable;
import pl.trollcraft.creative.chat.AutoMessages;
import pl.trollcraft.creative.chat.ChatListener;
import pl.trollcraft.creative.chat.blockades.ChatBlockadesController;
import pl.trollcraft.creative.chat.blockades.ChatBlockadesListener;
import pl.trollcraft.creative.chat.blockades.actions.BlockAction;
import pl.trollcraft.creative.chat.blockades.actions.BlockadeAction;
import pl.trollcraft.creative.chat.commands.*;
import pl.trollcraft.creative.chat.commands.socialspy.SocialSpyCommand;
import pl.trollcraft.creative.chat.commands.socialspy.SocialSpyManager;
import pl.trollcraft.creative.chat.config.ChatConfig;
import pl.trollcraft.creative.core.Perspectivum;
import pl.trollcraft.creative.core.controlling.Controller;
import pl.trollcraft.creative.core.gui.GUIManager;
import pl.trollcraft.creative.core.gui.InventoryListener;
import pl.trollcraft.creative.core.help.WorldLoader;
import pl.trollcraft.creative.core.help.blockades.BlockadesController;
import pl.trollcraft.creative.core.help.blocksdetector.BlockDetector;
import pl.trollcraft.creative.core.help.chatcreator.ChatCreator;
import pl.trollcraft.creative.core.help.demands.DemandsController;
import pl.trollcraft.creative.core.help.movement.MovementDetector;
import pl.trollcraft.creative.core.user.UserComponentsController;
import pl.trollcraft.creative.core.user.UsersController;
import pl.trollcraft.creative.core.user.UsersListener;
import pl.trollcraft.creative.essentials.adminchat.AdminChatCommand;
import pl.trollcraft.creative.essentials.antyafk.AntyAfkManager;
import pl.trollcraft.creative.essentials.colors.ChatColorCommand;
import pl.trollcraft.creative.essentials.colors.ChatColorsComponent;
import pl.trollcraft.creative.essentials.colors.ColorsListener;

import pl.trollcraft.creative.essentials.colors.data.ChatColorDataController;
import pl.trollcraft.creative.essentials.commands.*;

import pl.trollcraft.creative.essentials.commands.GamemodeCommand;
import pl.trollcraft.creative.essentials.giving.GiveCommand;
import pl.trollcraft.creative.essentials.giving.ItemsController;
import pl.trollcraft.creative.essentials.plots.MorePlotsCommand;
import pl.trollcraft.creative.essentials.plots.PlotsData;
import pl.trollcraft.creative.essentials.seen.SeenComponent;
import pl.trollcraft.creative.safety.limits.chunks.ChunkLimitsController;
import pl.trollcraft.creative.safety.limits.chunks.ChunkLimitsListener;
import pl.trollcraft.creative.safety.limits.file.LimitFileCommand;
import pl.trollcraft.creative.safety.limits.file.LimitFilesController;
import pl.trollcraft.creative.safety.players.VoidFallPreventer;
import pl.trollcraft.creative.safety.vehicles.AbstractVehiclesController;
import pl.trollcraft.creative.safety.vehicles.VehiclesListener;
import pl.trollcraft.creative.safety.worldedit.listener.WorldEditLimits;
import pl.trollcraft.creative.services.pets.RideEntityCommand;
import pl.trollcraft.creative.essentials.commands.SpeedCommand;

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
import pl.trollcraft.creative.essentials.teleport.demanded.TeleportToggleCommand;
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
import pl.trollcraft.creative.services.vehicles.manage.DeleteVehiclesCommand;
import pl.trollcraft.creative.shops.ShopCommand;
import pl.trollcraft.creative.essentials.commands.naming.LoreItemController;
import pl.trollcraft.creative.essentials.commands.naming.RenameItemController;
import pl.trollcraft.creative.shops.ShopManager;
import pl.trollcraft.creative.shops.ShopSession;

import java.util.logging.Level;

public final class Creative extends Perspectivum {

    private static Creative plugin;

    private Economy economy;

    private PlotsData plotsData;

    private BlockDetector blockDetector;
    private MovementDetector movementDetector;

    private ProtocolManager protocolManager;
    private GUIManager guiManager;
    private ShopManager shopManager;
    private SocialSpyManager socialSpyManager;
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

    private WorldEditCommandsController worldEditCommandsController;
    private PrefixesController prefixesController;
    private BlockadesController blockadesController;
    private AbstractVehiclesController abstractVehiclesController;
    private ItemsController itemsController;
    private ChatColorDataController chatColorDataController;
    private LimitFilesController limitFilesController;

    private AttractionsController attractionsController;
    private PlayablesController playablesController;

    private Controller<ShopSession, Player> shopSessionsController;
    private Controller<Parkour, String> parkoursController;
    private PetsController petsController;
    private PetSessionsController petSessionsController;

    private ChunkLimitsController chunkLimitsController;

    private ChatCreator chatCreator;

    private RedstoneListener redstoneListener;

    @Override
    public void init() {
        plugin = this;
        SpecialEnchantmentRegister.register(this);
        WorldLoader.load();
        blockDetector = new BlockDetector(this);
        blockDetector.monitor();
        movementDetector = MovementDetector.newInstance();
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

        plotsData = new PlotsData();
        plotsData.load();

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
        //safetyProviderController = new SafetyProviderController(this);
        componentsController = new UserComponentsController();

        componentsController.register(TailsComponent.COMP_NAME, TailsComponent.class);
        componentsController.register(VehiclesComponent.COMP_NAME, VehiclesComponent.class);
        componentsController.register(PrefixesComponent.COMP_NAME, PrefixesComponent.class);
        componentsController.register(WorldEditComponent.COMP_NAME, WorldEditComponent.class);
        componentsController.register(PetsComponent.COMP_NAME, PetsComponent.class);
        componentsController.register(SeenComponent.COMP_NAME, SeenComponent.class);
        componentsController.register(ChatColorsComponent.COMP_NAME, ChatColorsComponent.class);

        limitFilesController = new LimitFilesController();
        limitFilesController.load();
        limitFilesController.configure();

        worldEditCommandsController = new WorldEditCommandsController();
        chunkLimitsController = new ChunkLimitsController();

        demandsController = new DemandsController();
        userController = new UsersController();
        vehiclesController = new VehiclesController();
        specialItemsController = new SpecialItemsController();
        playerEventsController = new PlayerEventsController();
        blockadeActionsController = new Controller<>();
        blockadeActionsController.register(new BlockAction());
        chatBlockadesController = new ChatBlockadesController();

        prefixesController = new PrefixesController();

        blockadesController = new BlockadesController();
        blockadesController.newInstance("messages");

        attractionsController = new AttractionsController();
        playablesController = new PlayablesController();

        shopSessionsController = new Controller<>();
        parkoursController = new Controller<>();
        petsController = new PetsController();
        petSessionsController = new PetSessionsController();

        abstractVehiclesController = new AbstractVehiclesController();

        chatColorDataController = new ChatColorDataController();
        chatColorDataController.load();

        new BukkitRunnable() {

            @Override
            public void run() {
                abstractVehiclesController.load();
                Warp.load();
            }

        }.runTaskLater(this, 20*10);


        itemsController = new ItemsController();
        itemsController.load();

        chatBlockadesController.load();
        vehiclesController.load();
        specialItemsController.load();
        worldEditCommandsController.load();
        prefixesController.load();
    }

    @Override
    public void managers() {
        protocolManager = ProtocolLibrary.getProtocolManager();

        guiManager = new GUIManager();

        shopManager = new ShopManager();
        shopManager.init();

        socialSpyManager = SocialSpyManager.newInstance();

        chatCreator = new ChatCreator();

        Tail.enable();

        SpecialItemsListener.listen();

        ScheduledMoneyManager.init();

        VoidFallPreventer.observePlayers();
    }

    @Override
    public void commands() {
        getCommandsManager().bind("spawn", SpawnCommand.class);
        getCommandsManager().bind("setspawn", SetSpawnCommand.class);

        getCommandsManager().bind("seen", SeenCommand.class);
        getCommandsManager().bind("give", GiveCommand.class);

        getCommandsManager().bind("teleport", InstantTeleportCommand.class);
        getCommandsManager().bind("tphere", TeleportHereCommand.class);
        getCommandsManager().bind("tpa", DemandedTeleportCommand.class);
        getCommandsManager().bind("tpaccept", AcceptTeleportCommand.class);
        getCommandsManager().bind("tpdeny", RefuseTeleportCommand.class);
        getCommandsManager().bind("tptoggle", TeleportToggleCommand.class);

        getCommandsManager().bind("rename", RenameItemController.class);
        getCommandsManager().bind("lore", LoreItemController.class);
        getCommandsManager().bind("nick", RenamePlayerCommand.class);
        getCommandsManager().bind("jazda", RideEntityCommand.class);
        getCommandsManager().bind("speed", SpeedCommand.class);
        getCommandsManager().bind("colors", ChatColorCommand.class);
        getCommandsManager().bind("plots", MorePlotsCommand.class);
        getCommandsManager().bind("ignore", IgnoreCommand.class);
        getCommandsManager().bind("unignore", UnignoreCommand.class);

        getCommandsManager().bind("shop", ShopCommand.class);
        getCommandsManager().bind("tails", TailsCommand.class);
        getCommandsManager().bind("vehicles", VehiclesCommand.class);
        getCommandsManager().bind("safety", SafetyCommand.class);
        getCommandsManager().bind("gamemode", GamemodeCommand.class);
        getCommandsManager().bind("event", PlayerEventsCommand.class);
        getCommandsManager().bind("togglepm", ToggleMessagesCommand.class);
        getCommandsManager().bind("wed", WorldEditDebugCommand.class);
        getCommandsManager().bind("prefixes", PrefixesCommand.class);
        getCommandsManager().bind("parkour", ParkourCommand.class);
        getCommandsManager().bind("gry", GamesCommand.class);
        getCommandsManager().bind("pet", PetsCommand.class);
        getCommandsManager().bind("chat", ChatCommand.class);
        getCommandsManager().bind("broadcast", BroadcastCommand.class);
        getCommandsManager().bind("message", MessageCommand.class);
        getCommandsManager().bind("socialspy", SocialSpyCommand.class);
        getCommandsManager().bind("response", ResponseCommand.class);
        getCommandsManager().bind("sit", SitCommand.class);
        getCommandsManager().bind("home", HomeCommand.class);
        getCommandsManager().bind("sethome", SetHomeCommand.class);
        getCommandsManager().bind("delhome", DelHomeCommand.class);
        getCommandsManager().bind("warp", WarpCommand.class);
        getCommandsManager().bind("setwarp", SetWarpCommand.class);
        getCommandsManager().bind("delwarp", DelWarpCommand.class);
        getCommandsManager().bind("openenderchest", OpenEnderchestCommand.class);
        getCommandsManager().bind("openinventory", OpenInventoryCommand.class);
        getCommandsManager().bind("hat", HatCommand.class);
        getCommandsManager().bind("adminchat", AdminChatCommand.class);
        getCommandsManager().bind("deletevehicles", DeleteVehiclesCommand.class);
        getCommandsManager().bind("lf", LimitFileCommand.class);


    }
    @Override
    public void events() {

        getServer().getPluginManager().registerEvents(new ChatListener(), this);
        getServer().getPluginManager().registerEvents(new SeenListener(), this);
        getServer().getPluginManager().registerEvents(new InventoryListener(), this);
        getServer().getPluginManager().registerEvents(new UsersListener(), this);
        getServer().getPluginManager().registerEvents(new MoneySignListener(), this);
        getServer().getPluginManager().registerEvents(new SafetyListener(), this);
        getServer().getPluginManager().registerEvents(new VehiclesListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerEventsListener(), this);
        getServer().getPluginManager().registerEvents(new ColorsListener(), this);
        getServer().getPluginManager().registerEvents(new ChatBlockadesListener(), this);
        getServer().getPluginManager().registerEvents(new WorldEditListener(), this);
        getServer().getPluginManager().registerEvents(new PrefixesListener(), this);
        getServer().getPluginManager().registerEvents(new AttractionsListener(), this);
        getServer().getPluginManager().registerEvents(new PetsListener(), this);
        getServer().getPluginManager().registerEvents(new SittingListener(), this);

        getServer().getPluginManager().registerEvents(new WorldEditLimits(), this);
        getServer().getPluginManager().registerEvents(new ChunkLimitsListener(), this);

        getServer().getPluginManager().registerEvents(AntyAfkManager.newInstance(), this);

        getServer().getPluginManager().registerEvents(chatCreator, this);

        redstoneListener = new RedstoneListener();
        getServer().getPluginManager().registerEvents(redstoneListener, this);
    }

    @Override
    public void onDisable() {
        abstractVehiclesController.save();
        chunkLimitsController.save();
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

    public SocialSpyManager getSocialSpyManager() {
        return socialSpyManager;
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

    public BlockadesController getBlockadesController() {
        return blockadesController;
    }

    public AbstractVehiclesController getAbstractVehiclesController() {
        return abstractVehiclesController;
    }

    public PlotsData getPlotsData() {
        return plotsData;
    }

    public ItemsController getItemsController() {
        return itemsController;
    }

    public ChatColorDataController getChatColorDataController() {
        return chatColorDataController;
    }

    public LimitFilesController getLimitFilesController() {
        return limitFilesController;
    }

    public ChunkLimitsController getChunkLimitsController() {
        return chunkLimitsController;
    }
}

