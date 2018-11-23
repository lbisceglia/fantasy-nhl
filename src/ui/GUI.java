package ui;

import exceptions.InvalidFantasyWeekException;
import exceptions.InvalidTeamException;
import interfaces.Loadable;
import interfaces.Saveable;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import managers.FantasyManager;
import models.League;
import models.Team;

import java.io.*;

import static models.League.MAX_PARTICIPANTS;
import static models.League.MIN_PARTICIPANTS;

public class GUI extends Application implements Loadable, Saveable, Serializable {
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private final static String FONT = "Helvetica";
    private final static Font btnFont = new Font(FONT, 16);

    private FantasyManager fantasyManager;
    private Label instructions = new Label();
    private TextField inputTeam;
    private Stage window;
    private Scene startPage;
    private Scene mainMenu;
    private Scene addTeamMenu;
    private Scene deleteTeamMenu;
    private Scene Standings;

    private ComboBox<Team> teamsCombo;

    private Button btnGetStarted;
    private Button btnAddTeam;
    private Button btnDeleteTeam;
    private Button btnViewStandings;
    private Button btnDraft;
    private Button btnAdvanceWeek;
    private Button btnSave;
    private Button btnQuit;
    private Button btnBack;
    private Button btnSubmitAddTeam;
    private Button btnSubmitDeleteTeam;

    public static void main(String[] args) {
        launch(args);
    }

    // TODO: Make sure correct buttons are faded even after loading from a saved state
    // Something like a *check button state* method run in the top of the constructor might be a good idea

    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Pucks In Deep");

        load();
        setupStartPage();
        window.setScene(startPage);

        setupMainMenu();
        setupBackButton();
        setupTeamComboBox();
        setupAddTeamMenu();
        setupDeleteTeamMenu();

