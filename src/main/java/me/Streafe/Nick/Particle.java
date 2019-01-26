package me.Streafe.Nick;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Particle implements Listener {

    private EnumParticle particle;
    private Player player;
    public static List<Player> hasParticlesOn = new ArrayList<Player>();
    boolean x = true;
    boolean o = false;
    private boolean[][] shape = {
            {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
            {o, o, o, x, o, o, o, o, o, o, o, o, x, o, o, o},
            {o, o, x, x, o, o, o, o, o, o, o, o, x, x, o, o},
            {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
            {o, x, x, x, x, o, o, o, o, o, o, x, x, x, x, o},
            {o, o, x, x, x, x, o, o, o, o, x, x, x, x, o, o},
            {o, o, o, x, x, x, x, o, o, x, x, x, x, o, o, o},
            {o, o, o, o, x, x, x, x, x, x, x, x, o, o, o, o},
            {o, o, o, o, o, x, x, x, x, x, x, o, o, o, o, o},
            {o, o, o, o, o, o, x, x, x, x, o, o, o, o, o, o},
            {o, o, o, o, o, x, x, o, o, x, x, o, o, o, o, o},
            {o, o, o, o, x, x, x, o, o, x, x, x, o, o, o, o},
            {o, o, o, o, x, x, o, o, o, o, x, x, o, o, o, o},
            {o, o, o, o, x, o, o, o, o, o, o, x, o, o, o, o},
            {o, o, o, o, o, o, o, o, o, o, o, o, o, o, o, o},
    };

    public void sendPacket(Packet<?> packet){
        for(Player player : Bukkit.getOnlinePlayers()){
            ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
        }
    }

    public static void createHelix(Player player) {
        Location loc = player.getLocation();
        int radius = 2;
        for(double y = 0; y <= 50; y+=0.05) {
            double x = radius * Math.cos(y);
            double z = radius * Math.sin(y);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(EnumParticle.BARRIER,true, (float) (loc.getX() + x), (float) (loc.getY() + y), (float) (loc.getZ() + z), 0, 0, 0, 0, 1);
            for(Player online : Bukkit.getOnlinePlayers()) {
                ((CraftPlayer)online).getHandle().playerConnection.sendPacket(packet);
            }
        }}
    public static final Vector rotateAroundAxisX(final Vector v, final double angle) {
        final double cos = Math.cos(angle);
        final double sin = Math.sin(angle);
        final double y = v.getY() * cos - v.getZ() * sin;
        final double z = v.getY() * sin + v.getZ() * cos;
        return v.setY(y).setZ(z);
    }

    public static void createSpiralAroundPlayer(final Player p) {
        Particle.hasParticlesOn.add(p);
        new BukkitRunnable(){
            double phi = 0;

            public void run(){
                phi += Math.PI/8;
                double x;
                double y;
                double z;
                Location loc = p.getLocation();
                for(double t = 0; t <= 2*Math.PI; t += Math.PI/16){
                    for(double i = 0; i <= 1; i++){
                        x = 0.3*(2*Math.PI-t)*0.5*Math.cos(t+phi+i*Math.PI);
                        y = 0.5*t;
                        z = 0.3*(2*Math.PI-t)*0.5*Math.sin(t+phi+i*Math.PI);
                        loc.add(x,y,z);
                        World world = loc.getWorld();
                        world.playEffect(loc, Effect.COLOURED_DUST, 1);
                        loc.subtract(x,y,z);
                    }
                }
                if(!p.isOnline() || !Particle.hasParticlesOn.contains(p)){
                    this.cancel(); // Stop spawning particles.
                }
            }
        }.runTaskTimer(Nick.getInstance(), 0, 3);
    }

    public void createWings(Location location) {
        double space = 0.20;
        double defX = location.getX() - (space * shape[0].length / 2) + space;
        double x = defX;
        double y = location.clone().getY() + 2.8;
        double fire = -((location.getYaw() + 180) / 60);
        fire += (location.getYaw() < -180 ? 3.25 : 2.985);

        for (int i = 0; i < shape.length; i++) {
            for (int j = 0; j < shape[i].length; j++) {
                if (shape[i][j]) {

                    Location target = location.clone();
                    target.setX(x);
                    target.setY(y);

                    Vector v = target.toVector().subtract(location.toVector());
                    Vector v2 = getBackVector(location);
                    v = rotateAroundAxisY(v, fire);
                    v2.setY(0).multiply(-0.5);

                    location.add(v);
                    location.add(v2);
                    for (int k = 0; k < 3; k++)
                        location.getWorld().playEffect(location,Effect.FLAME,1);
                    location.subtract(v2);
                    location.subtract(v);
                }
                x += space;
            }
            y -= space;
            x = defX;
        }
    }

    public static Vector rotateAroundAxisY(Vector v, double fire) {
        double x, z, cos, sin;
        cos = Math.cos(fire);
        sin = Math.sin(fire);
        x = v.getX() * cos + v.getZ() * sin;
        z = v.getX() * -sin + v.getZ() * cos;
        return v.setX(x).setZ(z);
    }

    public static Vector getBackVector(Location loc) {
        final float newZ = (float) (loc.getZ() + (1 * Math.sin(Math.toRadians(loc.getYaw() + 90 * 1))));
        final float newX = (float) (loc.getX() + (1 * Math.cos(Math.toRadians(loc.getYaw() + 90 * 1))));
        return new Vector(newX - loc.getX(), 0, newZ - loc.getZ());
    }



    public static void removePlayerFromParticles(final Player player){
        if(hasParticlesOn.contains(player)){
            hasParticlesOn.remove(player);
            Util.sendActionBar(player,"§cParticles Disabled");
        }
    }


}
