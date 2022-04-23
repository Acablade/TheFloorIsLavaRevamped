package me.acablade.lavyukseliyor.game.objects;

import me.acablade.bladeapi.features.impl.TeamFeatureSupplier;
import me.acablade.bladeapi.objects.Team;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.ChatColor;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Acablade/oz
 */
public class LavTeamSupplier implements TeamFeatureSupplier {

    private final TextColor[] colors = Arrays.asList(TextColor.fromHexString("#c9f0ff"),TextColor.fromHexString("#efeff0")).toArray(new TextColor[0]);

    @Override
    public Set<Team> supplyTeams() {

        Set<Team> teamSet = new HashSet<>();

        String alphabet = "ABCÇDEFGĞHIİJKLMNOÖPQRSŞTUÜVWXYZ";

        for (int i = 0; i < alphabet.toCharArray().length; i++) {
            teamSet.add(new Team(alphabet.charAt(i)+"", ChatColor.GREEN,2));
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
