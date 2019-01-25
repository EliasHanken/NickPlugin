package me.Streafe.Nick.listener_package;

import me.Streafe.Nick.Nick;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.lang.reflect.Field;

public class ScoreBoardUtil {


    public static void setHeaderandFooter(Player p){

        String header = "§6mc.streafe.net §d/nick";
        String footer = "§6Homepage: mc.streafe.net \n§eRanks available:\n\n§7Default §ePro §ePro§c+ §bSupporter\n\n §aEnjoy your stay!";

        /*
        String header = Nick.getInstance().getConfig().get("server.header");
        String header = Nick.getInstance().getConfig().get("server.footer");
        */

        CraftPlayer cplayer = (CraftPlayer) p;
        PlayerConnection connection = cplayer.getHandle().playerConnection;

        IChatBaseComponent top = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + header + "\"}");
        IChatBaseComponent down = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + footer + "\"}");

        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();

        try{
            Field headerField = packet.getClass().getDeclaredField("a");
            headerField.setAccessible(true);
            headerField.set(packet,top);
            headerField.setAccessible(!headerField.isAccessible());

            Field footerField = packet.getClass().getDeclaredField("b");
            footerField.setAccessible(true);
            footerField.set(packet,down);
            footerField.setAccessible(!headerField.isAccessible());
        }catch(Exception e){
            e.printStackTrace();
        }

        connection.sendPacket(packet);

        p.sendMessage("§aNick: §fYou have been added to your group ");

        //TODO CREATE SERIALIZATION OF PLAYERS KIT including placement

    }

}
