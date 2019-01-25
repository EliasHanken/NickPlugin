package me.Streafe.Nick.listener_package;

import me.Streafe.Nick.Nick;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatControl implements Listener {

    @EventHandler
    public void onChat(AsyncPlayerChatEvent e){
        Player p = (Player) e.getPlayer();

        e.setCancelled(true);



        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', Nick.getInstance().getNick(p)) + "§f: " + e.getMessage());
    }
}
