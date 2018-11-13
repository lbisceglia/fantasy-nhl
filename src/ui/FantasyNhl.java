package ui;

import model.League.League;
import model.Player.Player;
import model.Stat.GameStat;
import model.Team.Team;
import model.exceptions.InvalidTeamException;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import static model.League.League.loadLeague;
import static model.Stat.GameStat.StatType.goals;
import static ui.LeagueFormatter.getTeamNames;
import static ui.TeamFormatter.getPlayerNamesAndPositions;

//import model.League.LeagueManager;

public class FantasyNhl implements Serializable {
    // TODO 2: Add League command phrase once multiple leagues are supported
    private static final String COMMAND_CREATE_LEAGUE = "league";
    private static final String COMMAND_LOAD_LEAGUE = "load";
    private static final String COMMAND_CREATE_TEAM = "team";
    private static final String COMMAND_VIEW_TEAM_NAMES = "view";
    private static final String COMMAND_ADD_PLAYER = "player";
    private static final String COMMAND_ADD_STAT = "goal";
    // TODO 4: Create leaderboard functionality
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_GO_BACK = "back";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SAVE_AND_QUIT = "save";
    private static final String OPTION_GO_BACK = " or type \"" + COMMAND_GO_BACK + "\" to return to the previous menu.";

    Scanner scanner = new Scanner(System.in);
    //    LeagueManager leagueManager;
    League league;

    public FantasyNhl() {

        // Modeled after B04-LoggingCalculator starter file
        String operation;
        System.out.println("Welcome to DreamTeam NHL!");
        league = loadLeague();
//        league.load();
//        League league = loadLeague();

        while (true) {
            printMenu();
            operation = scanner.nextLine();
            System.out.println("You selected " + operation + ".");

            // User creates a Team
            if (operation.equals(COMMAND_CREATE_TEAM)) {
                createUserGeneratedTeam();
                // User views the teams in the league
            } else if (operation.equals(COMMAND_VIEW_TEAM_NAMES) && atLeastOneTeam()) {
                printTeamsAndPlayers();

                // User adds a player to a team
            } else if (operation.equals(COMMAND_ADD_PLAYER) && atLeastOneTeam()) {
                addSelectedPlayerToSelectedTeam();

            } else if (operation.equals(COMMAND_ADD_STAT)) {
                createUserGeneratedStat();
            }
            // User quits the app
            else if (operation.equals(COMMAND_QUIT)) {
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
//                Set<Team> teams = league.getTeams();
//                for (Team t : teams) {
//                    List<Player> players = t.getPlayers();
//                    for (Player p : players) {
//                        StatManager stats = p.getStatManager();
//                        try {
//                            stats.addGameStats(gameIDs);
//                        } catch (IOException e) {
//                            System.out.println("Issue updating game stats.");
//                            e.printStackTrace();
//                        }
//                    }
//                    System.out.println(t.getTeamName() + ", Fantasy Points: " + t.getFantasyPoints());
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
        new FantasyNhl();
    }

    private void createUserGeneratedStat() {
        List<Player> allPlayers = new ArrayList<>();
        for (Team t : league.getTeams()) {
            allPlayers.addAll(t.getPlayers());
        }

        while (true) {
            System.out.println("Enter the number of the player you want to award a goal to," + OPTION_GO_BACK);

            int i = 1;
            for (Player p : allPlayers) {
                String title = "[" + i + "] " + p.getPlayerName() + " (" + p.getPosition() + ")";
                System.out.println(title);
                i++;
            }

            String playerNumber = scanner.nextLine();

            if (playerNumber.equals(COMMAND_GO_BACK)) {
                break;
            } else {
                try {
                    int playerIndex = isInteger(playerNumber) - 1;

                    if (playerIndex >= 0 && playerIndex < allPlayers.size()) {
                        LocalDate date = LocalDate.now();
                        GameStat gameStat = new GameStat(date, 2017020001, allPlayers.get(playerIndex), goals, 1);
                        break;
                    } else {
                        printIsInvalidOption(playerNumber);
                    }
                } catch (NumberFormatException numberFormatException) {
                    printIsInvalidOption(playerNumber);
                }
            }
        }
    }


    private void addSelectedPlayerToSelectedTeam() {
        while (true) {
            ArrayList<Team> teams = askUserToSelectTeamByIndex();
            String teamNumber = scanner.nextLine();
            Team team = null;

            if (teamNumber.equals(COMMAND_GO_BACK)) {
                break;
            } else {
                try {
                    int teamIndex = isInteger(teamNumber) - 1;

                    if (teamIndex >= 0 && teamIndex < teams.size()) {
                        team = teams.get(teamIndex);
                        String teamName = team.getTeamName();
                        selectPlayer(team, teamName);
                        break;
                    } else {
                        printIsInvalidOption(teamNumber);
                    }

                } catch (NumberFormatException numberFormatException) {
                    printIsInvalidOption(teamNumber);
                }
            }
        }
    }

    private void selectPlayer(Team team, String teamName) {
        while (true) {
            ArrayList<Player> players = askUserToSelectPlayerByIndex(teamName);
            String playerNumber = scanner.nextLine();

            if (playerNumber.equals(COMMAND_GO_BACK)) {
                break;
            } else {
                try {
                    int playerIndex = isInteger(playerNumber) - 1;

                    if (playerIndex >= 0 && playerIndex < players.size()) {
                        addPlayerToFantasyTeam(team, teamName, players, playerIndex);
                        break;
                    } else {
                        printIsInvalidOption(playerNumber);
                    }
                } catch (NumberFormatException numberFormatException) {
                    printIsInvalidOption(playerNumber);
                }
            }
        }
    }

    private void printIsInvalidOption(String playerNumber) {
        System.out.println(playerNumber + " is not a valid option");
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
                    league.getTeamManager().isValidTeam(team);
                    league.addTeam(team);
                    System.out.println("Success! " + teamName + " was added to the fantasy league!");
                    break;
                } catch (InvalidTeamException e) {
                    System.out.println("Sorry, that team name is already in the league.");
                }
            }
        }
    }

    private ArrayList<Player> askUserToSelectPlayerByIndex(String teamName) {
        System.out.println("Enter the number of the player you want to add to " + teamName + "," + OPTION_GO_BACK);
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


    private ArrayList<Team> askUserToSelectTeamByIndex() {
        System.out.println("Enter the number of the team you want to add the player to " + OPTION_GO_BACK);
        System.out.println("Here's the list of teams:");
        ArrayList<Team> teams = new ArrayList<>();
        int i = 1;
        for (Team t : league.getTeams()) {
            System.out.println("[" + i + "] - " + t.getTeamName());
            teams.add(t);
            i++;
        }
        return teams;
    }

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
            System.out.println("Team: " + t.getTeamName() + ", Roster: " +
                    getPlayerNamesAndPositions(t.getPlayers()));
        }
    }

    private void printMenu() {
        System.out.println("[" + COMMAND_CREATE_TEAM + "]    - Create Fantasy Team");
        if (atLeastOneTeam()) {
            System.out.println("[" + COMMAND_VIEW_TEAM_NAMES + "]    - View Fantasy Teams");
            System.out.println("[" + COMMAND_ADD_PLAYER + "]  - Add a player");
            System.out.println("[" + COMMAND_ADD_STAT + "]    - Add a goal");
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

    // EFFECTS: Returns true if there is at least one Team in the League
    private boolean atLeastOneTeam() {
        return league.getTeams().size() > 0;
    }

}
