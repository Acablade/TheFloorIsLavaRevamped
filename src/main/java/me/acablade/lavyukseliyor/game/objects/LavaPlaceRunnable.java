package me.acablade.lavyukseliyor.game.objects;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.lavyukseliyor.LavYukseliyorPlugin;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.utils.PlaceableBlock;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;

import java.util.Objects;

/**
 * @author Acablade/oz
 */
public class LavaPlaceRunnable implements Runnable{

    private static int taskId = -1;

    public static int getTaskId() {
        return taskId;
    }

    public static void setTaskId(int taskId) {
        LavaPlaceRunnable.taskId = taskId;
    }

    private final LavYukseliyorPlugin plugin;
    private final LavYukseliyorGame game;
    private final int yMultiplier;

    public LavaPlaceRunnable(LavYukseliyorGame game, int yMultiplier){
        this.plugin = (LavYukseliyorPlugin) game.getPlugin();
        this.game = game;
        this.yMultiplier = yMultiplier;
    }

    @Override
    public void run() {

        Location min = game.getMin();
        Location max = game.getMax();

        for (int y = 0; y < yMultiplier; y++) {
            for (int x = min.getBlockX()-1; x <= max.getBlockX(); x++) {
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),x,game.getCurrentLavaLevel(),min.getBlockZ()-1, Material.BARRIER));
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),x,game.getCurrentLavaLevel(),max.getBlockZ(), Material.BARRIER));
            }
            for (int z = min.getBlockZ()-1; z <= max.getBlockZ(); z++) {
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),min.getBlockX()-1,game.getCurrentLavaLevel(),z, Material.BARRIER));
                plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),max.getBlockX(),game.getCurrentLavaLevel(),z, Material.BARRIER));
            }
            for (int x = min.getBlockX(); x < max.getBlockX(); x++) {
                for (int z = min.getBlockZ(); z < max.getBlockZ(); z++) {
                    plugin.getWorkloadThread().add(new PlaceableBlock(min.getWorld().getUID(),x,game.getCurrentLavaLevel(),z, Material.LAVA));
                }
            }
            game.setCurrentLavaLevel(game.getCurrentLavaLevel()+1);
        }




        game.getGameData().getPlayerList().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .filter(player -> player.getLocation().getY() < game.getCurrentLavaLevel()-10)
                .forEach(player -> player.damage(100));

    }
}
