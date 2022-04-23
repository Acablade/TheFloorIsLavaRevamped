package me.acablade.lavyukseliyor.commands;

import me.acablade.lavyukseliyor.LavYukseliyorPlugin;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.phases.GamePhase;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;

/**
 * @author Acablade/oz
 */

@Command("lav")
public class LavCommands {

    @Dependency
    private LavYukseliyorPlugin plugin;

    @Usage("/lav skip")
    @Subcommand("skip")
    @CommandPermission("lav.skip")
    @Description("Skips the current phase")
    public void skip(BukkitCommandActor actor){
        actor.reply("&askipped");
        if(plugin.getGame().getCurrentPhase() instanceof GamePhase gamePhase){
            gamePhase.setStateChange(gamePhase.getStateChange()+1);
            if(gamePhase.getStateChange()>2){
                plugin.getGame().endPhase();
                return;
            }
            actor.reply(gamePhase.getStateChange()+"");
            return;
        }
        plugin.getGame().endPhase();
    }

    @Usage("/lav freeze")
    @Subcommand("freeze")
    @CommandPermission("lav.freeze")
    @Description("Freezes the current phase")
    public void freeze(BukkitCommandActor actor){
        plugin.getGame().setFrozen(true);
        actor.reply("&afrozen");
    }

    @Usage("/lav level <seviye>")
    @Subcommand("level")
    @CommandPermission("lav.level")
    @Description("Sets the lava level")
    public void level(BukkitCommandActor actor, int level){
        plugin.getGame().setCurrentLavaLevel(level);
        actor.reply("&aLevel set");
    }


}
