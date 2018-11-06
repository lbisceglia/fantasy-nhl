package ui;

import model.League.League;
import model.Player.Goalie;
import model.Player.Player;
import model.Player.Position;
import model.Player.Skater;
import model.Team.Team;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

import static ui.LeagueFormatter.getTeamNames;
import static ui.TeamFormatter.convertPlayerSetToList;
import static ui.TeamFormatter.getPlayerNamesAndPositions;

public class FantasyNhl implements Serializable {
    // TODO 2: Add League command phrase once multiple leagues are supported
    private static final String COMMAND_CREATE_LEAGUE = "league";
    private static final String COMMAND_LOAD_LEAGUE = "load";
    private static final String COMMAND_CREATE_TEAM = "team";
    private static final String COMMAND_VIEW_TEAM_NAMES = "view";
    private static final String COMMAND_ADD_PLAYER = "player";
    // TODO 4: Create leaderboard functionality
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_GO_BACK = "back";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SAVE_AND_QUIT = "save";
    private static final String OPTION_GO_BACK = " or type \"" + COMMAND_GO_BACK + "\" to return to the previous menu.";

    Scanner scanner = new Scanner(System.in);
    League league;

    public FantasyNhl(Set<Player> availablePlayers) throws IOException {

        // Modeled after B04-LoggingCalculator starter file
        String operation;
        System.out.println("Welcome to DreamTeam NHL!");
        league = new League(availablePlayers);
        league.load();
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
            }
            // User enters an invalid command
            else {
                System.out.println("Sorry, command not recognized!");
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
//                try {
                    Team team = new Team(teamName);
                    league.addTeam(team);
                    System.out.println("Success! " + teamName + " was added to the fantasy league!");
                    break;
//                } catch (DuplicateMatchException e) {
//                    System.out.println("Sorry, that team name is already in the league.");
//                }
            }
        }
    }

    private ArrayList<Player> askUserToSelectPlayerByIndex(String teamName) {
        System.out.println("Enter the number of the player you want to add to " + teamName + ", " + OPTION_GO_BACK);
        return printAvailablePlayerNames();
    }


    private ArrayList<Player> printAvailablePlayerNames() {
        int i = 1;
        ArrayList<Player> players = new ArrayList<>();

        Set<Player> availablePlayers = league.getAvailablePlayers();
        ArrayList<String> availableSkaterNames = new ArrayList<>();
        ArrayList<String> availableGoalieNames = new ArrayList<>();

        for (Player p : availablePlayers) {
            if (p instanceof Skater) {
                players.add(p);
                availableSkaterNames.add("[" + i + "] " + p.getPlayerName());
                i++;
            }
        }
        for (Player p : availablePlayers) {
            if (p instanceof Goalie) {
                players.add(p);
                availableGoalieNames.add("[" + i + "] " + p.getPlayerName());
                i++;
            }
        }
        System.out.println("Skaters: " + availableSkaterNames);
        System.out.println("Goalies: " + availableGoalieNames);
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
            System.out.println("Team: " + t.getTeamName() + ", Roster: " + getPlayerNamesAndPositions(convertPlayerSetToList(t.getPlayers())));
        }
    }

    private void printMenu() {
        // TODO 2: Add League command once multiple leagues are supported
        // System.out.println("To create a new fantasy league, type \"" + COMMAND_CREATE_LEAGUE + "\".");
        System.out.println("[" + COMMAND_CREATE_TEAM + "]   - Create Fantasy Team");
        if (atLeastOneTeam()) {
            System.out.println("[" + COMMAND_VIEW_TEAM_NAMES + "]   - View Fantasy Teams");
            System.out.println("[" + COMMAND_ADD_PLAYER + "] - Add a player");
        }
        // TODO 4: Create Leaderboard functionality
//        System.out.println("To view this week's leaderboard, type \"" + COMMAND_VIEW_WEEK_LEADER + "\".");
//        System.out.println("To view the overall leaderboard, type \"" + COMMAND_VIEW_OVERALL_LEADER + "\".");
        System.out.println("[" + COMMAND_QUIT + "]   - Quit");
        System.out.println("[" + COMMAND_SAVE_AND_QUIT + "]   - Save & Quit");
    }

    public static void main(String[] args) throws IOException {
        //Modeled after P4-FileReaderWriter starter file
        List<String> lines = Files.readAllLines(Paths.get("availablePlayers.txt"));

        Set<Player> availablePlayers = new HashSet<>();

        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0) + " " + partsOfLine.get(1);

            String position = partsOfLine.get(2);
            if (position.equals("G")) {
                double savePercentage = Double.parseDouble(partsOfLine.get(3));
                double goalsAgainstAverage = Double.parseDouble(partsOfLine.get(4));

                Goalie g = new Goalie(name, savePercentage, goalsAgainstAverage);
                availablePlayers.add(g);

            } else {
                Position p = Position.valueOf(position);
                int totalGoals = Integer.parseInt(partsOfLine.get(3));
                int totalAssists = Integer.parseInt(partsOfLine.get(4));

                Skater s = new Skater(name, p, totalGoals, totalAssists);
                availablePlayers.add(s);
            }

        }
        new FantasyNhl(availablePlayers);
//        new FantasyNhl();

    }

    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
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
