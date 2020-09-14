package pl.trollcraft.creative.essentials.redstone;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockRedstoneEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.essentials.redstone.signals.RedstoneSignal;
import pl.trollcraft.creative.essentials.redstone.signals.SignalsController;

public class RedstoneSignSignalizer implements Listener {

    private SignalsController signalsController = Creative.getPlugin().getSignalsController();

    @EventHandler
    public void onInteract (PlayerInteractEvent event) {

        Block block = event.getClickedBlock();

        /*if (!(block.getType() == Material.SIGN || block.getType() == Material.WALL_SIGN))
            return;*/

        RedstoneSignal signal = signalsController.find(block);

        if (signal == null)
            return;

        else {

            if (signal.isPowered()) {
                signal.cut();
                event.getPlayer().sendMessage("Cut");
            }
            else {
                signal.power();
                event.getPlayer().sendMessage("Power");
            }
        }

    }

    @EventHandler
    public void onRedstone (BlockRedstoneEvent event) {
        Block block = event.getBlock();

        if (signalsController.check(block)) {
            event.setNewCurrent(event.getOldCurrent());
            Bukkit.getConsoleSender().sendMessage("Powering");
        }
    }

    /*@Deprecated
    public void power(Block block, BlockFace ignore) {

        if (ignore != null)
            Bukkit.getConsoleSender().sendMessage("Processing " + ignore.name());

        if (fakeRedstoneBlocks.contains(block)) return;

        RedstoneWire wire = (RedstoneWire) block.getBlockData();
        if (wire.getPower() > 0) return;
        wire.setPower(wire.getMaximumPower());
        block.setBlockData(wire);
        fakeRedstoneBlocks.add(block);

        Block rel;
        for (BlockFace blockFace : BlockFace.values()) {

            if (blockFace == BlockFace.SELF) continue;
            if (ignore != null && blockFace == ignore) continue;

            rel = block.getRelative(blockFace);
            if (rel.getType() == Material.REDSTONE_WIRE)
                power(rel, blockFace.getOppositeFace());

        }

    }*/

}
