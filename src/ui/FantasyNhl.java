package ui;

import model.FantasyLeague;

import java.util.Scanner;

public class FantasyNhl {
    // TODO 2: Add FantasyLeague command phrase once multiple leagues are supported
    //    private static final String COMMAND_CREATE_LEAGUE = "league";
    private static final String COMMAND_CREATE_TEAM = "team";
    private static final String COMMAND_VIEW_TEAM_NAMES = "names";
    private static final String COMMAND_ADD_PLAYER = "player";
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_QUIT = "quit";

    Scanner scanner = new Scanner(System.in);

    public FantasyNhl() {

        // Modeled after B04-LoggingCalculator Starter file
        String operation;
        System.out.println("Welcome to DreamTeam NHL!");
        FantasyLeague league1 = new FantasyLeague();
        while (true) {
            printMenu();
            operation = scanner.nextLine();
            System.out.println("You selected " + operation + ".");
            if (operation.equals(COMMAND_CREATE_TEAM)) {
                System.out.println("What do you want to call your team?");
                String teamName = scanner.nextLine();
                if (!league1.isExistingTeamName(teamName)) {
                    league1.createTeam(teamName);
                } else System.out.println("Sorry, that team name already exists in this league.");
            } else if (operation.equals(COMMAND_VIEW_TEAM_NAMES)) {
                System.out.println("The teams in this league are: \n" +
                        league1.getTeamNames());
            } else if (operation.equals(COMMAND_ADD_PLAYER)) {
                System.out.println("Which player do you want to add? \n" +
                        "Please type in their full name.");
                // TODO 3: Create add-player-to-team functionality
                System.out.println("Your player has been added!");
            } else if (operation.equals(COMMAND_VIEW_WEEK_LEADER)) {
                System.out.println("Grabbing weekly leader...");
                // TODO 4: Create leaderboard functionality
                System.out.println("Weekly leader displayed.");
            } else if (operation.equals(COMMAND_VIEW_OVERALL_LEADER)) {
                System.out.println("Grabbing overall leader...");
                // TODO 4: Create leaderboard functionality
                System.out.println("Overall leader displayed.");
            } else if (operation.equals(COMMAND_QUIT)) {
                System.out.println("Thanks for playing! See you next time.");
                break;
            } else {
                System.out.println("Sorry, command not recognized!");
            }
        }
    }

    private void printMenu() {
        // TODO 2: Add FantasyLeague command once multiple leagues are supported
        // System.out.println("To create a new fantasy league, type \"" + COMMAND_CREATE_LEAGUE + "\".");
        System.out.println("To add a fantasy team to the league, type \"" + COMMAND_CREATE_TEAM + "\".");
        System.out.println("To view the names of the fantasy teams in the league, type \"" + COMMAND_VIEW_TEAM_NAMES + "\".");
        System.out.println("To add a player to an existing team, type \"" + COMMAND_ADD_PLAYER + "\".");
        System.out.println("To view this week's leaderboard, type \"" + COMMAND_VIEW_WEEK_LEADER + "\".");
        System.out.println("To view the overall leaderboard, type \"" + COMMAND_VIEW_OVERALL_LEADER + "\".");
        System.out.println("To quit, type \"" + COMMAND_QUIT + "\".");
    }

    public static void main(String[] args) {
        new FantasyNhl();
    }
}
