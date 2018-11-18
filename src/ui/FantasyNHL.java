package ui;

import exceptions.ImpossibleDraftException;
import exceptions.InvalidDraftChoiceException;
import exceptions.InvalidFantasyWeekException;
import exceptions.InvalidTeamException;
import managers.DraftManager;
import managers.FantasyManager;
import models.League;
import models.Player;
import models.Team;

import java.io.IOException;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static managers.FantasyManager.FANTASY_WEEKS;
import static models.League.*;
import static ui.formatters.LeagueFormatter.getTeamNames;
import static ui.formatters.TeamFormatter.getPlayerNamesAndPositions;

//import managers.LeagueManager;

public class FantasyNHL implements Serializable {
    private static final String COMMAND_CREATE_TEAM = "team";
    private static final String COMMAND_VIEW_TEAM_NAMES = "view";
    private static final String COMMAND_DRAFT = "draft";
    private static final String COMMAND_ADVANCE_WEEK = "advance";
    //    private static final String COMMAND_ADD_PLAYER = "player";
//    private static final String COMMAND_ADD_STAT = "goal";
    // TODO 4: Create leaderboard functionality
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_GO_BACK = "back";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SAVE_AND_QUIT = "save";
    public static final String OPTION_GO_BACK = " or type \"" + COMMAND_GO_BACK + "\" to return to the previous menu.";

    Scanner scanner = new Scanner(System.in);
    //    LeagueManager leagueManager;
    League league;
    FantasyManager fantasyManager;

    public FantasyNHL() {

        // Modeled after B04-LoggingCalculator starter file
        String operation;
        System.out.println("Welcome to DreamTeam NHL!");
        league = loadLeague();
        fantasyManager = new FantasyManager(league);
//        league.load();
//        league league = loadLeague();

        while (true) {
            printMenu();
            operation = scanner.nextLine();
            System.out.println("You selected " + operation + ".");

            // User creates a team
            if (operation.equals(COMMAND_CREATE_TEAM) && teamCanBeAdded()) {
                createUserGeneratedTeam();
                // User views the teams in the league
            } else if (operation.equals(COMMAND_VIEW_TEAM_NAMES) && atLeastOneTeam()) {
                printTeamsAndPlayers();
            } else if (operation.equals(COMMAND_DRAFT) && leagueCanDraft()) {
                selectDraftTypeAndDraftTeams();
            } else if (operation.equals(COMMAND_ADVANCE_WEEK) && leagueIsDrafted()) {
                updateLeagueWithNewStats();
//            } else if (operation.equals(COMMAND_ADD_PLAYER) && atLeastOneTeam()) {
//                addSelectedPlayerToSelectedTeam();
//            } else if (operation.equals(COMMAND_ADD_STAT)) {
//                createUserGeneratedStat();
            } else if (operation.equals(COMMAND_QUIT)) {
                System.out.println("Thanks for playing! See you next time.");
                break;
            } else if (operation.equals(COMMAND_SAVE_AND_QUIT)) {
                System.out.println("Saving data...");
                league.save();
                System.out.println("Your data has been saved! See you next time.");
                break;
//            } else if (operation.equals(COMMAND_VIEW_OVERALL_LEADER)) {
//                LocalDate startDate = LocalDate.of(2017, 11, 10);
//                LocalDate endDate = startDate.plusDays(6);
//
//                List<Integer> gameIDs = getWeeklyGameIDs(startDate, endDate);
//                Set<team> teams = league.getTeams();
//                for (team t : teams) {
//                    List<player> players = t.getPlayers();
//                    for (player p : players) {
//                        StatManager stats = p.getStatManager();
//                        try {
//                            stats.addGameStats(gameIDs);
//                        } catch (IOException e) {
//                            System.out.println("Issue updating game stats.");
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println(t.getTeamName() + ", Fantasy Points: " + t.getOverallFantasyPoints());
//                }
//            }
            }
            // User enters an invalid command
            else {
                System.out.println("Sorry, command not recognized!");
            }
        }
    }

