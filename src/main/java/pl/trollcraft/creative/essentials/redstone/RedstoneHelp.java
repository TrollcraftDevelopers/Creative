package pl.trollcraft.creative.essentials.redstone;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Directional;

public class RedstoneHelp {

    public static Block getMiddle(Block block) {
        BlockFace blockFace = ((Directional) block.getBlockData()).getFacing();
        Block blockBehindSign = null;
        switch (blockFace) {
            case WEST:
                blockBehindSign = block.getRelative(BlockFace.EAST);
                break;
            case EAST:
                blockBehindSign = block.getRelative(BlockFace.WEST);
                break;
            case NORTH:
                blockBehindSign = block.getRelative(BlockFace.SOUTH);
                break;
            case SOUTH:
                blockBehindSign = block.getRelative(BlockFace.NORTH);
                break;
            default:
                break;
        }

        return blockBehindSign;
    }

    public static Block findRedstone(Block block) {
        Block b;
        for (BlockFace face : BlockFace.values()) {
            b = block.getRelative(face);
            if (b.getType() == Material.REDSTONE_WIRE)
                return b;
        }
        return null;
    }

    public Block getNextRedstone(Block prev) {

        Block rel;
        for (BlockFace face : BlockFace.values()){

            if (face == BlockFace.SELF)
                continue;

            rel = prev.getRelative(face);

            if (rel.getType() == Material.REDSTONE_WIRE)
                return getNextRedstone(rel);

        }

        return null;

    }

}
