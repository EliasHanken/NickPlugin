package me.Streafe.Nick;

import me.Streafe.Nick.listener_package.ScoreBoardUtil;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.util.ArrayList;
import java.util.List;

public class Util {

    private static List<Player> playersOnline = new ArrayList<Player>();

    public Util(){

    }

    public static void sendActionBar(Player player, String message){
        PacketPlayOutChat packet = new PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\":\"" +message+ "\"}"), (byte) 2);

        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    public static boolean isPlayerOnline(Player target){
        for(Player players: Bukkit.getOnlinePlayers()){
            playersOnline.remove(players);
            playersOnline.add(players);
            if(playersOnline.contains(target)){
                return true;
            }
        }
        return false;
    }

    public void updateTab(){

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();

        List<Player> serverPlayers = new ArrayList<Player>();
        for(Player players : Bukkit.getOnlinePlayers()){
            Team t = board.registerNewTeam(players.getName());
            t.addPlayer(players);
            t.setPrefix(ChatColor.translateAlternateColorCodes('&',Nick.getInstance().getPrefix(players)));
            serverPlayers.add(players);
        }
        for(Player pl : serverPlayers){
            pl.setScoreboard(board);
        }

        System.out.println("updated nicks");
    }

    public static ItemStack createItem(Material material, String displayName, String itemLore, Enchantment enchantment, int enchantLvl){
        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();
        meta.addEnchant(enchantment,enchantLvl, true);
        meta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', itemLore));
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayName));

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createItem(Material material, String displayName, String itemLore){
        ItemStack item = new ItemStack(material);

        ItemMeta meta = item.getItemMeta();
        meta.getItemFlags().add(ItemFlag.HIDE_ATTRIBUTES);

        List<String> lore = new ArrayList<String>();
        lore.add("");
        lore.add(ChatColor.translateAlternateColorCodes('&', itemLore));
        meta.setLore(lore);
        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',displayName));

        item.setItemMeta(meta);

        return item;
    }


}