    public static void main(String[] args) {
        new FantasyNHL();
    }

    public void updateLeagueWithNewStats() {
        if (fantasyManager.getCurrentFantasyWeek() < FANTASY_WEEKS) {
            try {
                fantasyManager.advanceFantasyWeekByOne();

                int week = fantasyManager.getCurrentFantasyWeek();
                LocalDate startDate = fantasyManager.getWeekCutoffs().get(week-1).plusDays(1);
                LocalDate endDate = fantasyManager.getWeekCutoffs().get(week);

                List<String> gameIDs = fantasyManager.getGameIDs(startDate, endDate);
                System.out.println("Updating this week's stats. This may take some time...");
                fantasyManager.addGameStats(gameIDs);

                //TODO: add current week fantasy points and overall fantasy points for each team once above method is done

            } catch (InvalidFantasyWeekException e) {
                e.printStackTrace();
            } catch (IOException ioe) {
                System.out.println("Unable to upload this week's stats.");
                ioe.printStackTrace();
            }
        }
    }

    public void draftTeams() {
        try {
            List<Team> draftList = new ArrayList<>();
            draftList = fantasyManager.selectDraftOrder();
            announceDraftOrder(draftList);

            System.out.println("The draft is beginning!" + "\n");

            if (fantasyManager.getDraftManager().getDraftType().equals(DraftManager.DraftType.AutoDraft)) {
                fantasyManager.getDraftManager().autoDraft(draftList);
            } else {

                int i = 1;
                for (Team t : draftList) {
                    System.out.println("\n" + " Selection " + i + ": " + t.getTeamName() + "\n");
                    System.out.println(fantasyManager.getDraftManager().getDraftValidator().playersNeededByPosition(t));
                    selectPlayer(t);
                    i++;
                }
            }
            fantasyManager.setDrafted();

            System.out.println("\n" + "That concludes the Fantasy Draft! Good luck to all players!" + "\n");

        } catch (ImpossibleDraftException e) {
            System.out.println(e.getMsg());
        }
    }

    public void selectDraftTypeAndDraftTeams() {
        while (true) {
            System.out.println("Enter the type of draft you want to have" + OPTION_GO_BACK + "\n" +
                    "[Regular]" + "\n" +
                    "[Snake]" + "\n" +
                    "[AutoDraft]");
            String draftType = scanner.nextLine();
            if (draftType.equals(COMMAND_GO_BACK)) {
                break;
            } else if (draftType.equals(DraftManager.DraftType.Regular.toString()) ||
                    draftType.equals(DraftManager.DraftType.Snake.toString()) ||
                    draftType.equals(DraftManager.DraftType.AutoDraft.toString())) {
                DraftManager.DraftType dt = DraftManager.DraftType.valueOf(draftType);
                fantasyManager.getDraftManager().setDraftType(dt);
                draftTeams();
                break;
            } else {
                printIsInvalidOption(draftType);
            }
        }
    }

//    public void autoDraftTeams() {
//        System.out.println("Completing the autodraft...");
//
//    }

