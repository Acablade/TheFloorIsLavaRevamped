package me.acablade.lavyukseliyor.game.phases;

import me.acablade.bladeapi.AbstractGame;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.impl.TeamFeature;
import me.acablade.bladeapi.objects.Team;
import me.acablade.lavyukseliyor.game.LavYukseliyorGame;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ScopedComponent;
import net.kyori.adventure.text.TextComponent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.*;

import java.time.Duration;
import java.util.stream.Collectors;

/**
 * @author Acablade/oz
 */
public class EndPhase extends AbstractPhase {


    public EndPhase(AbstractGame game) {
        super(game);
    }

    @Override
    public Duration duration() {
        return Duration.ofSeconds(10);
    }

    @Override
    public void onEnable() {
        super.onEnable();

        LavYukseliyorGame game = (LavYukseliyorGame) getGame();

        String winner = game.getGameData().getWinner().stream().map(uuid -> Bukkit.getPlayer(uuid).getName()).collect(Collectors.joining(","));

        Bukkit.getOnlinePlayers().forEach(player -> player.sendTitle("§6KAZANAN", winner,5,10,5));

    }

}
