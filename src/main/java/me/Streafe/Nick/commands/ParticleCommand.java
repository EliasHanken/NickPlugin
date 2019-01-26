package me.Streafe.Nick.commands;

import me.Streafe.Nick.Util;
import me.Streafe.Nick.gui.EasyGUI;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class ParticleCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(command.getName().equalsIgnoreCase("particles")){
                EasyGUI gui = new EasyGUI("particles",27);
                ItemStack item = Util.createItem(Material.BLAZE_ROD, "&dHelix", "&7Beautifully simple");
                gui.setItem(13,item);
                ItemStack barrier = Util.createItem(Material.BARRIER, "&cRemove all particles", "");
                gui.setItem(22, barrier);
                ItemStack fire = Util.createItem(Material.YELLOW_FLOWER, "&6Wings", "&7Wings !");
                gui.setItem(11, fire);
                gui.openInventory(p);
                return true;
            }else {
                p.sendMessage("§aParticles: §cMore commands comming, use /particles");
            }
        }
        return false;
    }

}
