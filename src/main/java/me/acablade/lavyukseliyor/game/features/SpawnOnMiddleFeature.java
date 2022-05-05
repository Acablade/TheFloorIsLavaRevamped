package me.acablade.lavyukseliyor.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.bladeapi.features.impl.MapFeature;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

/**
 * @author Acablade/oz
 */
public class SpawnOnMiddleFeature extends AbstractFeature {

    public SpawnOnMiddleFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        Location spawnPoint = getAbstractPhase().getFeature(MapFeature.class).getSpawnPoints().get(0);

        getAbstractPhase().getGame().getGameData().getPlayerList().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player ->{
                    player.teleport(spawnPoint);
                });

    }

    @EventHandler
    public void onJoin(PlayerJoinGameEvent event){
        Location spawnPoint = getAbstractPhase().getFeature(MapFeature.class).getSpawnPoints().get(0);
        event.getPlayer().teleport(spawnPoint);
    }
}