    public void announceDraftOrder(List<Team> draftList) {
        int size = league.getTeams().size();
        System.out.println("The random draft order is...");
        for (int i = 1; i <= size; i++) {
            Team t = draftList.get(i - 1);
            System.out.println(i + ": " + t.getTeamName());
        }
    }

//    private void createUserGeneratedStat() {
//        List<Player> allPlayers = new ArrayList<>();
//        for (Team t : league.getTeams()) {
//            allPlayers.addAll(t.getPlayers());
//        }
//
//        while (true) {
//            System.out.println("Enter the number of the player you want to award a goal to," + OPTION_GO_BACK);
//
//            int i = 1;
//            for (Player p : allPlayers) {
//                String title = "[" + i + "] " + p.getPlayerName() + " (" + p.getPosition() + ")";
//                System.out.println(title);
//                i++;
//            }
//
//            String playerNumber = scanner.nextLine();
//
//            if (playerNumber.equals(COMMAND_GO_BACK)) {
//                break;
//            } else {
//                try {
//                    int playerIndex = isInteger(playerNumber) - 1;
//
//                    if (playerIndex >= 0 && playerIndex < allPlayers.size()) {
//                        LocalDate date = LocalDate.now();
//                        Stat stat = new Stat(date, 2017020001, allPlayers.get(playerIndex), goals, 1);
//                        break;
//                    } else {
//                        printIsInvalidOption(playerNumber);
//                    }
//                } catch (NumberFormatException numberFormatException) {
//                    printIsInvalidOption(playerNumber);
//                }
//            }
//        }
//    }


//    private void addSelectedPlayerToSelectedTeam() {
//        while (true) {
//            ArrayList<Team> teams = askUserToSelectTeamByIndex();
//            String teamNumber = scanner.nextLine();
//            Team team = null;
//
//            if (teamNumber.equals(COMMAND_GO_BACK)) {
//                break;
//            } else {
//                try {
//                    int teamIndex = isInteger(teamNumber) - 1;
//
//                    if (teamIndex >= 0 && teamIndex < teams.size()) {
//                        team = teams.get(teamIndex);
//                        String teamName = team.getTeamName();
//                        selectPlayer(team, teamName);
//                        break;
//                    } else {
//                        printIsInvalidOption(teamNumber);
//                    }
//
//                } catch (NumberFormatException numberFormatException) {
//                    printIsInvalidOption(teamNumber);
//                }
//            }
//        }
//    }

    private void selectPlayer(Team team) {
        String teamName = team.getTeamName();
        while (true) {
            ArrayList<Player> players = askUserToSelectPlayerByIndex(teamName);
            String playerNumber = scanner.nextLine();

            try {
                int playerIndex = isInteger(playerNumber) - 1;

                if (playerIndex >= 0 && playerIndex < players.size()) {
                    Player p = players.get(playerIndex);
                    try {
                        fantasyManager.getDraftManager().getDraftValidator().checkDraftChoiceIsValid(team, p);
                        addPlayerToFantasyTeam(team, teamName, players, playerIndex);
                        break;
                    } catch (InvalidDraftChoiceException e) {
                        System.out.println("You cannot add another player of that position.");
                        System.out.println(fantasyManager.getDraftManager().getDraftValidator().playersNeededByPosition(team));
                    }
                } else {
                    printIsInvalidOption(playerNumber);
                }
            } catch (NumberFormatException numberFormatException) {
                printIsInvalidOption(playerNumber);
            }
        }
    }

    private void printIsInvalidOption(String invalidInput) {
        System.out.println(invalidInput + " is not a valid option");
    }

    private void addPlayerToFantasyTeam(Team team, String teamName, ArrayList<Player> players, int playerIndex) {
        Player player = players.get(playerIndex);
        String playerName = player.getPlayerName();
        team.addPlayer(player);
        league.getAvailablePlayers().remove(player);
        System.out.println("Success! " + playerName + " was added to " + teamName + ".");
    }

    private void createUserGeneratedTeam() {
        while (true) {
            printActiveTeamNames();
            System.out.println("Enter a unique team name" + OPTION_GO_BACK);
            String teamName = scanner.nextLine();

            if (teamName.equals(COMMAND_GO_BACK)) {
                break;
            } else {
                try {
                    Team team = new Team(teamName);
                    league.addTeam(team);
                    System.out.println("Success! " + teamName + " was added to the fantasy league!");
                    break;
                } catch (InvalidTeamException e) {
                    System.out.println(e.getMsg());
                }
            }
        }
    }

    private ArrayList<Player> askUserToSelectPlayerByIndex(String teamName) {
        System.out.println("Enter the number of the player you want to add to " + teamName + ".");
        return printAvailablePlayerNames();
    }


