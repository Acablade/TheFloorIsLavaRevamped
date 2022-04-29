package me.acablade.lavyukseliyor.game.features;

import fr.mrmicky.fastboard.FastBoard;
import me.acablade.bladeapi.AbstractPhase;
import me.acablade.bladeapi.features.AbstractFeature;
import me.acablade.lavyukseliyor.game.phases.GamePhase;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.ChatColor;
import org.bukkit.Statistic;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author Acablade/oz
 */
public class ScoreboardFeature extends AbstractFeature {

    private static final Map<UUID, FastBoard> boards = new HashMap<>();

    public ScoreboardFeature(AbstractPhase abstractPhase) {
        super(abstractPhase);
    }

    @Override
    public void onTick() {
        super.onTick();
        for (FastBoard board : boards.values()) {
            updateBoard(board);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if(boards.containsKey(uuid)) return;

        FastBoard fastBoard = new FastBoard(player);
        fastBoard.updateTitle("§cLav Yükseliyor");

        boards.put(uuid,fastBoard);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        FastBoard board = boards.remove(player.getUniqueId());

        if (board != null) {
            board.delete();
        }
    }

    private void updateBoard(FastBoard board) {

        Player player = board.getPlayer();

        List<String> lines;
        String title;

        if(getAbstractPhase() instanceof GamePhase){
            lines = processLines("game", player);
            title = processTitle("game",player);
        }else{
            lines = processLines("lobby",player);
            title = processTitle("lobby",player);
        }

        board.updateTitle(title);
        board.updateLines(lines);

    }

    private List<String> processLines(String section, Player player){
        ConfigurationSection scoreboardSection = getAbstractPhase().getGame().getPlugin().getConfig().getConfigurationSection("scoreboard");
        ConfigurationSection phaseSection = scoreboardSection.getConfigurationSection(section);
        List<String> lines = phaseSection.getStringList("lines").stream().map(line -> processLine(line,player)).toList();
        return lines;
    }

    private String processTitle(String section, Player player){
        ConfigurationSection scoreboardSection = getAbstractPhase().getGame().getPlugin().getConfig().getConfigurationSection("scoreboard");
        ConfigurationSection phaseSection = scoreboardSection.getConfigurationSection(section);
        return processLine(phaseSection.getString("title"),player);
    }

    private String processLine(String line, Player player){
        return ChatColor.translateAlternateColorCodes('&', line
                        .replace("%player%",player.getName())
                        .replace("%time_left%",DurationFormatUtils.formatDuration(getAbstractPhase().timeLeft().toMillis(), "mm:ss", true))
                        .replace("%player_kills%",player.getStatistic(Statistic.PLAYER_KILLS)+""));
    }


}
