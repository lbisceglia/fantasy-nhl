package ui;

import model.League.League;
import model.Player.Goalie;
import model.Player.Player;
import model.Player.Position;
import model.Player.Skater;
import model.Team.Team;
import model.exceptions.DuplicateMatchException;
import model.exceptions.NoMatchException;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

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

    public FantasyNhl(Map<Team, ArrayList<Player>> teamPlayerMap, ArrayList<Player> availablePlayers) throws IOException {

        // Modeled after B04-LoggingCalculator starter file
        String operation;
        System.out.println("Welcome to DreamTeam NHL!");
        league = new League(teamPlayerMap, availablePlayers);
        league.load();
//        League league = loadLeague();

        while (true) {
            printMenu();
            operation = scanner.nextLine();
            System.out.println("You selected " + operation + ".");

            // User creates a Team
            if (operation.equals(COMMAND_CREATE_TEAM)) {
                while (true) {
                    if (atLeastOneTeam()) {
                        System.out.println("Here's the list of teams in the league:");
                        System.out.println(league.getTeamNames());
                    } else {
                        System.out.println("You're the first team in the league!");
                    }
                    System.out.println("Enter a unique team name" + OPTION_GO_BACK);
                    String teamName = scanner.nextLine();

                    if (teamName.equals(COMMAND_GO_BACK)) {
                        break;
                    } else {
                        try {
                            league.addTeam(teamName);
                            System.out.println("Success! " + teamName + " was added to the fantasy league!");
                            break;
                        } catch (DuplicateMatchException e) {
                            System.out.println("Sorry, that team name is already in the league.");
                        }
                    }
                }
                // User views the teams in the league
            } else if (operation.equals(COMMAND_VIEW_TEAM_NAMES) && atLeastOneTeam()) {
                league.printTeamsAndPlayers();

                // User adds a player to a team
            } else if (operation.equals(COMMAND_ADD_PLAYER) && atLeastOneTeam()) {
                while (true) {
                    System.out.println("Enter the number of the team you want to add the player to " + OPTION_GO_BACK);
                    System.out.println("Here's the list of teams:");
                    int i = 1;
                    // TODO: encapsulate this method
                    for (Team t : league.getTeams()) {
                        System.out.println("[" + i + "] - " + t.getTeamName());
                        i++;
                    }
                    String teamNumber = scanner.nextLine();

                    if (teamNumber.equals(COMMAND_GO_BACK)) {
                        break;
                    } else {
                        try {
                            int index = isInteger(teamNumber) - 1;

                            if (index >= 0 && index < league.getTeams().size()) {
                                String teamName = league.getTeamNames().get(index);

                                System.out.println("Which player do you want to add to " + teamName + "? \n" +
                                        "Please type in their full name " + OPTION_GO_BACK);
                                System.out.println("Skaters: " + league.getAvailableSkaterNames());
                                System.out.println("Goalies: " + league.getAvailableGoalieNames());
                                String playerName = scanner.nextLine();

                                if (playerName.equals(COMMAND_GO_BACK)) {
                                    break;
                                } else {
                                    try {
                                        league.addPlayerToFantasyTeam(teamName, playerName);
                                        Position p = league.getPlayerOnATeam(playerName, league.teamLookup(teamName)).getPlayerPosition();
                                        System.out.println("Success! " + playerName + " (" + p + ") was added to " + teamName + ".");
                                        break;
                                    } catch (NoMatchException e) {
                                        System.out.println("Sorry, " + playerName + " is not available in this league.");
                                    }
                                }
                            } else {
                                System.out.println(teamNumber + " is not a valid option.");
                            }

                        } catch (NumberFormatException numberFormatException) {
                            System.out.println(teamNumber + " is not a valid option.");

                        }
                    }
                }
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

    private void printMenu() {
            // TODO 2: Add League command once multiple leagues are supported
            // System.out.println("To create a new fantasy league, type \"" + COMMAND_CREATE_LEAGUE + "\".");
            System.out.println("[" + COMMAND_CREATE_TEAM + "]   - Create Fantasy Team");
        if (league.size() > 0) {
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

        Map<Team, ArrayList<Player>> teamPlayerMap = new HashMap<>();
        ArrayList<Player> availablePlayers = new ArrayList<>();

        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0) + " " + partsOfLine.get(1);
            Position position = Position.valueOf(partsOfLine.get(2));
            if (position.equals(Position.G)) {
                double savePercentage = Double.parseDouble(partsOfLine.get(3));
                double goalsAgainstAverage = Double.parseDouble(partsOfLine.get(4));

                Goalie g = new Goalie(name, savePercentage, goalsAgainstAverage);
                availablePlayers.add(g);

            } else {
                int totalGoals = Integer.parseInt(partsOfLine.get(3));
                int totalAssists = Integer.parseInt(partsOfLine.get(4));

                Skater s = new Skater(name, position, totalGoals, totalAssists);
                availablePlayers.add(s);
            }

        }
        new FantasyNhl(teamPlayerMap, availablePlayers);
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
        return league.size() > 0;
    }

}
