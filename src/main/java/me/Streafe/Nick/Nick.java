package me.Streafe.Nick;

import me.Streafe.Nick.commands.NickCommand;
import me.Streafe.Nick.database_connection.SQL;
import me.Streafe.Nick.listener_package.ChatControl;
import me.Streafe.Nick.listener_package.ScoreBoardUtil;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Nick extends JavaPlugin implements Listener {

    public static Nick instance;
    public SQL SQL = new SQL();
    public File pFile;

    public void onEnable(){
        instance = this;

        registerCommands();

        getConfig().options().copyDefaults(true);
        saveConfig();

        SQL.connect();

        registerListeners();

        pFile = new File(getDataFolder()+"/"+"players");
        if(!pFile.exists()){
            pFile.mkdirs();
        }

    }

    public static Nick getInstance() {

        return instance;
    }

    public void registerCommands(){
        getCommand("nick").setExecutor(new NickCommand());
    }

    public void registerListeners(){
        Bukkit.getPluginManager().registerEvents(this,this);
        Bukkit.getPluginManager().registerEvents(new ChatControl(), this);
    }

    @EventHandler
    public void createPlayerFiles(PlayerJoinEvent e){

        Player p = e.getPlayer();

        File playerFile = getPlayerFile(p.getName());
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);

        int logins = playerData.getInt("stats.totalLogins");

        playerData.set("stats.totalLogins", logins + 1);
        playerData.set("stats.kills", 0);
        playerData.set("player.uuid", p.getUniqueId().toString());

        if(playerData.get("player.nick") == null){
            playerData.set("player.nick", "");
        }

        if(playerData.get("player.rankPrefix") == null){
            playerData.set("player.rankPrefix", "");
        }


        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();

        if(playerData.get("player.firstLogin") == null){
            playerData.set("player.firstLogin",dateFormat.format(date));
        }


        try {
            playerData.save(playerFile);
        } catch (IOException e1) {
            Bukkit.getServer().getLogger().severe("Could not save " + p.getName() + "'s data file!");
            e1.printStackTrace();
        }

    }

    private File getPlayerFile(String playerName) {

        File pF =  new File (getDataFolder() + File.separator + "players", playerName + ".yml");

        return pF;
    }

    public void setNick(Player p, String nick){

        File playerFile = getPlayerFile(p.getName());
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);



        playerData.set("player.nick", nick);


        try {
            playerData.save(playerFile);
        } catch (IOException e1) {
            Bukkit.getServer().getLogger().severe("Could not save " + p.getName() + "'s data file!");
            e1.printStackTrace();
        }

    }

    public void setRankPrefix(Player p, String prefix){

        File playerFile = getPlayerFile(p.getName());
        FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);



        playerData.set("player.rankPrefix", prefix);


        try {
            playerData.save(playerFile);
        } catch (IOException e1) {
            Bukkit.getServer().getLogger().severe("Could not save " + p.getName() + "'s data file!");
            e1.printStackTrace();
        }

    }


    public String getNick(Player p) {
        try{
            File playerFile = getPlayerFile(p.getName());
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
            String nick;
            nick = playerData.get("player.nick").toString();
            return nick;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
    public String getPrefix(Player p) {
        try{
            File playerFile = getPlayerFile(p.getName());
            FileConfiguration playerData = YamlConfiguration.loadConfiguration(playerFile);
            String prefix;
            prefix = playerData.get("player.rankPrefix").toString();
            return prefix;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }


    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e){
        ScoreBoardUtil.setHeaderandFooter(e.getPlayer());

        Scoreboard board = Bukkit.getScoreboardManager().getNewScoreboard();


            for(Player players : Bukkit.getOnlinePlayers()){
                Team t = board.registerNewTeam(players.getName());
                t.addPlayer(players);
                t.setPrefix(ChatColor.translateAlternateColorCodes('&',getPrefix(players)));
                players.setScoreboard(board);
            }


    }


}
