package ui;

import exceptions.InvalidTeamException;
import interfaces.Loadable;
import managers.FantasyManager;
import models.League;
import models.Team;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static models.League.MAX_PARTICIPANTS;

public class PucksInDeep extends JFrame implements Loadable, ActionListener {
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

    private FantasyManager fantasyManager;
    private InputDialog dialogBox;
    JPanel p = new JPanel();
    JTextField t = new JTextField("Hey bud");
    JTextArea console = new JTextArea();
    JButton delete = new JButton("Delete");
    JButton view = new JButton("View");
    JButton quit = new JButton("Quit");
    JButton save = new JButton("Save");

    public static void main(String[] args) {
        new PucksInDeep();
    }

    public PucksInDeep() {
        super("Pucks In Deep");
        load();

        // Modelled after: https://alvinalexander.com/java/jframe-size-example-screen
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height * 1 / 2;
        int width = screenSize.width * 1 / 2;
        setSize(width, height);
        setResizable(true);
        setLocationRelativeTo(null);
        setVisible(true);

        dialogBox = new InputDialog();


        JButton add = new JButton("Add");
        add.setActionCommand(COMMAND_CREATE_TEAM);
        add.addActionListener(this);

        p.add(add);
        p.add(delete);
        p.add(view);
        p.add(quit);
        p.add(save);

        p.add(t);
        p.add(console);

        add(p);
    }

    private void createUserGeneratedTeam() {
        while (true) {
            printActiveTeamNames();
            try {
                String teamName = dialogBox.getUserInput("Enter a unique team name.");
                Team team = new Team(teamName);
                fantasyManager.getLeague().addTeam(team);
                System.out.println("Success! " + teamName + " was added to the fantasy league!");
                break;
            } catch (InvalidTeamException e) {
                System.out.println(e.getMsg());
            } catch (NullPointerException e) {
                break;
            }
        }
    }

    private void deleteUserGeneratedTeam() {
        while (true) {
            printActiveTeamNames();
            try {
                String teamName = dialogBox.getUserInput("Enter a unique team name.");
                Team team = new Team(teamName);
                fantasyManager.getLeague().addTeam(team);
                System.out.println("Success! " + teamName + " was added to the fantasy league!");
                break;
            } catch (InvalidTeamException e) {
                System.out.println(e.getMsg());
            } catch (NullPointerException e) {
                break;
            }
        }
    }
//
//    private void printActiveTeamNames() {
//        console.

    private void printActiveTeamNames() {
//        if (atLeastOneTeam()) {
//            console.
//            System.out.println("Here's the list of teams in the league:");
//            int i = 1;
//            for (Team t : fantasyManager.getLeague().getTeams()) {
//                String title = "[" + i + "] - " + t.getTeamName();
//                System.out.println(title);
//                i++;
//            }
//        } else {
//            System.out.println("You're the first team in the league!");
//        }
    }

    private boolean atLeastOneTeam() {
        return fantasyManager.size() > 0;
    }

    private boolean teamCanBeAdded() {
        return fantasyManager.size() < MAX_PARTICIPANTS;
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

    /**
     * Invoked when an action occurs.
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals(COMMAND_CREATE_TEAM) && teamCanBeAdded()) {
            createUserGeneratedTeam();
//        } else if (e.getActionCommand().equals(COMMAND_REMOVE_TEAM) && teamCanBeRemoved()) {
//            deleteUserGeneratedTeam();
//        } else if (e.getActionCommand().equals(COMMAND_VIEW_TEAM_NAMES) && atLeastOneTeam()) {
//            printTeamsAndPlayers();
//        } else if (e.getActionCommand().equals(COMMAND_DRAFT) && leagueCanDraft()) {
//            selectDraftTypeAndDraftTeams();
//        } else if (e.getActionCommand().equals(COMMAND_ADVANCE_WEEK) && leagueIsDrafted() && leagueCanAdvance()) {
//            updateLeague();
//        } else if (e.getActionCommand().equals(COMMAND_VIEW_OVERALL_LEADER) && leagueHasStarted()) {
//            displayOverallLeaders();
//        } else if (e.getActionCommand().equals(COMMAND_VIEW_WEEK_LEADER) && leagueHasStarted()) {
//            displayThisWeeksLeaders();
        } else if (e.getActionCommand().equals(COMMAND_QUIT)) {
            System.out.println("Thanks for playing! See you next time.");
//            break;
        } else if (e.getActionCommand().equals(COMMAND_SAVE_AND_QUIT)) {
            System.out.println("Saving data...");
//            save();
            System.out.println("Your data has been saved! See you next time.");
//            break;
        } else {
            System.out.println("Sorry, command not recognized!");
        }

    }
}
