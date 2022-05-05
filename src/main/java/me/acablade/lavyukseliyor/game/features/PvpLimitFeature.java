package me.acablade.lavyukseliyor.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

/**
 * @author Acablade/oz
 */


public class PvpLimitFeature extends AbstractFeature {

    private boolean pvp = false;

    public PvpLimitFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @EventHandler
    public void onDamage(EntityDamageEvent event){
        event.setCancelled(!pvp && event.getEntity() instanceof Player);
    }

    public boolean isPvpEnabled() {
        return pvp;
    }

    public void setPvp(boolean pvp) {
        this.pvp = pvp;
    }
}
