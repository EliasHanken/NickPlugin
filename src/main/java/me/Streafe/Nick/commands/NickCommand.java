package me.Streafe.Nick.commands;

import me.Streafe.Nick.Nick;
import me.Streafe.Nick.listener_package.ScoreBoardUtil;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(command.getName().equalsIgnoreCase("nick")){
                if(args.length >= 1 && args[0] != null){
                    if(args[0].equalsIgnoreCase(p.getName())) {
                        p.sendMessage("§aNick: §fError: You can't make your name your own, do /nick reset");
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("reset")){
                        p.sendMessage("§aNick: §fYou have reset your name");
                        return true;
                    }

                    if((!args[0].equalsIgnoreCase("reset")) && (!args[0].equalsIgnoreCase(p.getName()))){

                        String nick = "";
                        for(int i = 0; i<args.length; i++){
                            nick = nick + args[i] + " ";


                        }


                        Nick.getInstance().setNick(p,(nick.trim()));
                        p.sendMessage("§aNick: §fYou have updated your nick to: " + ChatColor.translateAlternateColorCodes('&', nick.trim()));
                        //ScoreBoardUtil.teamCreatorAndSet(p, args[0]);
                    }

                }else {
                    p.sendMessage("§aNick: §fError, Usage: /nick <name> (or) <reset>");
                }


            }
        }

        return true;
    }
}
