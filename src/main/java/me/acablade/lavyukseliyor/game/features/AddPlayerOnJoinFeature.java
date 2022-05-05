package me.acablade.lavyukseliyor.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerJoinGameEvent;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

/**
 * @author Acablade/oz
 */
public class AddPlayerOnJoinFeature extends AbstractFeature {

    public AddPlayerOnJoinFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        getAbstractPhase().getGame().getGameData().getPlayerList().add(uuid);
        Bukkit.getPluginManager().callEvent(new PlayerJoinGameEvent(player, getAbstractPhase().getGame()));


    }


}