    private ArrayList<Player> printAvailablePlayerNames() {
        int i = 1;
        ArrayList<Player> players = new ArrayList<>();

        Set<Player> availablePlayers = league.getAvailablePlayers();
        ArrayList<String> availablePlayerNames = new ArrayList<>();

        for (Player p : availablePlayers) {
            String title = "[" + i + "] " + p.getPlayerName() + " (" + p.getPosition() + ")";
            System.out.println(title);
            players.add(p);
            i++;
        }
        return players;
    }


//    private ArrayList<Team> askUserToSelectTeamByIndex() {
//        System.out.println("Enter the number of the team you want to add the player to " + OPTION_GO_BACK);
//        System.out.println("Here's the list of teams:");
//        ArrayList<Team> teams = new ArrayList<>();
//        int i = 1;
//        for (Team t : league.getTeams()) {
//            System.out.println("[" + i + "] - " + t.getTeamName());
//            teams.add(t);
//            i++;
//        }
//        return teams;
//    }

    private void printActiveTeamNames() {
        if (atLeastOneTeam()) {
            System.out.println("Here's the list of teams in the league:");
            System.out.println(getTeamNames(league.getTeams()));
        } else {
            System.out.println("You're the first team in the league!");
        }
    }

    // EFFECTS: Prints the team name and roster for every team in the league
    public void printTeamsAndPlayers() {
        for (Team t : league.getTeams()) {
            ArrayList<Player> players = new ArrayList<>();
            System.out.println("team: " + t.getTeamName() + ", Roster: " +
                    getPlayerNamesAndPositions(t.getPlayers()));
        }
    }

    private void printMenu() {
        System.out.println();
        if (fantasyManager.getCurrentFantasyWeek() > 0) {
            System.out.println("Fantasy Week " + fantasyManager.getCurrentFantasyWeek() + " of " + FANTASY_WEEKS);
        }
        if (moreTeamsNeeded()) {
            System.out.println("*** Please create " + (MIN_PARTICIPANTS - league.getTeams().size()) + " more teams in order to play ***");
        }
        if (leagueCanDraft()) {
            System.out.println("[" + COMMAND_DRAFT + "]   - Draft");
        }
        if (leagueIsDrafted()) {
            System.out.println("[" + COMMAND_ADVANCE_WEEK + "] - Advance to the Next Fantasy Week");
        }
        if (teamCanBeAdded()) {
            System.out.println("[" + COMMAND_CREATE_TEAM + "]    - Create Fantasy team");
        }
        if (atLeastOneTeam()) {
            System.out.println("[" + COMMAND_VIEW_TEAM_NAMES + "]    - View Fantasy Teams");
//            if (leagueIsDrafted()) {
//                System.out.println("[" + COMMAND_ADD_PLAYER + "]  - Add a player");
//                System.out.println("[" + COMMAND_ADD_STAT + "]    - Add a goal");
//            }
        }
        // TODO 4: Create Leaderboard functionality
//        System.out.println("To view this week's leaderboard, type \"" + COMMAND_VIEW_WEEK_LEADER + "\".");
//        System.out.println("[" + COMMAND_VIEW_OVERALL_LEADER + "] - Leaderboard");
        System.out.println("[" + COMMAND_QUIT + "]    - Quit");
        System.out.println("[" + COMMAND_SAVE_AND_QUIT + "]    - Save & Quit");
    }


    //TODO: Specify
    public int isInteger(String answer) throws NumberFormatException {
        return Integer.parseInt(answer);
    }

    // EFFECTS: Returns true if there is at least one team in the league
    private boolean atLeastOneTeam() {
        return league.getTeams().size() > 0;
    }

    private boolean teamCanBeAdded() {
        return league.getTeams().size() < MAX_PARTICIPANTS;
    }

    private boolean leagueCanDraft() {
        return (league.getTeams().size() >= MIN_PARTICIPANTS && !fantasyManager.isDrafted());
    }

    private boolean leagueIsDrafted() {
        return fantasyManager.isDrafted();
    }

    private boolean moreTeamsNeeded() {
        return (league.getTeams().size() < MIN_PARTICIPANTS && !fantasyManager.isDrafted());
    }

}
