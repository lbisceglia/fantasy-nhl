package ui;

import exceptions.ImpossibleDraftException;
import exceptions.InvalidDraftChoiceException;
import exceptions.InvalidFantasyWeekException;
import exceptions.InvalidTeamException;
import interfaces.Loadable;
import interfaces.Saveable;
import managers.DraftManager;
import managers.FantasyManager;
import models.League;
import models.Player;
import models.Team;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static managers.FantasyManager.FANTASY_WEEKS;
import static models.League.MAX_PARTICIPANTS;
import static models.League.MIN_PARTICIPANTS;
import static ui.formatters.TeamFormatter.getPlayerNamesAndPositions;

//import managers.LeagueManager;

public class FantasyNHL implements Serializable, Saveable, Loadable {
    private static final String COMMAND_CREATE_TEAM = "add";
    private static final String COMMAND_REMOVE_TEAM = "delete";
    private static final String COMMAND_VIEW_TEAM_NAMES = "view";
    private static final String COMMAND_DRAFT = "draft";
    private static final String COMMAND_ADVANCE_WEEK = "advance";
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_GO_BACK = "back";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SAVE_AND_QUIT = "save";
    private static final String OPTION_GO_BACK = " or type \"" + COMMAND_GO_BACK + "\" to return to the previous menu.";

    private Scanner scanner = new Scanner(System.in);
    private FantasyManager fantasyManager;

    public FantasyNHL() {

        // Modeled after B04-LoggingCalculator starter file
        System.out.println("Welcome to Fantasy NHL!");
        load();
        String operation;

        while (true) {
            printMenu();
            operation = scanner.nextLine();
            System.out.println("You selected " + operation + ".");

            // User creates a team
            if (operation.equals(COMMAND_CREATE_TEAM) && teamCanBeAdded()) {
                createUserGeneratedTeam();
            } else if (operation.equals(COMMAND_REMOVE_TEAM) && teamCanBeRemoved()) {
                deleteUserGeneratedTeam();
            } else if (operation.equals(COMMAND_VIEW_TEAM_NAMES) && atLeastOneTeam()) {
                printTeamsAndPlayers();
            } else if (operation.equals(COMMAND_DRAFT) && leagueCanDraft()) {
                selectDraftTypeAndDraftTeams();
            } else if (operation.equals(COMMAND_ADVANCE_WEEK) && leagueIsDrafted() && leagueCanAdvance()) {
                updateLeague();
            } else if (operation.equals(COMMAND_VIEW_OVERALL_LEADER) && leagueHasStarted()) {
                displayOverallLeaders();
            } else if (operation.equals(COMMAND_VIEW_WEEK_LEADER) && leagueHasStarted()) {
                displayThisWeeksLeaders();
            } else if (operation.equals(COMMAND_QUIT)) {
                System.out.println("Thanks for playing! See you next time.");
                break;
            } else if (operation.equals(COMMAND_SAVE_AND_QUIT)) {
                System.out.println("Saving data...");
                save();
                System.out.println("Your data has been saved! See you next time.");
                break;
            }
            else {
                System.out.println("Sorry, command not recognized!");
            }
        }
    }

    public static void main(String[] args) {
        new FantasyNHL();
    }

    private void displayOverallLeaders() {
        List<Team> teams = fantasyManager.getLeague().getTeams();
        //Modelled after Stack Overflow post: https://stackoverflow.com/questions/19471005/sorting-an-arraylist-of-objects-alphabetically
        Collections.sort(teams,Comparator.comparing(Team :: getOverallFantasyPoints).reversed());
        printOverallLeaders(teams);
    }

    private void displayThisWeeksLeaders() {
        List<Team> teams = fantasyManager.getLeague().getTeams();
        //Modelled after Stack Overflow post: https://stackoverflow.com/questions/19471005/sorting-an-arraylist-of-objects-alphabetically
        Collections.sort(teams,Comparator.comparing(Team :: getCurrentWeekFantasyPoints).reversed());
        printWeekLeaders(teams);
    }


    private void printOverallLeaders(List<Team> teams) {
        System.out.println("Overall Leaderboard:");
        double previous = 0;
        int i = 1;
        for(Team t : teams) {
            if (t.getOverallFantasyPoints() < previous) {
                i++;
            }
            String pts = new DecimalFormat("#.#").format(t.getOverallFantasyPoints());
            System.out.println(i + " - " + t.getTeamName() + " (" + pts + ") points");
            previous = t.getOverallFantasyPoints();
        }
    }


