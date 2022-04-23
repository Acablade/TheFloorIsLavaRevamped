package me.acablade.lavyukseliyor.utils;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

/**
 * @author Acablade/oz
 */
public class LocationUtil {

    public static Location getLocation(ConfigurationSection section){

        int x = section.getInt("x");
        int y = section.getInt("y");
        int z = section.getInt("z");
        World world = Bukkit.getWorld(section.getString("world"));

        return new Location(world,x,y,z);
    }

    public static void setLocation(Location location, ConfigurationSection section){
        section.set("x",location.getBlockX());
        section.set("y",location.getBlockY());
        section.set("z",location.getBlockZ());
        section.set("world",location.getWorld().getName());
    }

    public static Location getCenter(Location location1, Location location2){
        return new Location(
                location1.getWorld(),
                (location1.getX() + location2.getX())/2,
                (location1.getY() + location2.getY())/2,
                (location1.getZ() + location2.getZ())/2);
    }

}
