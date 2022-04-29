package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.utils.LocationUtil;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.io.File;
import java.nio.file.Files;
import java.time.Duration;

/**
 * @author Acablade/oz
 */
public class EndPhase extends AbstractPhase {

    private Duration duration = Duration.ofSeconds(10);

    public EndPhase(AbstractGame game) {
        super(game);
    }

    @Override
    public Duration duration() {
        return duration;
    }

    @Override
    public void onEnable() {
        super.onEnable();

        LavYukseliyorGame game = (LavYukseliyorGame) getGame();

        if(game.getResetCount()>0&&game.getResetCount()%5==0){
            resetWorld();
            game.getMax().set(-50,256,-50);
            game.getMin().set(-150,-64,-150);
        }

        FileConfiguration config = getGame().getPlugin().getConfig();

        ConfigurationSection locationSection = config.getConfigurationSection("locations");


        LocationUtil.setLocation(game.getMin().add(100,0,100),locationSection.getConfigurationSection("min"));
        LocationUtil.setLocation(game.getMax().add(100,0,100),locationSection.getConfigurationSection("max"));

        Location center = game.getCenter();

        center.getWorld().getWorldBorder().setCenter(center);

        game.setCurrentLavaLevel(-64);

        game.addPhase(LobbyPhase.class);
        game.addPhase(GamePhase.class);
        game.addPhase(EndPhase.class);

        game.getPlugin().saveConfig();

        duration = Duration.ofMillis(10);
        game.setResetCount(game.getResetCount()+1);

    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event){
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER,"oyun yenileniyor");
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
