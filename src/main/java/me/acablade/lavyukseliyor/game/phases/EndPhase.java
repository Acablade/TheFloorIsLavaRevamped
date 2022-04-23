package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.utils.LocationUtil;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.Duration;

/**
 * @author Acablade/oz
 */
public class EndPhase extends AbstractPhase {

    public EndPhase(AbstractGame game) {
        super(game);
    }

    @Override
    public Duration duration() {
        return Duration.ofSeconds(10);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        FileConfiguration config = getGame().getPlugin().getConfig();

        ConfigurationSection locationSection = config.getConfigurationSection("locations");

        LocationUtil.setLocation(((LavYukseliyorGame)getGame()).getMin().add(100,0,100),locationSection.getConfigurationSection("min"));
        LocationUtil.setLocation(((LavYukseliyorGame)getGame()).getMax().add(100,0,100),locationSection.getConfigurationSection("max"));

        getGame().addPhase(LobbyPhase.class);
        getGame().addPhase(GamePhase.class);
        getGame().addPhase(EndPhase.class);

        getGame().getPlugin().saveConfig();

    }

    @EventHandler
    public void onJoin(PlayerLoginEvent event){
        event.disallow(PlayerLoginEvent.Result.KICK_OTHER,"oyun yenileniyor");
    }
}
