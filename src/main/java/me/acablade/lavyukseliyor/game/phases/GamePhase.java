package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.*;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.features.GameWinCondition;
import me.acablade.lavyukseliyor.game.features.PvpLimitFeature;
import me.acablade.lavyukseliyor.game.features.SpawnOnMiddleFeature;
import me.acablade.lavyukseliyor.game.features.StartingItemsFeature;
import me.acablade.lavyukseliyor.game.objects.LavTeamSupplier;
import me.acablade.lavyukseliyor.game.objects.LavaPlaceRunnable;
import org.bukkit.Bukkit;

import java.time.Duration;

/**
 * @author Acablade/oz
 */
public class GamePhase extends AbstractPhase {


    private int currentTask;

    public GamePhase(AbstractGame game) {
        super(game);

        addFeature(new TeamFeature(this, new LavTeamSupplier()));
        addFeature(new MapFeature(this));
        addFeature(new SpectatorOnDeathFeature(this));
        addFeature(new SpectatorOnJoinFeature(this));
        addFeature(new SpawnOnMiddleFeature(this));
        addFeature(new StartingItemsFeature(this));
        addFeature(new PvpLimitFeature(this));
        addFeature(new AutoRespawnOnDeathFeature(this));
        addFeature(new GameWinCondition(this));

        getFeature(MapFeature.class).getSpawnPoints().add(((LavYukseliyorGame)game).getCenter().getWorld().getSpawnLocation());
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    private int stateChange = 0;

    @Override
    public void onTick() {
        super.onTick();

        if(!getFeature(PvpLimitFeature.class).isPvp()&&((LavYukseliyorGame)getGame()).getCurrentLavaLevel()>=80) getFeature(PvpLimitFeature.class).setPvp(true);

        if(stateChange<1&&timeLeft().compareTo(Duration.ofMinutes(25))<0){
            stateChange=1;
            currentTask = Bukkit.getScheduler()
                    .runTaskTimer(getGame().getPlugin(), new LavaPlaceRunnable((LavYukseliyorGame) getGame(),1),0,20*3L)
                    .getTaskId();
        }else if(stateChange<2&&((LavYukseliyorGame)getGame()).getCurrentLavaLevel()>120){
            stateChange=2;
            Bukkit.getScheduler().cancelTask(currentTask);
            currentTask = Bukkit.getScheduler()
                    .runTaskTimer(getGame().getPlugin(),new LavaPlaceRunnable((LavYukseliyorGame) getGame(),3),0,20*3L)
                    .getTaskId();
        }
    }

    @Override
    public Duration duration() {
        return Duration.ofMinutes(30);
    }

    public int getStateChange() {
        return stateChange;
    }

    public void setStateChange(int stateChange) {
        this.stateChange = stateChange;
    }
}
