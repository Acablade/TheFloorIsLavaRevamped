package me.acablade.lavyukseliyor.game.features;

import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.events.GameFinishEvent;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.bladeapi.features.impl.TeamFeature;
import me.acablade.bladeapi.objects.Team;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.PlayerDeathEvent;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Acablade/oz
 */
public class GameWinCondition extends AbstractFeature {

    public GameWinCondition(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event){

        if(getAbstractPhase().getGame().getGameData().getPlayerList().size()>3) return;

        Set<Team> teamSet = new HashSet<>();

        getAbstractPhase().getGame().getGameData().getPlayerList().stream()
                .map(Bukkit::getPlayer)
                .filter(Objects::nonNull)
                .forEach(player ->{
                    teamSet.add(getTeam(player));
                });

        if(teamSet.size()==1){
            List<UUID> uuids = Arrays.asList(teamSet.toArray(new Team[0])[0].getPlayerList().toArray(new UUID[0]));
            getAbstractPhase().getGame().getGameData().setWinner(new HashSet<>(uuids));
            Bukkit.getPluginManager().callEvent(new GameFinishEvent(getAbstractPhase().getGame()));
            getAbstractPhase().getGame().endPhase();
        }

    }

    private Team getTeam(Player player){
        TeamFeature teamFeature = getAbstractPhase().getFeature(TeamFeature.class);
        return teamFeature
                .getTeamSet()
                .stream()
                .filter(team -> team.getPlayerList().contains(player.getUniqueId()))
                .findFirst()
                .get();
    }

}
