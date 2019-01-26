package me.Streafe.Nick.gui;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class EasyGUI {

    private Inventory inv;

    public EasyGUI(String containerName){
        this.inv = Bukkit.createInventory(null,45,containerName);
    }
    public EasyGUI(String containerName, int value){
        this.inv = Bukkit.createInventory(null,value,containerName);
    }


    public Inventory getInv() {
        return inv;
    }

    public void setInv(Inventory inv) {
        this.inv = inv;
    }

    public void setItem(int index, ItemStack item){
        this.inv.setItem(index,item);
    }

    public void openInventory(Player player){
        player.openInventory(inv);
    }
}
