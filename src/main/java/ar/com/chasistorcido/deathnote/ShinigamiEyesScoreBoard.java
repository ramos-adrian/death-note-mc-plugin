package ar.com.chasistorcido.deathnote;

import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class ShinigamiEyesScoreBoard {

    private static Scoreboard scoreboard;
    private static final String TEAM_NAME = "ShinigamiEyes";
    private static final String NO_TEAM_NAME = "noTeam";

    private ShinigamiEyesScoreBoard() {
        // This class should not be instantiated
    }

    public static Scoreboard getScoreBoard() {
        if (scoreboard == null) {
            scoreboard = Bukkit.getScoreboardManager().getNewScoreboard();
            Team team = scoreboard.registerNewTeam(TEAM_NAME);
            team.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.FOR_OTHER_TEAMS);
            team.color(NamedTextColor.DARK_RED);
            Team noTeam = scoreboard.registerNewTeam(NO_TEAM_NAME);
            team.setAllowFriendlyFire(false);
            noTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            return scoreboard;
        }
        return scoreboard;
    }

    public static Team getTeam() {
        return getScoreBoard().getTeam(TEAM_NAME);
    }

    public static Team getNoTeam() {
        return getScoreBoard().getTeam(NO_TEAM_NAME);
    }
}
