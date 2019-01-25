package me.Streafe.Nick.database_connection;

import me.Streafe.Nick.Nick;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.sql.*;

public class SQL {

    private String database;
    private String user;
    private String pw;
    private String host;
    private Connection con;

    public void connect(){

        try{
            this.host = Nick.getInstance().getConfig().get("mysql.host").toString();
            this.database = Nick.getInstance().getConfig().get("mysql.database").toString();
            this.user = Nick.getInstance().getConfig().get("mysql.username").toString();
            this.pw = Nick.getInstance().getConfig().get("mysql.password").toString();

            con = DriverManager.getConnection("jdbc:mysql://"+this.host+":"+3306+"/"+this.database,this.user,this.pw);
            System.out.println("§aSQL: §fDatabase successfully connected");
            createTables();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Connection getCon(){
        return con;
    }

    public void createTables(){
        try{
            Statement st = con.createStatement();

            st.executeUpdate("CREATE TABLE IF NOT EXISTS player_nick (PlayerUUID varchar(255), PlayerNick varchar(255))");
            System.out.println("§aSQL: §fTables successfully created");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void defaultNick(){
        try{
            Statement st = con.createStatement();
            st.executeUpdate("INSERT ");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }

    public boolean isPlayerExists(OfflinePlayer player){
        try{
            PreparedStatement ps = con.prepareStatement("SELECT * FROM player_nick WHERE PlayerUUID = ?");
            ps.setString(1,player.getUniqueId().toString());
            Statement st = con.createStatement();

        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

}
