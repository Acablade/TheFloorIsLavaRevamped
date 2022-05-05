package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.features.ScoreboardFeature;
import me.acablade.lavyukseliyor.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;

/**
 * @author Acablade/oz
 */
public class ResetPhase extends AbstractPhase {

    private Duration duration = Duration.ofSeconds(10);

    public ResetPhase(AbstractGame game) {
        super(game);
        addFeature(new ScoreboardFeature(this));
    }

    @Override
    public Duration duration() {
        return duration;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        LavYukseliyorGame game = (LavYukseliyorGame) getGame();

        game.getAllPlayers().stream()
                .map(Bukkit::getPlayer)
                .forEach(player -> Bukkit.getScheduler().runTask(getGame().getPlugin(),() -> player.kickPlayer("Resetleniyor")));

        FileConfiguration config = getGame().getPlugin().getConfig();

        ConfigurationSection locationSection = config.getConfigurationSection("locations");

        Location min = game.getMin();
        Location max = game.getMax();

        int size = locationSection.getInt("size",100);


        if(game.getResetCount()>0&&game.getResetCount()%5==0){
            double minCenter = size*-1.5;
            double maxCenter = size*-.5;
            resetWorld();
            game.getMax().set(maxCenter,256,maxCenter);
            game.getMin().set(minCenter,-64,minCenter);
        }


        LocationUtil.setLocation(min.add(size,0,size),locationSection.getConfigurationSection("min"));
        LocationUtil.setLocation(max.add(size,0,size),locationSection.getConfigurationSection("max"));

        Location center = game.getCenter();

        center.getWorld().getWorldBorder().setCenter(center);
        center.getWorld().getWorldBorder().setSize(size);

        game.setCurrentLavaLevel(-64);

        game.addPhase(LobbyPhase.class);
        game.addPhase(GamePhase.class);
        game.addPhase(EndPhase.class);
        game.addPhase(ResetPhase.class);

        game.getPlugin().saveConfig();

        duration = Duration.ofMillis(10);
        game.setResetCount(game.getResetCount()+1);



    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event){
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER, "oyun yenileniyor");
    }

    private void resetWorld(){
        LavYukseliyorGame game = (LavYukseliyorGame) getGame();
        World oldWorld = game.getCenter().getWorld();
        World world = Bukkit.createWorld(new WorldCreator("lavyukseliyor_"+System.currentTimeMillis()));
        Bukkit.unloadWorld(oldWorld,false);

        deleteDir(oldWorld.getWorldFolder());
        game.getMax().setWorld(world);
        game.getMin().setWorld(world);

    }

    void deleteDir(File file) {
        File[] contents = file.listFiles();
        if (contents != null) {
            for (File f : contents) {
                if (! Files.isSymbolicLink(f.toPath())) {
                    deleteDir(f);
                }
            }
        }
        file.delete();
    }
}
