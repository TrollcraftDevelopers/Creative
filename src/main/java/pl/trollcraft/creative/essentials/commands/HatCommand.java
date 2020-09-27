package pl.trollcraft.creative.essentials.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pl.trollcraft.creative.core.commands.CommandController;
import pl.trollcraft.creative.core.help.Colors;

public class HatCommand extends CommandController {

    @Override
    public void command(CommandSender sender, String label, String[] args) {
        if(!(sender instanceof Player)){
            sender.sendMessage("Komenda tylko dla graczy!");
            return;
        }

        Player player = (Player) sender;

        if(!player.hasPermission("creative.hat")){
            sender.sendMessage(Colors.color("&cNie masz dostepu do tej komendy!"));
            return;
        }

        ItemStack itemStack = player.getItemInHand();

        if(itemStack == null || itemStack.getType().equals(Material.AIR)){
            sender.sendMessage(Colors.color("&cMusisz trzymac przedmiot w reku!"));
            return;
        }

        if(player.getInventory().getHelmet() != null){
            if(player.getInventory().getHelmet().getType().equals(Material.AIR)){
                player.getInventory().setItemInHand(player.getInventory().getHelmet());
                player.getInventory().setHelmet(itemStack);
            }
        }
        else{
            player.getInventory().setHelmet(itemStack);
            player.getInventory().setItemInHand(new ItemStack(Material.AIR, 1));
        }


        sender.sendMessage(Colors.color("&aMasz nowa czapke!"));
    }
}
