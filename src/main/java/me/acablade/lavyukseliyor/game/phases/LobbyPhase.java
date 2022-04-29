package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.AntiBlockBreakFeature;
import me.acablade.bladeapi.features.impl.AntiBlockPlaceFeature;
import me.acablade.bladeapi.features.impl.MapFeature;
import me.acablade.bladeapi.features.impl.NoHitFeature;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.features.AddPlayerOnJoinFeature;
import me.acablade.lavyukseliyor.game.features.RemovePlayerOnLeaveFeature;
import me.acablade.lavyukseliyor.game.features.ScoreboardFeature;
import me.acablade.lavyukseliyor.game.features.SpawnOnMiddleFeature;
import me.acablade.lavyukseliyor.game.objects.LavaPlaceRunnable;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.time.Duration;

/**
 * @author Acablade/oz
 */


public class LobbyPhase extends AbstractPhase {
    public LobbyPhase(AbstractGame game) {
        super(game);
        addFeature(new AntiBlockBreakFeature(this));
        addFeature(new AntiBlockPlaceFeature(this));
        addFeature(new AddPlayerOnJoinFeature(this));
        addFeature(new RemovePlayerOnLeaveFeature(this));
        addFeature(new MapFeature(this));
        addFeature(new SpawnOnMiddleFeature(this));
        addFeature(new ScoreboardFeature(this));
        addFeature(new NoHitFeature(this));

        Bukkit.getScheduler().cancelTask(LavaPlaceRunnable.taskId);

        Location center = ((LavYukseliyorGame)game).getCenter();

        getFeature(MapFeature.class).getSpawnPoints().add(center.getWorld().getHighestBlockAt(center).getLocation().add(0,1,0));
    }

    @Override
    public Duration duration() {
        return Duration.ofMinutes(5);
    }

}
