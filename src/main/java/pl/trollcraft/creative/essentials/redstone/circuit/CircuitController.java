package pl.trollcraft.creative.essentials.redstone.circuit;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import pl.trollcraft.creative.core.controlling.Controller;

import java.util.Optional;

public class CircuitController extends Controller<RedstoneCircuit, Block> {

    @Override
    public RedstoneCircuit find(Block id) {

        Optional<RedstoneCircuit> opt = instances.stream()
                .filter( c -> c.core.equals(id))
                .findFirst();

        if (opt.isPresent())
            return opt.get();
        return null;

    }


    public RedstoneCircuit findInCircuits(Block id) {

        Optional<RedstoneCircuit> opt = instances.stream()
                .filter( c -> c.circuit.contains(id))
                .findFirst();

        if (opt.isPresent()) {
            Bukkit.getConsoleSender().sendMessage("found");
            return opt.get();
        }

        return null;

    }
}
