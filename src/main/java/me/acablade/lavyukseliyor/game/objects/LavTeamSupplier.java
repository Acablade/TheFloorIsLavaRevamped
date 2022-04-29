package me.acablade.lavyukseliyor.game.objects;

import me.acablade.bladeapi.features.impl.TeamFeatureSupplier;
import me.acablade.bladeapi.objects.Team;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import net.md_5.bungee.chat.ComponentSerializer;
import org.bukkit.Bukkit;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Acablade/oz
 */
public class LavTeamSupplier implements TeamFeatureSupplier {

    private final TextColor[] colors = Arrays.asList(TextColor.fromHexString("#b74f6f"),TextColor.fromHexString("#35281d")).toArray(new TextColor[0]);

    @Override
    public Set<Team> supplyTeams() {

        Set<Team> teamSet = new HashSet<>();

        String alphabet = "ABCÇDEFGĞHIİJKLMNOÖPQRSŞTUÜVWXYZ";

        for (int i = 0; i < alphabet.toCharArray().length; i++) {
            float phase = i*1.0f/(alphabet.toCharArray().length-1);
            teamSet.add(new Team(alphabet.charAt(i)+"", color(phase),2));
        }
        return teamSet;
    }

    private TextColor color(float phase) {
        final float steps = 1f / (this.colors.length - 1);
        for (int colorIndex = 1; colorIndex < this.colors.length; colorIndex++) {
            final float val = colorIndex * steps;
            if (val >= phase) {
                final float factor = 1 + (phase - val) * (this.colors.length - 1);

                return TextColor.lerp(factor, this.colors[colorIndex - 1], this.colors[colorIndex]);
            }
        }
        return this.colors[0];
    }

}