        updateButtonAccess();
        window.show();
    }

    private void setupBackButton() {
        btnBack = new Button();
        btnBack.setText("Back");
        btnBack.setFont(btnFont);
        btnBack.setOnAction(e -> {
            returnToMainMenu();
        });
    }

    public void setupTeamComboBox() {
        teamsCombo = new ComboBox<>();
        updateActiveTeamsCombo();
        teamsCombo.setPromptText("Select the team to delete.");
        teamsCombo.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team object) {
                return object.getTeamName();
            }

            @Override
            public Team fromString(String string) {
                return null;
            }
        });
    }

    private void setupDeleteTeamMenu() {
        btnSubmitDeleteTeam = new Button();
        btnSubmitDeleteTeam.setText("Delete Team");
        btnSubmitDeleteTeam.setFont(btnFont);
        btnSubmitDeleteTeam.setOnAction(e -> {
            deleteUserGeneratedTeam();
        });

        VBox deleteTeamLayout = new VBox(10);
        deleteTeamLayout.setAlignment(Pos.CENTER);
        deleteTeamLayout.getChildren().addAll(teamsCombo, btnSubmitDeleteTeam, btnBack);

        deleteTeamMenu = new Scene(deleteTeamLayout, WIDTH, HEIGHT);
    }

    private void setupAddTeamMenu() {
        btnSubmitAddTeam = new Button();
        btnSubmitAddTeam.setText("Create Team");
        btnSubmitAddTeam.setFont(btnFont);
        btnSubmitAddTeam.setOnAction(e -> {
            createUserGeneratedTeam();
        });
        instructions = new Label();
        instructions.setFont(new Font(FONT, 20));

        inputTeam = new TextField();

        VBox addTeamLayout = new VBox(10);
        addTeamLayout.setAlignment(Pos.CENTER);
        addTeamLayout.getChildren().addAll(instructions, inputTeam, btnSubmitAddTeam, btnBack);

        addTeamMenu = new Scene(addTeamLayout, WIDTH, HEIGHT);
    }

    private void updateAddTeamInstructions() {
        if (atLeastOneTeam()) {
            instructions.setText("Please enter a unique team name." + "\n" + printActiveTeamNames());
        } else {
            instructions.setText("You're the first team in the league!" + "\n" +
                    "Please enter a unique team name.");
        }
    }

    private void returnToMainMenu() {
        updateButtonAccess();
        window.setScene(mainMenu);
    }

    private void updateButtonAccess() {
        btnAddTeam.setDisable(!teamCanBeAdded());
        btnDeleteTeam.setDisable(!teamCanBeRemoved());
        btnDraft.setDisable(!leagueCanDraft());
        btnAdvanceWeek.setDisable(!(leagueIsDrafted() && leagueCanAdvance()));
    }

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

    private boolean leagueCanAdvance() {
        try {
            fantasyManager.checkFantasyWeekHasFullyElapsed(fantasyManager.currentWeek());
            return true;
        } catch (InvalidFantasyWeekException e) {
            return false;
        }
    }

    private void createUserGeneratedTeam() {
        String teamName = String.valueOf(inputTeam.getText());

        try {
            Team team = new Team(teamName.toString());
            fantasyManager.getLeague().addTeam(team);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText("Success! " + teamName + " was added to the fantasy league.");
            alert.showAndWait();
            inputTeam.clear();
            returnToMainMenu();
        } catch (InvalidTeamException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Team Error");
            alert.setContentText(e.getMsg());
            alert.showAndWait();
        }
    }

    private void deleteUserGeneratedTeam() {
        Team team = teamsCombo.getValue();

        if (team == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Team Error");
            alert.setContentText("You must choose a team to delete.");
            alert.showAndWait();
        } else {
            fantasyManager.getLeague().removeTeam(team);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Dialog");
            alert.setContentText(team.getTeamName() + " was deleted from the fantasy league.");
            alert.showAndWait();
            returnToMainMenu();
        }
    }

    private void updateActiveTeamsCombo() {
        teamsCombo.getItems().clear();
        teamsCombo.getItems().addAll(fantasyManager.getLeague().getTeams());
    }


    private String printActiveTeamNames() {
        String teams = "Here is the list of teamsCombo: ";
        if (atLeastOneTeam()) {
            int i = 1;
            for (Team t : fantasyManager.getLeague().getTeams()) {
                String title = "\n" + "[" + i + "] - " + t.getTeamName();
                teams += title;
                i++;
            }
        } else {
            System.out.println("You're the first team in the league!");
        }
        return teams;
    }

    private void setupStartPage() {
        Label lblWelcome = new Label("Welcome to Fantasy NHL!");
        lblWelcome.setFont(new Font(FONT, 30));

        btnGetStarted = new Button();
        btnGetStarted.setText("Get Started");
        btnGetStarted.setFont(btnFont);
        btnGetStarted.setOnAction(e -> {
            returnToMainMenu();
        });

        VBox startLayout = new VBox(50);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.getChildren().addAll(lblWelcome, btnGetStarted);

        startPage = new Scene(startLayout, WIDTH, HEIGHT);
    }

    private void setupMainMenu() {
        btnAddTeam = new Button();
        btnAddTeam.setText("Add Team");
        btnAddTeam.setFont(btnFont);
        btnAddTeam.setDisable(!teamCanBeAdded());
        btnAddTeam.setOnAction(e -> {
            updateAddTeamInstructions();
            window.setScene(addTeamMenu);
        });

        btnDeleteTeam = new Button();
        btnDeleteTeam.setText("Delete Team");
        btnDeleteTeam.setFont(btnFont);
        btnDeleteTeam.setDisable(!teamCanBeRemoved());
        btnDeleteTeam.setOnAction(e -> {
            updateActiveTeamsCombo();
            window.setScene(deleteTeamMenu);
        });

        btnViewStandings = new Button();
        btnViewStandings.setText("View Teams");
        btnViewStandings.setFont(btnFont);
        btnViewStandings.setOnAction(e -> {

        });

        btnDraft = new Button();
        btnDraft.setText("Draft");
        btnDraft.setFont(btnFont);
        btnDraft.setDisable(!leagueCanDraft());
        btnDraft.setOnAction(e -> {

        });

        btnAdvanceWeek = new Button();
        btnAdvanceWeek.setText("Advance Week");
        btnAdvanceWeek.setFont(btnFont);
        btnAdvanceWeek.setDisable(!(leagueIsDrafted() && leagueCanAdvance()));
        btnAdvanceWeek.setOnAction(e -> {

        });

        btnSave = new Button();
        btnSave.setText("Save & Quit");
        btnSave.setFont(btnFont);
        btnSave.setOnAction(e -> {
            save();
            window.close();
        });

        btnQuit = new Button();
        btnQuit.setText("Quit");
        btnQuit.setFont(btnFont);
        btnQuit.setOnAction(e -> {
            window.close();
        });


        VBox mainMenuLayout = new VBox(10);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.getChildren().addAll(btnAddTeam, btnDeleteTeam, btnViewStandings, btnDraft, btnAdvanceWeek, btnSave, btnQuit);

        mainMenu = new Scene(mainMenuLayout, WIDTH, HEIGHT);
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
            loadNewLeague();
        } catch (IOException e) {
            loadNewLeague();
        } catch (ClassNotFoundException e) {
            loadNewLeague();
        }
    }

    private void loadNewLeague() {
        League league = new League();
        this.fantasyManager = new FantasyManager(league);
    }

}
