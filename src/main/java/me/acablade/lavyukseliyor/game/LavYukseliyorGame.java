package me.acablade.lavyukseliyor.game;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.lavyukseliyor.utils.LocationUtil;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Acablade/oz
 */
public class LavYukseliyorGame extends AbstractGame {

    private int currentLavaLevel = -64;

    private int resetCount = 0;

    private Location min;
    private Location max;

    public LavYukseliyorGame(String name, JavaPlugin plugin) {
        super(name, plugin);
    }

    @Override
    public void onEnable() {
        this.min = LocationUtil.getLocation(getPlugin().getConfig().getConfigurationSection("locations").getConfigurationSection("min"));
        this.max = LocationUtil.getLocation(getPlugin().getConfig().getConfigurationSection("locations").getConfigurationSection("max"));
        Location center = getCenter();
        center.getWorld().getWorldBorder().setCenter(center);
        center.getWorld().getWorldBorder().setSize(max.getBlockX()-min.getBlockX());
        center.getWorld().setSpawnLocation(center.getWorld().getHighestBlockAt(center.getBlockX(), center.getBlockZ()).getLocation().add(0,1,0));
        getPlugin().getLogger().info(getName() + " oyun aktif!");
    }

    @Override
    public void onDisable() {
        super.onDisable();
        getPlugin().getLogger().info(getName() + " oyun devre dışı!");
    }

    public Location getMax() {
        return max;
    }

    public Location getMin() {
        return min;
    }

    public Location getCenter() {
        return LocationUtil.getCenter(min,max);
    }

    public int getCurrentLavaLevel() {
        return currentLavaLevel;
    }

    public void setCurrentLavaLevel(int currentLavaLevel) {
        this.currentLavaLevel = currentLavaLevel;
    }

    public int getResetCount() {
        return resetCount;
    }

    public void setResetCount(int resetCount) {
        this.resetCount = resetCount;
    }

    public Set<UUID> getAllPlayers(){
        Set<UUID> allPlayerUUIDs = new HashSet<>(getGameData().getPlayerList());
        allPlayerUUIDs.addAll(getGameData().getSpectatorList());
        return  allPlayerUUIDs;
    }


}
