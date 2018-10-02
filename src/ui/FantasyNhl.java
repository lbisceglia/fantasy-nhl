package ui;

import model.League.League;
import model.Player.Goalie;
import model.Player.Position;
import model.Player.Skater;
import model.Team.PlayerList;
import model.Team.Team;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class FantasyNhl implements Serializable{
    // TODO 2: Add League command phrase once multiple leagues are supported
    private static final String COMMAND_CREATE_LEAGUE = "league";
    private static final String COMMAND_LOAD_LEAGUE = "load";
    private static final String COMMAND_CREATE_TEAM = "team";
    private static final String COMMAND_VIEW_TEAM_NAMES = "view";
    private static final String COMMAND_ADD_PLAYER = "player";
    // TODO 4: Create leaderboard functionality
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SAVE_AND_QUIT = "save";

    Scanner scanner = new Scanner(System.in);

    public FantasyNhl(PlayerList availablePlayers) throws IOException {

        // Modeled after B04-LoggingCalculator starter file
        String operation;
        System.out.println("Welcome to DreamTeam NHL!");
        League league = new League(availablePlayers);
        league.load();

        while (true) {
            printMenu();
            operation = scanner.nextLine();
            System.out.println("You selected " + operation + ".");

            // User creates a Team
            if (operation.equals(COMMAND_CREATE_TEAM)) {
                System.out.println("What do you want to call your team?");
                String teamName = scanner.nextLine();
                if (!league.containsTeamName(teamName)) {
                    league.addTeam(teamName);
                    System.out.println("Success! " + teamName + " was added to the fantasy league!");
                } else {
                    System.out.println("The team is already in the league. Please choose a different name.");
                }
                // User views the teams in the league
            } else if (operation.equals(COMMAND_VIEW_TEAM_NAMES)) {
                league.printTeamsAndPlayers();

                // User adds a player to a team
            } else if (operation.equals(COMMAND_ADD_PLAYER)) {
                System.out.println("Which team do you want to add the player to?");
                System.out.println(league.getTeamNames());
                String teamName = scanner.nextLine();

                if (!league.containsTeamName(teamName)) {
                    System.out.println(teamName + " does not exist in this league.");
                } else {
                    System.out.println("Which player do you want to add? \n" +
                            "Please type in their full name.");
                    System.out.println(league.getAvailablePlayerNames());
                    String playerName = scanner.nextLine();

                    if (league.containsAvailablePlayerName(playerName)) {
                        league.addPlayerToFantasyTeam(teamName, playerName);
                        System.out.println("Success! " + playerName + " was added to your fantasy team.");
                    } else {
                        System.out.println(playerName + " is not available in this league.");
                    }
                }
                // User quits the app
            } else if (operation.equals(COMMAND_QUIT)) {
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
        System.out.println("To add a fantasy team to the league, type \"" + COMMAND_CREATE_TEAM + "\".");
        System.out.println("To view the names of the fantasy teams in the league, type \"" + COMMAND_VIEW_TEAM_NAMES + "\".");
        System.out.println("To add a player to an existing team, type \"" + COMMAND_ADD_PLAYER + "\".");
        // TODO 4: Create Leaderboard functionality
//        System.out.println("To view this week's leaderboard, type \"" + COMMAND_VIEW_WEEK_LEADER + "\".");
//        System.out.println("To view the overall leaderboard, type \"" + COMMAND_VIEW_OVERALL_LEADER + "\".");
        System.out.println("To quit without saving, type \"" + COMMAND_QUIT + "\".");
        System.out.println("To save and quit, type \"" + COMMAND_SAVE_AND_QUIT + "\".");
    }

    public static void main(String[] args) throws IOException {
        //Modeled after P4-FileReaderWriter starter file
        List<String> lines = Files.readAllLines(Paths.get("availablePlayers.txt"));
        PlayerList availablePlayers = new Team("Available Players");
        for (String line : lines) {
            ArrayList<String> partsOfLine = splitOnSpace(line);
            String name = partsOfLine.get(0) + " " + partsOfLine.get(1);
            Position position = Position.valueOf(partsOfLine.get(2));
            int weekFantasyPoints = Integer.parseInt(partsOfLine.get(3));
            int totalFantasyPoints = Integer.parseInt(partsOfLine.get(4));
            if (position.equals("G")) {
                double savePercentage = Double.parseDouble(partsOfLine.get(5));
                double goalsAgainstAverage = Double.parseDouble(partsOfLine.get(6));

                Goalie g = new Goalie(name, weekFantasyPoints, totalFantasyPoints, savePercentage, goalsAgainstAverage);
                availablePlayers.addPlayer(g);

            } else {
                int totalGoals = Integer.parseInt(partsOfLine.get(5));
                int totalAssists = Integer.parseInt(partsOfLine.get(6));

                Skater s = new Skater(name, position, weekFantasyPoints, totalFantasyPoints, totalGoals, totalAssists);
                availablePlayers.addPlayer(s);
            }

        }
        new FantasyNhl(availablePlayers);
    }

    public static ArrayList<String> splitOnSpace(String line) {
        String[] splits = line.split(" ");
        return new ArrayList<>(Arrays.asList(splits));
    }

}
