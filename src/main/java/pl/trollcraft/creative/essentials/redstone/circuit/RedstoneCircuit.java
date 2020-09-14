package pl.trollcraft.creative.essentials.redstone.circuit;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import org.bukkit.block.data.type.WallSign;
import pl.trollcraft.creative.essentials.redstone.RedstoneHelp;

import java.util.ArrayList;

/**
 * A class representing a redstone
 * circuit with its core (eg. Sign)
 */
public abstract class RedstoneCircuit {

    /**
     * Redstone core.
     */
    protected Block core;

    /**
     * Redstone blocks in circuit.
     */
    protected ArrayList<Block> circuit;

    protected RedstoneCircuit (Block core) {
        this.core = core;
        circuit = new ArrayList<>();

        /*Block middle;
        if (core.getType().na) {

            WallSign wallSign = (WallSign) core.getBlockData();
            middle = core.getRelative(wallSign.getFacing());

            //middle = RedstoneHelp.getMiddle(core);
        }

        else if (core.getType() == Material.SIGN)
            middle = core.getRelative(BlockFace.DOWN);

        else
            throw new IllegalStateException("Redstone Circuit core must be a sign");

        Block redstone = RedstoneHelp.findRedstone(middle);

        calculateBlocks(redstone, null);*/
    }

    private void calculateBlocks(Block block, BlockFace ignore) {

        if (ignore != null)
            Bukkit.getConsoleSender().sendMessage("Processing " + ignore.name());

        if (circuit.contains(block)) return;
        circuit.add(block);

        Block rel;
        for (BlockFace blockFace : BlockFace.values()) {

            if (blockFace == BlockFace.SELF) continue;
            if (ignore != null && blockFace == ignore) continue;

            rel = block.getRelative(blockFace);
            if (rel.getType() == Material.REDSTONE_WIRE)
                calculateBlocks(rel, blockFace.getOppositeFace());

        }

    }

    public abstract void powerOn();

    public void powerOff() {
        circuit.forEach( block -> {

            RedstoneWire wire = (RedstoneWire) block.getBlockData();
            wire.setPower(0);
            block.setBlockData(wire);

        } );
    }

}
