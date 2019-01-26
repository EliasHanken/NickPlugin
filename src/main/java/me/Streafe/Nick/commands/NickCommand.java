package me.Streafe.Nick.commands;

import me.Streafe.Nick.Nick;
import me.Streafe.Nick.Util;
import me.Streafe.Nick.listener_package.ScoreBoardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class NickCommand implements CommandExecutor {

    Util util = new Util();

    public boolean onCommand(CommandSender sender, Command command, String label, String args[]) {

        if(sender instanceof Player){
            Player p = (Player) sender;

            if(command.getName().equalsIgnoreCase("nick")){
                util.updateTab();
                if(args.length >= 1 && args[0] != null){
                    if(args[0].equalsIgnoreCase("help")){
                        p.sendMessage("§8//*------------------------------*");
                        p.sendMessage("§7This is the list of commands");
                        p.sendMessage("§7for the §aNickPlugin");
                        p.sendMessage("§a/nick help §7- brings up this menu");
                        p.sendMessage("§a/nick prefix <prefix> §7- sets your prefix in tab");
                        p.sendMessage("§a/nick <nick> §7- sets your nick in chat");
                        p.sendMessage("§a/nick <player> prefix <prefix> §7- sets a players' prefix in tab");
                        p.sendMessage("§a/nick <player> <nick> §7- sets a players' nick");
                        p.sendMessage("§a/nick reset §7- resets your nick");
                        p.sendMessage("§a/nick version §7- checks version");
                        p.sendMessage("§cBugs: §7You have enter §a/nick <player> prefix <prefix> ");
                        p.sendMessage("§7twice, in order for it to update!");
                        return true;
                    }
                    if (args[0].equalsIgnoreCase("version")) {
                        p.sendMessage("§7[§aNick§7] ");
                        p.sendMessage("§aversion: §7" + Nick.getInstance().getConfig().get("version").toString());
                        p.sendMessage("§adeveloper: §7 Streafe");
                        return true;
                    }
                    Player target = Bukkit.getPlayerExact(args[0]);

                    if(Util.isPlayerOnline(target) && args[1].equalsIgnoreCase("prefix") && args[0].equalsIgnoreCase(target.getName())){
                        String prefix = "";
                        for(int i = 2; i<args.length; i++){
                            prefix = prefix + args[i] + " ";
                        }
                        Nick.getInstance().setRankPrefix(target, prefix.trim());
                        p.sendMessage("§aNick: §fyou have updated " + target.getName() + "'s prefix to " + prefix.trim());
                        target.sendMessage("§aNick: §fyour prefix was updated to " + prefix.trim() + " by " + Nick.getInstance().getNick(p));
                        return true;
                    }
                    if(Util.isPlayerOnline(target) && !args[1].equalsIgnoreCase("prefix") && args[0].equalsIgnoreCase(target.getName())){
                        String nick = "";
                        for(int i = 2; i<args.length; i++){
                            nick = nick + args[i] + " ";
                        }
                        Nick.getInstance().setNick(target, nick.trim());
                        p.sendMessage("§aNick: §fyou have updated " + target.getName() + "'s nick to " + nick.trim());
                        target.sendMessage("§aNick: §fyour nick was updated to " + nick.trim() + " by " + Nick.getInstance().getNick(p));
                        return true;
                    }

                    if(args[0].length() > 16){
                        p.sendMessage("§aNick: §cError, your nick's maximum lenght is 16");
                        return true;
                    }
                    if(args[0].equalsIgnoreCase(p.getName())) {
                        p.sendMessage("§aNick: §fError: You can't make your name your own, do /nick reset");
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("reset")){
                        p.sendMessage("§aNick: §fYou have reset your name");
                        return true;
                    }
                    if(args[0].equalsIgnoreCase("prefix") && !Util.isPlayerOnline(target)){
                        String prefix = "";
                        for(int i = 1; i<args.length; i++){
                            prefix = prefix + args[i] + " ";
                        }
                        Nick.getInstance().setRankPrefix(p,prefix.trim());
                        p.sendMessage("§aNick: §fYou have updated your rank prefix to " + ChatColor.translateAlternateColorCodes('&', prefix.trim()));
                        return true;
                    }

                    if((!args[0].equalsIgnoreCase("reset")) && (!args[0].equalsIgnoreCase(p.getName()))){

                        String nick = "";
                        for(int i = 0; i<args.length; i++){
                            nick = nick + args[i] + " ";


                        }


                        Nick.getInstance().setNick(p,(nick.trim()));
                        p.sendMessage("§aNick: §fYou have updated your nick to: " + ChatColor.translateAlternateColorCodes('&', nick.trim()));
                        Util.sendActionBar(p,"Nick = " + ChatColor.translateAlternateColorCodes('&', nick.trim()));
                        //ScoreBoardUtil.teamCreatorAndSet(p, args[0]);
                    }

                }else {
                    p.sendMessage("§aNick: §f/nick help §7- brings up the help list");
                }


            }
        }

        return true;
    }
}
