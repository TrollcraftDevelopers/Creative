package pl.trollcraft.creative.essentials.redstone.signals;

import org.bukkit.block.Block;
import pl.trollcraft.creative.core.controlling.Controller;

import java.util.Optional;

public class SignalsController extends Controller<RedstoneSignal, Block> {

    @Override
    public RedstoneSignal find(Block id) {

        Optional<RedstoneSignal> opt = instances.stream()
                .filter( sig -> sig.getCore().equals(id) )
                .findAny();

        return opt.orElse(null);

    }

    public boolean check(Block redstone) {

        Optional<RedstoneSignal> opt = instances.stream()
                .filter( sig -> sig.getRelays().contains(redstone) )
                .filter( sig -> sig.isPowered() )
                .findAny();

        return opt.isPresent();

    }

}
