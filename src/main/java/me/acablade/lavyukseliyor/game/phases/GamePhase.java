package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.*;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.features.*;
import me.acablade.lavyukseliyor.game.objects.LavTeamSupplier;
import me.acablade.lavyukseliyor.game.objects.LavaPlaceRunnable;
import me.acablade.lavyukseliyor.utils.Message;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ScopedComponent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.time.Duration;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author Acablade/oz
 */
public class GamePhase extends AbstractPhase {
    private Duration duration = Duration.ofMinutes(30);

    public GamePhase(AbstractGame game) {
        super(game);

        addFeature(new TeamFeature(this, new LavTeamSupplier(), false));
        addFeature(new MapFeature(this));
        addFeature(new SpectatorOnDeathFeature(this));
        addFeature(new SpectatorOnJoinFeature(this));
        addFeature(new SpawnOnMiddleFeature(this));
        addFeature(new StartingItemsFeature(this));
        addFeature(new PvpLimitFeature(this));
        addFeature(new AutoRespawnOnDeathFeature(this));
        addFeature(new GameWinCondition(this));
        addFeature(new ScoreboardFeature(this));

        getFeature(MapFeature.class).getSpawnPoints().add(((LavYukseliyorGame)game).getCenter().getWorld().getSpawnLocation());
    }

    private int stateChange = 0;

    @Override
    public void onTick() {
        super.onTick();

        if(timeLeft().compareTo(Duration.ofMinutes(25))<0&&stateChange==0) stateChange=1;
        if(((LavYukseliyorGame)getGame()).getCurrentLavaLevel()>120&&stateChange==-1) stateChange = 2;

        if(!getFeature(PvpLimitFeature.class).isPvpEnabled()&&((LavYukseliyorGame)getGame()).getCurrentLavaLevel()>=80) getFeature(PvpLimitFeature.class).setPvp(true);

        if(stateChange==1){
            LavaPlaceRunnable.setTaskId(Bukkit.getScheduler()
                    .runTaskTimer(getGame().getPlugin(), new LavaPlaceRunnable((LavYukseliyorGame) getGame(),1),0,20L*getGame().getPlugin().getConfig().getInt("lava-place-timer"))
                    .getTaskId());

            ((LavYukseliyorGame) getGame()).getAllPlayers().stream()
                    .map(Bukkit::getPlayer)
                    .forEach(player -> Message.LAVA_STARTED_RISING.send(player,null));
            stateChange=-1;
        }else if(stateChange==2){
            Bukkit.getScheduler().cancelTask(LavaPlaceRunnable.getTaskId());
            LavaPlaceRunnable.setTaskId(Bukkit.getScheduler()
                    .runTaskTimer(getGame().getPlugin(),new LavaPlaceRunnable((LavYukseliyorGame) getGame(),
                            getGame().getPlugin().getConfig().getInt("lava-fast-paced-block-amount")),0,20L*getGame().getPlugin().getConfig().getInt("lava-place-timer"))
                    .getTaskId());
            stateChange=-2;
        }



    }

    @Override
    public Duration duration() {
        return duration;
    }

    public int getStateChange() {
        return stateChange;
    }

    public void setStateChange(int stateChange) {
        this.stateChange = stateChange;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }
}
