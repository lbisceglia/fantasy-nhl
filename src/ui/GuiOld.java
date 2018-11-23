package ui;

import exceptions.InvalidTeamException;
import interfaces.Loadable;
import managers.FantasyManager;
import models.League;
import models.Team;

import javax.swing.*;
import java.awt.*;
import java.io.*;

import static javax.swing.JFrame.EXIT_ON_CLOSE;
import static models.League.MAX_PARTICIPANTS;

public class GuiOld implements Loadable, Serializable {
    private static final String COMMAND_CREATE_TEAM = "Create Team";
    private static final String COMMAND_REMOVE_TEAM = "Delete Team";
    private static final String COMMAND_VIEW_TEAM_NAMES = "view";
    private static final String COMMAND_DRAFT = "draft";
    private static final String COMMAND_ADVANCE_WEEK = "advance";
    private static final String COMMAND_VIEW_WEEK_LEADER = "week";
    private static final String COMMAND_VIEW_OVERALL_LEADER = "overall";
    private static final String COMMAND_GO_BACK = "back";
    private static final String COMMAND_QUIT = "quit";
    private static final String COMMAND_SAVE_AND_QUIT = "save";

    private FantasyManager fantasyManager;
    private InputDialog dialogBox;
    private JPanel panelMain;
    private JButton btnAdd;
    private JButton btnRemove;
    private JButton btnView;
    private JComboBox comboBox1;
    private JTabbedPane tabbedPane1;

    public GuiOld() {
        load();
        btnAdd = new JButton(COMMAND_CREATE_TEAM);
        btnAdd.addActionListener(event->{
            createUserGeneratedTeam();
        });

        btnRemove = new JButton(COMMAND_REMOVE_TEAM);
        btnRemove.addActionListener(event-> {


    });

    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pucks In Deep");
        frame.setContentPane(new GuiOld().panelMain);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int height = screenSize.height / 2;
        int width = screenSize.width / 2;
        frame.setSize(width, height);
        frame.setResizable(true);
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        new GuiOld();


//        GridLayout gl = new GridLayout();
//        panelMain.setLayout(gl);
//        add(panelMain);
//
//        gui.btnAdd = new JButton(COMMAND_CREATE_TEAM);
//        gui.btnAdd.addActionListener(event->{
//            gui.createUserGeneratedTeam();
//        });
//
//        gui.btnRemove = new JButton(COMMAND_REMOVE_TEAM);
//        gui.btnRemove.addActionListener(event-> {
//
//        });

//        btnView = new JButton(COMMAND_VIEW_TEAM_NAMES);
//        btnView.addActionListener(event-> {
//
//        });

//        gui.panelMain.add(gui.btnAdd);
//        gui.panelMain.add(gui.btnRemove);

        frame.setVisible(true);
    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//        if (e.getActionCommand().equals(COMMAND_CREATE_TEAM) && teamCanBeAdded()) {
//            createUserGeneratedTeam();
////        } else if (e.getActionCommand().equals(COMMAND_REMOVE_TEAM) && teamCanBeRemoved()) {
////            deleteUserGeneratedTeam();
////        } else if (e.getActionCommand().equals(COMMAND_VIEW_TEAM_NAMES) && atLeastOneTeam()) {
////            printTeamsAndPlayers();
////        } else if (e.getActionCommand().equals(COMMAND_DRAFT) && leagueCanDraft()) {
////            selectDraftTypeAndDraftTeams();
////        } else if (e.getActionCommand().equals(COMMAND_ADVANCE_WEEK) && leagueIsDrafted() && leagueCanAdvance()) {
////            updateLeague();
////        } else if (e.getActionCommand().equals(COMMAND_VIEW_OVERALL_LEADER) && leagueHasStarted()) {
////            displayOverallLeaders();
////        } else if (e.getActionCommand().equals(COMMAND_VIEW_WEEK_LEADER) && leagueHasStarted()) {
////            displayThisWeeksLeaders();
//        } else if (e.getActionCommand().equals(COMMAND_QUIT)) {
//            System.out.println("Thanks for playing! See you next time.");
////            break;
//        } else if (e.getActionCommand().equals(COMMAND_SAVE_AND_QUIT)) {
//            System.out.println("Saving data...");
////            save();
//            System.out.println("Your data has been saved! See you next time.");
////            break;
//        } else {
//            System.out.println("Sorry, command not recognized!");
//        }
//
//    }

    private boolean teamCanBeAdded() {
        return fantasyManager.size() < MAX_PARTICIPANTS;
    }

    private void createUserGeneratedTeam() {
        String errorMsg = "";
        while (true) {
            printActiveTeamNames();
            try {
                String teamName = JOptionPane.showInputDialog(this,errorMsg + "Enter a unique team name.");
                Team team = new Team(teamName);
                fantasyManager.getLeague().addTeam(team);
                System.out.println("Success! " + teamName + " was added to the fantasy league!");
                break;
            } catch (InvalidTeamException e) {
                errorMsg = e.getMsg() + "\n";
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
