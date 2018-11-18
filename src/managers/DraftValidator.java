package managers;

import exceptions.ImpossibleDraftException;
import exceptions.InvalidDraftChoiceException;
import models.Player;
import models.Team;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import static java.lang.Integer.max;
import static models.Team.*;

public class DraftValidator {
    private final List<Team> teams;
    private final Set<Player> playerPool;

    public DraftValidator(Set<Player> playerPool, List<Team> teams) {
        this.teams = teams;
        this.playerPool = playerPool;

    }

    // EFFECTS: Checks that there are enough forwards, defencemen and goalies to support the createDraftList
    public void checkDraftIsPossible() throws ImpossibleDraftException {
        int teamCount = teams.size();
        int minC = ALLOWED_CENTERS * teamCount;
        int minRW = ALLOWED_RWS * teamCount;
        int minLW = ALLOWED_LWS * teamCount;
        int minD = ALLOWED_DEFENCEMEN * teamCount;
        int minG = ALLOWED_GOALIES * teamCount;

        int availableC = countPlayersOfPosition(playerPool, Player.Position.C);
        int availableRW = countPlayersOfPosition(playerPool, Player.Position.RW);
        int availableLW = countPlayersOfPosition(playerPool, Player.Position.LW);
        int availableD = countPlayersOfPosition(playerPool, Player.Position.D);
        int availableG = countPlayersOfPosition(playerPool, Player.Position.G);

        int discrepancyC = max(minC - availableC, 0);
        int discrepancyRW = max(minRW - availableRW, 0);
        int discrepancyLW = max(minLW - availableLW, 0);
        int discrepancyD = max(minD - availableD, 0);
        int discrepancyG = max(minG - availableG, 0);

        if (discrepancyC > 0
                || discrepancyRW > 0
                || discrepancyLW > 0
                || discrepancyD > 0
                || discrepancyG > 0) {
            String msg = "The player pool is too restrictive to support the draft requirements." + "\n" +
                    "Please add the following players prior to drafting:";
            String msgC = "\n" + "[" + Integer.toString(discrepancyC) + "] x Centers";
            String msgRW = "\n" + "[" + Integer.toString(discrepancyRW) + "] x Right Wingers";
            String msgLW = "\n" + "[" + Integer.toString(discrepancyLW) + "] x Left Wingers";
            String msgD = "\n" + "[" + Integer.toString(discrepancyD) + "] x Defencemen";
            String msgG = "\n" + "[" + Integer.toString(discrepancyG) + "] x Goalies";
            throw new ImpossibleDraftException(msg + msgC + msgRW + msgLW + msgD + msgG);
        }
    }

    // EFFECTS: Counts the number of players in the player pool with the given position
    public int countPlayersOfPosition(Collection<Player> players, Player.Position position) {
        int i = 0;
        for (Player p : players) {
            if (p.getPosition().equals(position)) {
                i++;
            }
        }
        return i;
    }

    public String playersNeededByPosition(Team t) {
        List<Player> players = t.getPlayers();
        int centersNeeded = ALLOWED_CENTERS - countPlayersOfPosition(players, Player.Position.C);
        int rightWingsNeeded = ALLOWED_RWS - countPlayersOfPosition(players, Player.Position.RW);
        int leftWingsNeeded = ALLOWED_LWS - countPlayersOfPosition(players, Player.Position.LW);
        int defencemenNeeded = ALLOWED_DEFENCEMEN - countPlayersOfPosition(players, Player.Position.D);
        int goaliesNeeded = ALLOWED_GOALIES - countPlayersOfPosition(players, Player.Position.G);

        String msg = "Your team still needs the following postiions filled:";
        String msgC = "\n" + "[" + centersNeeded + "] x Centers";
        String msgRW = "\n" + "[" + rightWingsNeeded + "] x Right Wingers";
        String msgLW = "\n" + "[" + leftWingsNeeded + "] x Left Wingers";
        String msgD = "\n" + "[" + defencemenNeeded + "] x Defencemen";
        String msgG = "\n" + "[" + goaliesNeeded + "] x Goalies";

        return msg + msgC + msgRW + msgLW + msgD + msgG;
    }

    public void checkDraftChoiceIsValid(Team draftingTeam, Player player) throws InvalidDraftChoiceException {
        Player.Position position = player.getPosition();

        if ((position.equals(Player.Position.C) && maxCDrafted(draftingTeam))
            || (position.equals(Player.Position.RW) && maxRWDrafted(draftingTeam))
            || (position.equals(Player.Position.LW) && maxLWDrafted(draftingTeam))
            || (position.equals(Player.Position.D) && maxDDrafted(draftingTeam))
            || (position.equals(Player.Position.G) && maxGDrafted(draftingTeam))) {
            throw new InvalidDraftChoiceException();
        }
    }


    public boolean maxCDrafted(Team team) {
        List<Player> players = team.getPlayers();
        boolean limitReached = countPlayersOfPosition(players, Player.Position.C) >= ALLOWED_CENTERS;
        return limitReached;
    }

    public boolean maxRWDrafted(Team team) {
        List<Player> players = team.getPlayers();
        boolean limitReached = countPlayersOfPosition(players, Player.Position.RW) >= ALLOWED_RWS;
        return limitReached;
    }

    public boolean maxLWDrafted(Team team) {
        List<Player> players = team.getPlayers();
        boolean limitReached = countPlayersOfPosition(players, Player.Position.LW) >= ALLOWED_LWS;
        return limitReached;
    }

    public boolean maxDDrafted(Team team) {
        List<Player> players = team.getPlayers();
        boolean limitReached = countPlayersOfPosition(players, Player.Position.D) >= ALLOWED_DEFENCEMEN;
        return limitReached;
    }

    public boolean maxGDrafted(Team team) {
        List<Player> players = team.getPlayers();
        boolean limitReached = countPlayersOfPosition(players, Player.Position.G) >= ALLOWED_GOALIES;
        return limitReached;
    }


}
