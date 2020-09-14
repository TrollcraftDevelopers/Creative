package pl.trollcraft.creative.safety.leaks.redstone;

import org.bukkit.Chunk;
import org.bukkit.block.Block;
import pl.trollcraft.creative.Creative;
import pl.trollcraft.creative.safety.leaks.SafetyLeak;

import java.util.Collection;

public class RedstoneLeak implements SafetyLeak {

    private Chunk chunk;
    private Collection<Block> redstone;

    public RedstoneLeak(Chunk chunk, Collection<Block> redstone) {
        this.chunk = chunk;
        this.redstone = redstone;
    }

    @Override
    public boolean solve() {
        RedstoneListener redstoneListener = Creative.getPlugin().getRedstoneListener();
        redstone.forEach( r -> redstoneListener.block(r) );

        return false;
    }

    @Override
    public String message() {
        return "Znaleziono zbyt obciazajace mechanizmy Redstone w " +
                "poblizu lokacji: x=" + chunk.getX() + ", z=" + chunk.getZ();
    }
}
