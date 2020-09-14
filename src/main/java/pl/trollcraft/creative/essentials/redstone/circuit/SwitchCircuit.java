package pl.trollcraft.creative.essentials.redstone.circuit;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.type.RedstoneWire;

public class SwitchCircuit extends RedstoneCircuit {

    private boolean powered;

    public SwitchCircuit(Block core) {
        super(core);
        powered = false;
    }

    @Override
    public void powerOn() {

        Bukkit.getConsoleSender().sendMessage(circuit.size() + "");
        powered = true;

        circuit.forEach( block -> {

            RedstoneWire wire = (RedstoneWire) block.getBlockData();
            wire.setPower(15);
            block.setBlockData(wire);

        } );
    }

    @Override
    public void powerOff() {
        super.powerOff();
        powered = false;
    }

    public boolean isPowered() {
        return powered;
    }



}
