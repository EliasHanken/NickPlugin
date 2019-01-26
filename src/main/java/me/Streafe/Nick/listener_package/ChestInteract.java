package me.Streafe.Nick.listener_package;

import me.Streafe.Nick.CloakFireWings;
import me.Streafe.Nick.Particle;
import me.Streafe.Nick.Util;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ChestInteract implements Listener {

    @EventHandler
    public void chestInteract(InventoryClickEvent e){
        Player player = (Player) e.getWhoClicked();
        if(Particle.hasParticlesOn.contains(player) && e.getCurrentItem().getType().equals(Material.BLAZE_ROD) || (e.getCurrentItem().getType().equals(Material.YELLOW_FLOWER) && Particle.hasParticlesOn.contains(player))){
            player.sendMessage("§cError: you cant use more than 1 at the same time");
            e.setCancelled(true);
            return;
        }
        if(e.getView().getTopInventory().getTitle().equalsIgnoreCase("particles") && e.getCurrentItem().getType().equals(Material.BLAZE_ROD)){
            e.getWhoClicked().closeInventory();
            Util.sendActionBar((Player) e.getWhoClicked(),"§aHelix");
            e.setCancelled(true);
            Particle.createSpiralAroundPlayer(player);
        }
        if(e.getView().getTopInventory().getTitle().equalsIgnoreCase("particles") && e.getCurrentItem().getType().equals(Material.BARRIER)){
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
            Particle.removePlayerFromParticles((Player) e.getWhoClicked());
        }
        if(e.getView().getTopInventory().getTitle().equalsIgnoreCase("particles") && e.getCurrentItem().getType().equals(Material.YELLOW_FLOWER)){
            e.getWhoClicked().closeInventory();
            e.setCancelled(true);
            //Particle particle = new Particle();
            //particle.createWings(player.getLocation());
            CloakFireWings cfw = new CloakFireWings(player);
            cfw.update();

        }


    }
}
