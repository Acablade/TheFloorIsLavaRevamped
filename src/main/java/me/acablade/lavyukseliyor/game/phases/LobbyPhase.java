package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.AntiBlockBreakFeature;
import me.acablade.bladeapi.features.impl.AntiBlockPlaceFeature;
import me.acablade.bladeapi.features.impl.MapFeature;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.features.AddPlayerOnJoinFeature;
import me.acablade.lavyukseliyor.game.features.RemovePlayerOnLeaveFeature;
import me.acablade.lavyukseliyor.game.features.SpawnOnMiddleFeature;

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

        getFeature(MapFeature.class).getSpawnPoints().add(((LavYukseliyorGame)game).getCenter().getWorld().getSpawnLocation());
    }

    @Override
    public Duration duration() {
        return Duration.ofMinutes(1);
    }

}
