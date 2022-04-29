package me.acablade.lavyukseliyor.commands;

import me.acablade.lavyukseliyor.LavYukseliyorPlugin;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.phases.GamePhase;
import revxrsal.commands.annotation.*;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.help.CommandHelp;

import java.time.Duration;

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
            if(gamePhase.getStateChange()==-2){
                plugin.getGame().endPhase();
                return;
            }
            gamePhase.setStateChange(Math.abs(gamePhase.getStateChange()-1));
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
        plugin.getGame().setFrozen(!plugin.getGame().isFrozen());
        actor.reply(plugin.getGame().isFrozen() ? "&afrozen" : "&cunfrozen");
    }

    @Usage("/lav level <level>")
    @Subcommand("level")
    @CommandPermission("lav.level")
    @Description("Sets the lava level")
    public void level(BukkitCommandActor actor, int level){
        plugin.getGame().setCurrentLavaLevel(level);
        actor.reply("&aLevel set");
    }

    @Usage("/lav help")
    @Subcommand("help")
    @Description("Shows the help page")
    public void help(BukkitCommandActor actor, CommandHelp<String> helpEntries, @Default("1") int page) {
        actor.reply("§e--------- §rHelp: LavYukseliyorRevamp ("+page+"/"+ (int)Math.ceil(helpEntries.size() / 7.0)+") §e---------");
        for (String entry : helpEntries.paginate(page, 7)) // 7 entries per page
            actor.reply(entry);
    }

}
