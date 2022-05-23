package me.acablade.lavyukseliyor.commands;

import me.acablade.bladecommandframework.annotations.CommandInfo;
import me.acablade.bladecommandframework.classes.CommandActor;
import me.acablade.lavyukseliyor.LavYukseliyorPlugin;
import me.acablade.lavyukseliyor.game.features.ScoreboardFeature;
import me.acablade.lavyukseliyor.game.phases.GamePhase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashSet;

/**
 * @author Acablade/oz
 */

@CommandInfo(commandName = "lav")
public class LavCommands {

    private final LavYukseliyorPlugin plugin;

    public LavCommands(LavYukseliyorPlugin plugin){
        this.plugin = plugin;
    }

    @CommandInfo(commandName = "skip")
    public void skip(CommandActor actor){
        actor.reply("&askipped");
        if(plugin.getGame().getCurrentPhase() instanceof GamePhase gamePhase){
            if(gamePhase.getStateChange()==-2){
                plugin.getGame().endPhase();
                return;
            }
            gamePhase.setStateChange(Math.abs(gamePhase.getStateChange()-1));
            return;
        }
        plugin.getGame().getGameData().setWinner(new HashSet<>(Arrays.asList(Bukkit.getOnlinePlayers().toArray(new Player[0])[0].getUniqueId())));
        plugin.getGame().endPhase();
    }

    @CommandInfo(commandName = "freeze")
    public void freeze(CommandActor actor){
        plugin.getGame().setFrozen(!plugin.getGame().isFrozen());
        actor.reply(plugin.getGame().isFrozen() ? "&afrozen" : "&cunfrozen");
    }

    @CommandInfo(commandName = "level")
    public void level(CommandActor actor, int level){
        plugin.getGame().setCurrentLavaLevel(level);
        actor.reply("&aLevel set");
    }

    @CommandInfo(commandName = "reload")
    public void reload(CommandActor actor){
        ScoreboardFeature scoreboardFeature = plugin.getGame().getCurrentPhase().getFeature(ScoreboardFeature.class);
        if(scoreboardFeature==null) return;
        plugin.reloadConfig();
        scoreboardFeature.onDisable();
        scoreboardFeature.onEnable();
        actor.reply("&eReloaded successfully");
    }

}
