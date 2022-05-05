package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.features.impl.MapFeature;
import me.acablade.bladeapi.features.impl.NoBlockBreakFeature;
import me.acablade.bladeapi.features.impl.NoBlockPlaceFeature;
import me.acablade.bladeapi.features.impl.NoHitFeature;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.features.AddPlayerOnJoinFeature;
import me.acablade.lavyukseliyor.game.features.RemovePlayerOnLeaveFeature;
import me.acablade.lavyukseliyor.game.features.ScoreboardFeature;
import me.acablade.lavyukseliyor.game.features.SpawnOnMiddleFeature;
import me.acablade.lavyukseliyor.game.objects.LavaPlaceRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Difficulty;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.Duration;

/**
 * @author Acablade/oz
 */


public class LobbyPhase extends AbstractPhase implements Listener {

    private Duration duration = Duration.ofMinutes(5);

    public LobbyPhase(AbstractGame game) {
        super(game);
        addFeature(new NoBlockBreakFeature(this));
        addFeature(new NoBlockPlaceFeature(this));
        addFeature(new AddPlayerOnJoinFeature(this));
        addFeature(new RemovePlayerOnLeaveFeature(this));
        addFeature(new MapFeature(this));
        addFeature(new SpawnOnMiddleFeature(this));
        addFeature(new ScoreboardFeature(this));
        addFeature(new NoHitFeature(this));

        Bukkit.getScheduler().cancelTask(LavaPlaceRunnable.getTaskId());

        Bukkit.getPluginManager().registerEvents(this, game.getPlugin());

        Location center = ((LavYukseliyorGame)game).getCenter();

        getFeature(MapFeature.class).getSpawnPoints().add(center.getWorld().getHighestBlockAt(center).getLocation().add(0,1,0));
    }

    @Override
    public void onDisable() {
        super.onDisable();
        HandlerList.unregisterAll(this);
    }

    @Override
    public Duration duration() {
        return duration;
    }

    @EventHandler
    public void onJoin(PlayerJoinGameEvent event){
        if(Math.floor(Bukkit.getServer().getMaxPlayers()*0.8)<=getGame().getGameData().getPlayerList().size()){
            duration = Duration.ofSeconds(15);
        }

    }

}