    private void printWeekLeaders(List<Team> teams) {
        System.out.println("Leaderboard after Week " + (fantasyManager.currentWeek()-1) + ":");
        double previous = 0;
        int i = 1;
        for(Team t : teams) {
            if (t.getCurrentWeekFantasyPoints() < previous) {
                i++;
            }
            String pts = new DecimalFormat("#.#").format(t.getCurrentWeekFantasyPoints());
            System.out.println(i + " - " + t.getTeamName() + " (" + pts + ") points");
            previous = t.getCurrentWeekFantasyPoints();
        }
    }

    private void updateLeague() {
        if (fantasyManager.currentWeek() < FANTASY_WEEKS) {
            try {

                fantasyManager.advanceFantasyWeekByOne();

                for(Team t : fantasyManager.getLeague().getTeams()) {
                    t.updateCurrentWeekFantasyPoints(fantasyManager.currentWeek());
                    t.updateOverallFantasyPoints(fantasyManager.currentWeek());
                }

                fantasyManager.updatePlayersOnWeek();

            } catch (InvalidFantasyWeekException e) {
                System.out.println("You cannot advance to that fantasy week.");
            }
        }
    }

    private void draftTeams() {
        try {
            List<Team> draftList = fantasyManager.selectDraftOrder();
            announceDraftOrder(draftList);

            System.out.println("The draft is beginning!" + "\n");

            if (fantasyManager.getDraftManager().getDraftType().equals(DraftManager.DraftType.AutoDraft)) {
                fantasyManager.getDraftManager().autoDraft(draftList, fantasyManager);
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
            updateLeague();

            System.out.println("\n" + "That concludes the Fantasy Draft! Good luck to all players!");

        } catch (ImpossibleDraftException e) {
            System.out.println(e.getMsg());
        }
    }

    private void selectDraftTypeAndDraftTeams() {
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

    private void announceDraftOrder(List<Team> draftList) {
        int size = fantasyManager.size();
        System.out.println("The random draft order is...");
        for (int i = 1; i <= size; i++) {
            Team t = draftList.get(i - 1);
            System.out.println(i + ": " + t.getTeamName());
        }
    }


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

    private void addPlayerToFantasyTeam(Team team, String teamName, ArrayList<Player> players, int playerIndex) {
        Player player = players.get(playerIndex);
        String playerName = player.getPlayerName();
        team.addPlayer(player);
        fantasyManager.getLeague().getAvailablePlayers().remove(player);
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
                    fantasyManager.getLeague().addTeam(team);
                    System.out.println("Success! " + teamName + " was added to the fantasy league!");
                    break;
                } catch (InvalidTeamException e) {
                    System.out.println(e.getMsg());
                }
            }
        }
    }

    private void deleteUserGeneratedTeam() {
        while (true) {
            System.out.println("Enter the number of the team you want to remove" + OPTION_GO_BACK);
            printActiveTeamNames();
            String teamNumber = scanner.nextLine();

            if (teamNumber.equals(COMMAND_GO_BACK)) {
                break;
            } else {
                try {
                    int teamIndex = isInteger(teamNumber) - 1;

                    if (teamIndex >= 0 && teamIndex < fantasyManager.size()) {
                        Team t = fantasyManager.getLeague().getTeams().get(teamIndex);
                        fantasyManager.getLeague().removeTeam(t);
                        System.out.println(t.getTeamName() + " was removed from the fantasy league.");
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


    private ArrayList<Player> askUserToSelectPlayerByIndex(String teamName) {
        System.out.println("Enter the number of the player you want to add to " + teamName + ".");
        return printAvailablePlayerNames();
    }


    private ArrayList<Player> printAvailablePlayerNames() {
        int i = 1;
        ArrayList<Player> players = new ArrayList<>();

        Set<Player> availablePlayers = fantasyManager.getLeague().getAvailablePlayers();

        for (Player p : availablePlayers) {
            String title = "[" + i + "] " + p.getPlayerName() + " (" + p.getPosition() + ")";
            System.out.println(title);
            players.add(p);
            i++;
        }
        return players;
    }


    private void printActiveTeamNames() {
        if (atLeastOneTeam()) {
            System.out.println("Here's the list of teams in the league:");
            int i = 1;
            for (Team t : fantasyManager.getLeague().getTeams()) {
                String title = "[" + i + "] - " + t.getTeamName();
                System.out.println(title);
                i++;
            }
        } else {
            System.out.println("You're the first team in the league!");
        }
    }

    // EFFECTS: Prints the team name and roster for every team in the league
    private void printTeamsAndPlayers() {
        for (Team t : fantasyManager.getLeague().getTeams()) {
            ArrayList<Player> players = new ArrayList<>();
            System.out.println("Team: " + t.getTeamName() + ", Roster: " +
                    getPlayerNamesAndPositions(t.getPlayers()));
        }
    }

    private void printMenu() {
        if (!leagueCanDraft()) {
            System.out.println();
        }
        if (fantasyManager.currentWeek() > 0) {
            System.out.println("Fantasy Week " + fantasyManager.currentWeek() + " of " + FANTASY_WEEKS);
            displayWeeksFantasyDates(fantasyManager.currentWeek());
        }
        if (moreTeamsNeeded()) {
            System.out.println("*** Please create " + (MIN_PARTICIPANTS - fantasyManager.size() + " more teams in order to play ***"));
        }
        if (leagueCanDraft()) {
            System.out.println("[" + COMMAND_DRAFT + "] - Draft");
        }
        if (leagueIsDrafted() && leagueCanAdvance()) {
            System.out.println("[" + COMMAND_ADVANCE_WEEK + "] - Advance to the Next Fantasy Week");
        }
        if (teamCanBeAdded()) {
            System.out.println("[" + COMMAND_CREATE_TEAM + "] - Create Fantasy team");
        }
        if (teamCanBeRemoved()) {
            System.out.println("[" + COMMAND_REMOVE_TEAM + "] - Delete Fantasy team");
        }
        if (atLeastOneTeam()) {
            System.out.println("[" + COMMAND_VIEW_TEAM_NAMES + "] - View Fantasy Teams");
        }
        if (leagueHasStarted()) {
            System.out.println("[" + COMMAND_VIEW_OVERALL_LEADER + "] - Overall Leaderboard");
            System.out.println("[" + COMMAND_VIEW_WEEK_LEADER + "] - This Week's Leaders");
        }
        System.out.println("[" + COMMAND_QUIT + "] - Quit");
        System.out.println("[" + COMMAND_SAVE_AND_QUIT + "] - Save & Quit");
    }

    private void displayWeeksFantasyDates(int week) {
        List<LocalDate> dates = fantasyManager.getWeekCutoffs();
        LocalDate startDate = dates.get(week - 1).plusDays(1);
        LocalDate endDate = dates.get(week);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        String start = startDate.format(formatter);
        String end = endDate.format(formatter);

        String dateRange = "(" + start + " - " + end + ")";
        System.out.println(dateRange);
    }



    private int isInteger(String answer) throws NumberFormatException {
        return Integer.parseInt(answer);
    }

    private void printIsInvalidOption(String invalidInput) {
        System.out.println(invalidInput + " is not a valid option");
    }

    // EFFECTS: Returns true if there is at least one team in the league
    private boolean atLeastOneTeam() {
        return fantasyManager.size() > 0;
    }

    private boolean teamCanBeAdded() {
        return fantasyManager.size() < MAX_PARTICIPANTS;
    }

    private boolean teamCanBeRemoved() {
        return (atLeastOneTeam() && !leagueIsDrafted());
    }

    private boolean leagueCanDraft() {
        return (fantasyManager.size() >= MIN_PARTICIPANTS && !fantasyManager.isDrafted());
    }

    private boolean leagueIsDrafted() {
        return fantasyManager.isDrafted();
    }

    private boolean moreTeamsNeeded() {
        return (fantasyManager.size() < MIN_PARTICIPANTS && !fantasyManager.isDrafted());
    }

    private boolean leagueHasStarted() {
        return (fantasyManager.currentWeek() >= 1);
    }

    private boolean leagueCanAdvance() {
        try {
            fantasyManager.checkFantasyWeekHasFullyElapsed(fantasyManager.currentWeek());
            return true;
        } catch (InvalidFantasyWeekException e) {
            return false;
        }
    }

    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(new File("fantasyManager.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(fantasyManager);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error initializing stream");
        }
    }

    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void load() {
        try {
            FileInputStream fi = new FileInputStream(new File("fantasyManager.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            FantasyManager restoredFantasyManager = (FantasyManager) oi.readObject();

            oi.close();
            fi.close();

            this.fantasyManager = restoredFantasyManager;

        } catch (FileNotFoundException e) {
            System.out.println("No saved state found. Creating a new league...");

            League league = new League();
            this.fantasyManager = new FantasyManager(league);

        } catch (IOException e) {
            System.out.println("No saved state found." + "\n" + "Creating new league!");

            League league = new League();
            this.fantasyManager = new FantasyManager(league);

        } catch (ClassNotFoundException e) {
            System.out.println("Class not found.");

            League league = new League();
            this.fantasyManager = new FantasyManager(league);
        }
    }

}
