package managers;

import exceptions.ImpossibleDraftException;
import exceptions.InvalidDraftChoiceException;
import models.Player;
import models.Team;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import static managers.DraftManager.DraftType.*;
import static models.Team.MAX_ROSTER_SIZE;
import static ui.formatters.TeamFormatter.convertPlayerSetToList;

public class DraftManager implements Serializable {
    private final List<Team> teams;
    private final Set<Player> playerPool;
    private DraftType draftType;
    private DraftValidator draftValidator;

    public enum DraftType {Regular, Snake, AutoDraft}

    // EFFECTS: Constructs a DraftManager with the given teams, available players
    public DraftManager(List<Team> teams, Set<Player> playerPool, DraftType draftType) {
        this.teams = teams;
        this.playerPool = playerPool;
        this.draftType = draftType;
        this.draftValidator = new DraftValidator(playerPool, teams);
    }

    public DraftValidator getDraftValidator() {
        return draftValidator;
    }

    public DraftType getDraftType() {
        return draftType;
    }

    public void setDraftType(DraftType draftType) {
        this.draftType = draftType;
    }

    // MODIFIES: this
    // EFFECTS: Randomizes the draft order and notifies fantasy players
    //          Drafts players based on the method that was chosen
    public List<Team> createDraftList() throws ImpossibleDraftException {
        draftValidator.checkDraftIsPossible();

        randomizeDraftOrder();

        int selections = calculateTotalSelections();

        List<Team> draftList = new ArrayList<>();

        if (draftType.equals(Regular) || draftType.equals(AutoDraft)) {
            draftList = regularDraft(selections);
        } else if (draftType.equals(Snake)) {
            draftList = snakeDraft(selections);
        }
        return draftList;
    }


    public int calculateTotalSelections() {
        int teamCount = teams.size();
        int maxSelectablePlayers = MAX_ROSTER_SIZE * teamCount;
        int maxAvailablePlayers = playerPool.size();
        int possibleSelections = Integer.min(maxSelectablePlayers, maxAvailablePlayers);
        int totalSelections = possibleSelections - (possibleSelections % teamCount);
        return totalSelections;
    }

    // EFFECTS: Returns the ordered list of teams for a regular draft
    // Regular Draft Rules:
    //          The first team picks first, followed by team 2, 3, ... team n
    //          Once every team has made a selection, the team that went first picks again
    //          The process repeats itself until all selections have been made
    public List<Team> regularDraft(int selections) {
        List<Team> draftList = new ArrayList<>();

        for (int i = 0; i < selections; i++) {
            Team draftingTeam = teams.get(i % teams.size());
            draftList.add(draftingTeam);
        }
        return draftList;
    }


    // EFFECTS: Returns the ordered list of teams for a snake draft
    // Snake Draft Rules:
    //          The first team picks first, followed by team 2, 3, ... team n
    //          Then the order reverses: team n picks again, followed by n-1, n-2, ... 3, 2, 1
    //          Finally, team 1 picks again, and the process repeats until all selections have been made
    public List<Team> snakeDraft(int selections) {
        List<Team> draftList = new ArrayList<>();
        for (int i = 0; i < selections; i++) {

            Team draftingTeam;
            int size = teams.size();
            int ascendingPick = i % size;
            int descendingPick = Math.floorMod(((2 * size) - i - 1), size);

            boolean ascending = (i % (2 * size) < size);

            if (ascending) {
                draftingTeam = teams.get(ascendingPick);
            } else {
                draftingTeam = teams.get(descendingPick);
            }

            draftList.add(draftingTeam);
        }
        return draftList;
    }



    public void autoDraft(List<Team> draftList) {
        ArrayList<Player> players = convertPlayerSetToList(playerPool);
        randomizePlayerOrder(players);

        for (Team t : draftList) {
            for (Player p : players) {
                try {
                    draftValidator.checkDraftChoiceIsValid(t, p);
                    t.addPlayer(p);
                    players.remove(p);
                    playerPool.remove(p);
                    System.out.println("Success! " + p.getPlayerName() + " was added to " + t.getTeamName() + ".");
                    break;
                } catch (InvalidDraftChoiceException e) {

                }
            }
        }
    }


    public void randomizeDraftOrder() {
        Collections.shuffle(teams);
    }


    public void randomizePlayerOrder(List<Player> players) {
        Collections.shuffle(players);
    }

}

