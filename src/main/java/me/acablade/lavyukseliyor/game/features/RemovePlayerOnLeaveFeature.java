package me.acablade.lavyukseliyor.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.PlayerLeaveGameEvent;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * @author Acablade/oz
 */
public class RemovePlayerOnLeaveFeature extends AbstractFeature {
    public RemovePlayerOnLeaveFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        getAbstractPhase().getGame().getGameData().getPlayerList().remove(event.getPlayer().getUniqueId());
        Bukkit.getPluginManager().callEvent(new PlayerLeaveGameEvent(event.getPlayer(), getAbstractPhase().getGame()));
    }

}
