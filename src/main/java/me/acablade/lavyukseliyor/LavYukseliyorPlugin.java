package me.acablade.lavyukseliyor;

import me.acablade.lavyukseliyor.commands.LavCommands;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import me.acablade.lavyukseliyor.game.objects.LavaPlaceRunnable;
import me.acablade.lavyukseliyor.game.phases.EndPhase;
import me.acablade.lavyukseliyor.game.phases.ResetPhase;
import me.acablade.lavyukseliyor.game.phases.GamePhase;
import me.acablade.lavyukseliyor.game.phases.LobbyPhase;
import me.acablade.lavyukseliyor.utils.WorkloadThread;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import revxrsal.commands.bukkit.BukkitCommandHandler;

import java.util.Locale;

public final class LavYukseliyorPlugin extends JavaPlugin {

    private WorkloadThread workloadThread;
    private LavYukseliyorGame game;

    @Override
    public void onEnable() {
        // Plugin startup logic

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

        workloadThread = new WorkloadThread();

        game = new LavYukseliyorGame("lav_yukseliyor",this);
        game.addPhase(LobbyPhase.class);
        game.addPhase(GamePhase.class);
        game.addPhase(EndPhase.class);
        game.addPhase(ResetPhase.class);

        BukkitCommandHandler commandHandler = BukkitCommandHandler.create(this);

        commandHandler.getTranslator().addResourceBundle("lavyukseliyor", Locale.forLanguageTag("tr"));
        commandHandler.setLocale(Locale.forLanguageTag("tr"));
        commandHandler.setHelpWriter((command, actor) -> String.format("§6%s: §r%s", command.getUsage(), command.getDescription()));


        commandHandler.registerDependency(LavYukseliyorPlugin.class, this);
        commandHandler.register(new LavCommands());


        Bukkit.getScheduler().runTaskTimer(this, workloadThread,0,1);

        game.enable(2,1);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        Bukkit.getScheduler().cancelTask(LavaPlaceRunnable.getTaskId());

    }

    public WorkloadThread getWorkloadThread() {
        return workloadThread;
    }

    public LavYukseliyorGame getGame() {
        return game;
    }
}
