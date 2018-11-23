package ui;

import interfaces.Loadable;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import managers.FantasyManager;
import models.League;

import java.io.*;

public class GUI extends Application implements Loadable, Serializable {
    private final static int WIDTH = 500;
    private final static int HEIGHT = 500;
    private final static String FONT = "Helvetica";
    private final static Font btnFont = new Font(FONT, 16);

    private FantasyManager fantasyManager;
    private Label lblLoadComplete = new Label();
    private Stage window;
    private Scene startPage;
    private Scene mainMenu;
    private Scene addTeamMenu;
    private Scene deleteTeamMenu;
    private Scene viewTeams;
    private Scene leaderboard;


    private Button btnGetStarted;
    private Button btnAddTeam;
    private Button btnDeleteTeam;
    private Button btnViewTeams;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        window = primaryStage;
        window.setTitle("Pucks In Deep");

        load();
        setupStartPage();
        window.setScene(startPage);

        setupMainMenu();

        window.show();
    }

    private void setupStartPage() {
        Label lblWelcome = new Label("Welcome to Fantasy NHL!");
        lblWelcome.setFont(new Font(FONT, 30));

        btnGetStarted = new Button();
        btnGetStarted.setText("Get Started");
        btnGetStarted.setFont(btnFont);
        btnGetStarted.setOnAction(e -> {
            window.setScene(mainMenu);
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
        btnAddTeam.setOnAction(e -> {
            //TODO: add functionality
        });

        btnDeleteTeam = new Button();
        btnDeleteTeam.setText("Delete Team");
        btnDeleteTeam.setFont(btnFont);
        btnDeleteTeam.setDisable(true);
        btnDeleteTeam.setOnAction(e -> {

        });

        VBox mainMenuLayout = new VBox(10);
        mainMenuLayout.setAlignment(Pos.CENTER);
        mainMenuLayout.getChildren().addAll(btnAddTeam, btnDeleteTeam);

        mainMenu = new Scene(mainMenuLayout, WIDTH, HEIGHT);
    }

//    private void setupLoadingPage() {
//        Label lblLoading = new Label("Loading the league!" + "\n" + "This may take a moment...");
//        lblLoading.setFont(new Font("Arial", 20));
//        Label lblLoadComplete = new Label("");
//        btnEnterApp = new Button();
//        btnEnterApp.setText("Enter");
//        btnEnterApp.setDisable(true);
//        btnEnterApp.setOnAction(e -> window.setScene(mainMenu));
//
//        VBox loadingLayout = new VBox(20);
//        loadingLayout.setAlignment(Pos.CENTER);
//        loadingLayout.getChildren().add(lblLoading);
//        loadingLayout.getChildren().add(lblLoadComplete);
//
//        loadingPage = new Scene(loadingLayout, WIDTH, HEIGHT);
//    }

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
