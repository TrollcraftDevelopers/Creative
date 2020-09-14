package pl.trollcraft.creative.essentials.redstone.signals;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.type.RedstoneWire;
import pl.trollcraft.creative.essentials.redstone.RedstoneHelp;

import java.util.ArrayList;

public class RedstoneSignal {

    private Block core;

    private ArrayList<Block> relays;
    private boolean powered;

    public RedstoneSignal(Block sign) {
        core = sign;
        relays = new ArrayList<>();
        power(RedstoneHelp.getMiddle(sign));

        Bukkit.getConsoleSender().sendMessage("Powering up redstone");

    }

    public void power() {
        relays.clear();
        power(RedstoneHelp.getMiddle(core));

        powered = true;
        RedstoneWire wire;
        for (Block b : relays) {
            wire = (RedstoneWire) b.getBlockData();
            wire.setPower(15);
            b.setBlockData(wire);
        }

    }

    private void power(Block middle) {

        Block rel;
        for (BlockFace face : BlockFace.values()){

            if (face == BlockFace.SELF)
                continue;

            rel = middle.getRelative(face);

            if (rel.getType() == Material.REDSTONE_WIRE) {

                if (!relays.contains(rel)) {
                    relays.add(rel);
                    power(rel);
                }

            }

        }

    }

    public void cut() {
        powered = false;
    }

    public Block getCore() {
        return core;
    }

    public ArrayList<Block> getRelays() {
        return relays;
    }

    public boolean isPowered() {
        return powered;
    }
}
