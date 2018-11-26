package ui;

import exceptions.ImpossibleDraftException;
import exceptions.InvalidDraftChoiceException;
import exceptions.InvalidFantasyWeekException;
import exceptions.InvalidTeamException;
import interfaces.Loadable;
import interfaces.Saveable;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.StringConverter;
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

import static javafx.scene.text.TextAlignment.CENTER;
import static javafx.scene.text.TextAlignment.LEFT;
import static managers.DraftManager.DraftType.*;
import static managers.FantasyManager.FANTASY_WEEKS;
import static models.League.MAX_PARTICIPANTS;
import static models.League.MIN_PARTICIPANTS;

public class GUI extends Application implements Loadable, Saveable, Serializable {
    private final static int WIDTH = 500;
    private final static int HEIGHT = 650;
    private final static String FONT = "Helvetica";
    private final static Font btnFont = new Font(FONT, 14);

    private List<Team> draftOrder = new ArrayList<>();
    private int currentDraftPosition = 0;

    private FantasyManager fantasyManager;
    private Label weeksLabel = new Label();
    private Label instructions = new Label();
    private Label selectionLabel = new Label();
    private Label playersNeededLabel = new Label();

    private TextField inputTeam;
    private ScrollPane standingScroll;

    private Stage window;

    private Scene startPage;
    private Scene mainMenu;
    private Scene addTeamMenu;
    private Scene deleteTeamMenu;
    private Scene draftMenu;
    private Scene standingsMenu;

    private ComboBox<Team> teamsCombo;
    private ComboBox<Player> playersCombo;

    private Button btnGetStarted;
    private Button btnAddTeam;
    private Button btnDeleteTeam;
    private Button btnViewStandings;
    private Button btnDraft;
    private Button btnAdvanceWeek;
    private Button btnSave;
    private Button btnQuit;

    private Button btnBackAddTeam;
    private Button btnBackDeleteTeam;
    private Button btnBackStandings;

    private Button btnSubmitAddTeam;
    private Button btnSubmitDeleteTeam;
    private Button btnSubmitDraft;

    public static void main(String[] args) {
        launch(args);
    }

    //Dialogs modelled after: https://code.makery.ch/blog/javafx-dialogs-official/

    // TODO: LOAD ALL PLAYER STATS RIGHT WHEN THE APP IS LAUNCHED

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
        setupTeamComboBox();
        setupAddTeamMenu();
        setupDeleteTeamMenu();
        setupDraftMenu();
        setupStandingsMenu();

        updateButtonAccess();
        window.show();
    }

    private void setupBackButton(Button btn) {
        btn.setText("Back");
        btn.setFont(btnFont);
        btn.setOnAction(e -> {
            returnToMainMenu();
        });
    }

    public void setupTeamComboBox() {
        teamsCombo = new ComboBox<>();
        updateActiveTeamsCombo();
        teamsCombo.setPromptText("Select the team to delete");
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

    private void setupPlayerComboBox() {
        playersCombo = new ComboBox<>();
        updateActivePlayersCombo();
        playersCombo.setPromptText("Select the player to draft!");
        playersCombo.setConverter(new StringConverter<Player>() {
            @Override
            public String toString(Player object) {
                return object.getPlayerName() + " (" + object.getPosition() + ")";
            }

            @Override
            public Player fromString(String string) {
                return null;
            }
        });
    }

    private VBox displayAllTeams() {
        VBox teams = new VBox(30);
        for (Team t : fantasyManager.getLeague().getTeams()) {
            teams.getChildren().add(displayRoster(t));
        }
        return teams;
    }

    private VBox displayRoster(Team t) {

//        Label team = new Label();
//        team.setText(t.getTeamName());
//        team.setFont(new Font(FONT, 20));

        VBox roster = new VBox(10);
        roster.setAlignment(Pos.CENTER);
//        roster.getChildren().addAll(team);

        for (Player p : t.getPlayers()) {
            HBox player = displayPlayer(p);
            roster.getChildren().add(player);
        }

        return roster;
    }

    // Modelled after https://stackoverflow.com/questions/24463064/javafx8-vbox-center-image
    private HBox displayPlayer(Player p) {
        String fileName = "C:\\Users\\Lorenzo Bisceglia\\Google Drive\\1 - School\\1 - BCS\\CPSC 210\\Project\\projectw1_team29\\playerPhotos\\" + p.getPlayerID() + ".jpg";

        HBox record = new HBox(15);
        record.setAlignment(Pos.CENTER_LEFT);

        String pts = new DecimalFormat("#.#").format(p.calculateOverallFantasyPoints(fantasyManager.currentWeek()-1));

        String title = p.getPlayerName() + " (" + p.getPosition() + ")" + "\n" + pts + " Fantasy Points";
        Label player = new Label();
        player.setText(title);
        player.setFont(new Font(FONT, 18));

        try {
            Image img = new Image(new FileInputStream(fileName));
            ImageView imageView = new ImageView(img);
            imageView.setPreserveRatio(true);
            int radius = 72;

            Circle circle = new Circle(radius);
            circle.setFill(new ImagePattern(img));

//            imageView.autosize();
//            imageView.setFitHeight(height);
            record.getChildren().addAll(circle);
        } catch (FileNotFoundException e) {

        }

        record.getChildren().addAll(player);
        return record;
    }

    private void setupDraftMenu() {

        setupPlayerComboBox();

        btnSubmitDraft = new Button();
        btnSubmitDraft.setText("Draft Player");
        btnSubmitDraft.setFont(btnFont);
        btnSubmitDraft.setOnAction(e -> {
             selectPlayer();
            if (currentDraftPosition >= draftOrder.size()) {
                concludeFantasyDraft();
            } else {
                updateDraftMenu();
            }
        });

        Label draftLabel = new Label();
        draftLabel.setText("Fantasy Draft 2018-2019");
        draftLabel.setFont(new Font(FONT, 30));

        VBox draftLayout = new VBox(10);
        draftLayout.setAlignment(Pos.CENTER);
        draftLayout.getChildren().addAll(draftLabel, selectionLabel, playersNeededLabel, playersCombo, btnSubmitDraft);

        draftMenu = new Scene(draftLayout, WIDTH, HEIGHT);
    }

    private void setupStandingsMenu() {
        Font labelFont = new Font(FONT, 16);

        btnBackStandings = new Button();
        setupBackButton(btnBackStandings);

        Label standingsLabel = new Label();
        standingsLabel.setText("Standings");
        standingsLabel.setFont(new Font(FONT, 30));
        standingsLabel.setTextAlignment(CENTER);

        Label rosterLabel = new Label();
        rosterLabel.setText("Roster");
        rosterLabel.setFont(new Font(FONT, 30));
        rosterLabel.setTextAlignment(CENTER);

        Label overallStandingsLabel = new Label();
        overallStandingsLabel.setText("Overall" + displayOverallLeaders());
        overallStandingsLabel.setFont(labelFont);
        overallStandingsLabel.setTextAlignment(LEFT);

        Label weeklyStandingsLabel = new Label();
        weeklyStandingsLabel.setText("Last Week (Week " + (fantasyManager.currentWeek() - 1) + ")" + displayThisWeeksLeaders());
        weeklyStandingsLabel.setFont(labelFont);
        weeklyStandingsLabel.setTextAlignment(LEFT);

        ComboBox<Team> teamStandingCB = new ComboBox<>();
        teamStandingCB.setPromptText("Select the team to view.");
        teamStandingCB.getItems().addAll(fantasyManager.getLeague().getTeams());
        teamStandingCB.setConverter(new StringConverter<Team>() {
            @Override
            public String toString(Team object) {
                return object.getTeamName();
            }

            @Override
            public Team fromString(String string) {
                return null;
            }
        });

        teamStandingCB.setOnAction(e-> {
            Team t = teamStandingCB.getValue();
            standingScroll.setContent(displayRoster(t));
            window.setScene(standingsMenu);
        });

        standingScroll = new ScrollPane(new VBox());
        standingScroll.setPrefViewportHeight(HEIGHT/3);

        HBox rankingsLayout = new HBox(50);
        rankingsLayout.setAlignment(Pos.CENTER);
        rankingsLayout.getChildren().addAll(overallStandingsLabel, weeklyStandingsLabel);

        VBox standingsLayout = new VBox(10);
        standingsLayout.setAlignment(Pos.CENTER);
        standingsLayout.getChildren().addAll(standingsLabel, rankingsLayout, rosterLabel, teamStandingCB, standingScroll, btnBackStandings);

        standingsMenu = new Scene(standingsLayout, WIDTH, HEIGHT);
    }

    private String displayOverallLeaders() {
        List<Team> teams = fantasyManager.getLeague().getTeams();
        //Modelled after Stack Overflow post: https://stackoverflow.com/questions/19471005/sorting-an-arraylist-of-objects-alphabetically
        Collections.sort(teams, Comparator.comparing(Team::getOverallFantasyPoints).reversed());
        return printOverallLeaders(teams);
    }

    private String displayThisWeeksLeaders() {
        List<Team> teams = fantasyManager.getLeague().getTeams();
        //Modelled after Stack Overflow post: https://stackoverflow.com/questions/19471005/sorting-an-arraylist-of-objects-alphabetically
        Collections.sort(teams, Comparator.comparing(Team::getCurrentWeekFantasyPoints).reversed());
        return printWeekLeaders(teams);
    }


    private String printOverallLeaders(List<Team> teams) {
        String overall = "";
        double previous = 0;
        int i = 1;
        for (Team t : teams) {
            if (t.getOverallFantasyPoints() < previous) {
                i++;
            }
            String pts = new DecimalFormat("#.#").format(t.getOverallFantasyPoints());
            String team = i + " - " + t.getTeamName() + " (" + pts + " points)";
            previous = t.getOverallFantasyPoints();
            overall += "\n" + team;
        }
        return overall;
    }


    private String printWeekLeaders(List<Team> teams) {
        String week = "";
        double previous = 0;
        int i = 1;
        for (Team t : teams) {
            if (t.getCurrentWeekFantasyPoints() < previous) {
                i++;
            }
            String pts = new DecimalFormat("#.#").format(t.getCurrentWeekFantasyPoints());
            String team = i + " - " + t.getTeamName() + " (" + pts + " points)";
            previous = t.getCurrentWeekFantasyPoints();
            week += "\n" + team;
        }
        return week;
    }

    private void updateStandingsMenu() {
        //TODO: FILL THIS IN
    }


    private void concludeFantasyDraft() {
        fantasyManager.setDrafted();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fantasy Draft");
        if (fantasyManager.getDraftManager().getDraftType().equals(AutoDraft)) {
            alert.setContentText("That concludes the fantasy AutoDraft! Please click the 'Standings' button to see your team. Good luck to all players!");
        } else {
            alert.setContentText("That concludes the fantasy draft! Good luck to all players!");
        }
        alert.showAndWait();
        try {
            fantasyManager.advanceFantasyWeekByOne();
        } catch (InvalidFantasyWeekException e) {
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setTitle("Advance Week Error");
            error.setContentText("You cannot advance to that fantasy week.");
            error.showAndWait();
        }
        returnToMainMenu();
    }

    private void selectDraftTypeAndBeginDraft() {
        List<DraftManager.DraftType> choices = new ArrayList<>();
        choices.add(AutoDraft);
        choices.add(Regular);
        choices.add(Snake);

        ChoiceDialog<DraftManager.DraftType> dialog = new ChoiceDialog<>(AutoDraft, choices);
        dialog.setHeaderText("Draft Type");
        dialog.setContentText("Choose the type of draft you want to have:");

        DraftManager.DraftType result = dialog.showAndWait().orElse(null);
        if (!(result == null)) {
            fantasyManager.getDraftManager().setDraftType(result);
            beginDraft();
        }
    }

    private void selectPlayer() {

        // TODO: Make this a generic select method that works for AutoDraft too
        Player player = playersCombo.getValue();

        if (player == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Draft Choice Error");
            alert.setContentText("You must choose a valid player to draft.");
            alert.showAndWait();
        } else {
            try {
                Team team = draftOrder.get(currentDraftPosition);

                fantasyManager.getDraftManager().getDraftValidator().checkDraftChoiceIsValid(team, player);
                addPlayerToFantasyTeam(team, player);
                updateDraftMenu();

                currentDraftPosition++;

            } catch (InvalidDraftChoiceException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Draft Choice Error");
                alert.setContentText("You cannot choose another player of that position.");
                alert.showAndWait();
            }
        }
    }

    private void updateDraftMenu() {
        updateActivePlayersCombo();
        updateDraftLabels();
    }

    private void updateDraftLabels() {
        Team team = draftOrder.get(currentDraftPosition);
        selectionLabel.setText("Selection " + (currentDraftPosition + 1) + " of " + draftOrder.size() + ": " + team.getTeamName());
        selectionLabel.setFont(new Font(FONT, 24));
        selectionLabel.setTextAlignment(CENTER);

        playersNeededLabel.setText(fantasyManager.getDraftManager().getDraftValidator().playersNeededByPosition(team));
        playersNeededLabel.setFont(new Font(FONT, 12));
        selectionLabel.setTextAlignment(LEFT);
    }


    private void addPlayerToFantasyTeam(Team team, Player player) {
        team.addPlayer(player);
        fantasyManager.getLeague().getAvailablePlayers().remove(player);
        try {
            fantasyManager.addPlayerStats(player, team);
        } catch (IOException e){

        }
//        fantasyManager.getLeague().getTeams().get
        // TODO: ADD THE PLAYER'S STATS TO THE TEAM
        fantasyManager.getLeague().getAvailablePlayers().remove(player);

        String msg = "Success! " + player.getPlayerName() + " was added to " + team.getTeamName() + ".";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setContentText(msg);
        alert.showAndWait();
    }


    private void beginDraft() {
        try {
            draftOrder = fantasyManager.selectDraftOrder();
            announceDraftOrder(draftOrder);
            updateDraftMenu();

            if (fantasyManager.getDraftManager().getDraftType().equals(DraftManager.DraftType.AutoDraft)) {
                fantasyManager.getDraftManager().autoDraft(draftOrder, fantasyManager);
                concludeFantasyDraft();
            } else {
                window.setScene(draftMenu);
            }

        } catch (ImpossibleDraftException e) {
            returnToMainMenu();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Fantasy Draft");
            alert.setContentText(e.getMsg());
            alert.showAndWait();
        }
    }


    private void setupDeleteTeamMenu() {
        btnBackDeleteTeam = new Button();
        setupBackButton(btnBackDeleteTeam);

        btnSubmitDeleteTeam = new Button();
        btnSubmitDeleteTeam.setText("Delete Team");
        btnSubmitDeleteTeam.setFont(btnFont);
        btnSubmitDeleteTeam.setOnAction(e -> {
            deleteUserGeneratedTeam();
        });

        VBox deleteTeamLayout = new VBox(10);
        deleteTeamLayout.setAlignment(Pos.CENTER);
        deleteTeamLayout.getChildren().addAll(teamsCombo, btnSubmitDeleteTeam, btnBackDeleteTeam);

        deleteTeamMenu = new Scene(deleteTeamLayout, WIDTH, HEIGHT);
    }

    private void setupAddTeamMenu() {
        btnBackAddTeam = new Button();
        setupBackButton(btnBackAddTeam);

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
        addTeamLayout.getChildren().addAll(instructions, inputTeam, btnSubmitAddTeam, btnBackAddTeam);

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
        updateWeekLabel();
        window.setScene(mainMenu);
    }

    private void updateButtonAccess() {
        btnAddTeam.setDisable(!teamCanBeAdded());
        btnDeleteTeam.setDisable(!teamCanBeRemoved());
        btnDraft.setDisable(!leagueCanDraft());
        btnAdvanceWeek.setDisable(!(leagueIsDrafted() && leagueCanAdvance()));
        btnViewStandings.setDisable(!leagueIsDrafted());
    }

    private boolean atLeastOneTeam() {
        return fantasyManager.size() > 0;
    }

    private boolean teamCanBeAdded() {
        return fantasyManager.size() < MAX_PARTICIPANTS;
    }

    private boolean moreTeamsNeeded() {
        return (fantasyManager.size() < MIN_PARTICIPANTS && !fantasyManager.isDrafted());
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
            Optional<ButtonType> buttonType = alert.showAndWait();
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

    public void updatePlayersOnWeek() {
        String update = "";
        for (Team t : fantasyManager.getLeague().getTeams()) {
            double points = t.getCurrentWeekFantasyPoints();
            String pts = new DecimalFormat("#.##").format(points);
            update += "\n" + t.getTeamName() + " has earned " + pts + " fantasy points this week.";
        }
        String header = "The results for week " + fantasyManager.currentWeek() + " are in!" + "\n";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Fantasy Draft");
        alert.setContentText(header + update);
        alert.showAndWait();
    }

    private void updateLeague() {
        if (fantasyManager.currentWeek() < FANTASY_WEEKS) {
            try {

//                fantasyManager.advanceFantasyWeekByOne();

                for (Team t : fantasyManager.getLeague().getTeams()) {
                    t.updateCurrentWeekFantasyPoints(fantasyManager.currentWeek());
                    t.updateOverallFantasyPoints(fantasyManager.currentWeek());
                }

                updatePlayersOnWeek();

                fantasyManager.advanceFantasyWeekByOne();

            } catch (InvalidFantasyWeekException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Fantasy Week Error");
                alert.setContentText("You cannot advance to that fantasy week.");
                alert.showAndWait();
            }
        }
    }

    private void announceDraftOrder(List<Team> draftList) {
        String order = formatDraftOrder(draftList);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Draft Lottery Results");
        alert.setContentText("The draft lottery results are in!" + "\n" +
                "The random draft order is..." + order);
        alert.showAndWait();
    }

    private String formatDraftOrder(List<Team> draftList) {
        int size = fantasyManager.size();
        String order = "";
        for (int i = 1; i <= size; i++) {
            Team t = draftList.get(i - 1);
            order += "\n" + i + ": " + t.getTeamName();
        }
        return order;
    }

    private void updateActiveTeamsCombo() {
        teamsCombo.getItems().clear();
        teamsCombo.getItems().addAll(fantasyManager.getLeague().getTeams());
        List<Team> teams = fantasyManager.getLeague().getTeams();
    }

    private void updateActivePlayersCombo() {
        playersCombo.getItems().clear();
        List<Player> players = new ArrayList<>();
        players = convertPlayerSetToList(fantasyManager.getLeague().getAvailablePlayers());
        playersCombo.getItems().addAll(players);
    }

    private List<Player> convertPlayerSetToList(Set<Player> players) {
        List<Player> list = new ArrayList<>();

        for (Player p : players) {
            list.add(p);
        }
        return list;
    }


    private String printActiveTeamNames() {
        String teams = "Here is the list of teams: ";
        if (atLeastOneTeam()) {
            int i = 1;
            for (Team t : fantasyManager.getLeague().getTeams()) {
                String title = "\n" + t.getTeamName();
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

        VBox startLayout = new VBox(40);
        startLayout.setAlignment(Pos.CENTER);
        startLayout.getChildren().addAll(lblWelcome, btnGetStarted);

        startPage = new Scene(startLayout, WIDTH, HEIGHT);
    }

    private void updateWeekLabel() {
        String week = "Fantasy Week " + fantasyManager.currentWeek() + " of " + FANTASY_WEEKS;
        String dates = "";
        String requirement = "";
        if (fantasyManager.currentWeek() > 0) {
            dates = "\n" + getWeeksFantasyDates(fantasyManager.currentWeek());
        }
        if (moreTeamsNeeded()) {
            requirement = "\n" + "Please create " + (MIN_PARTICIPANTS - fantasyManager.size()) + " more team(s) in order to draft.";
        }
        weeksLabel.setText(week + dates + requirement);
        weeksLabel.setFont(new Font(FONT, 16));
        weeksLabel.setTextAlignment(CENTER);
    }

    private String getWeeksFantasyDates(int week) {
        List<LocalDate> dates = fantasyManager.getWeekCutoffs();
        LocalDate startDate = dates.get(week - 1).plusDays(1);
        LocalDate endDate = dates.get(week);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM yyyy");
        String start = startDate.format(formatter);
        String end = endDate.format(formatter);

        String dateRange = "(" + start + " - " + end + ")";
        return dateRange;
    }

    private void setupMainMenu() {
        updateWeekLabel();

        Label mainLabel = new Label();
        mainLabel.setText("Fantasy NHL  2018-19");
        mainLabel.setFont(new Font(FONT, 30));

        btnAddTeam = new Button();
        btnAddTeam.setText("Add Team");
        btnAddTeam.setFont(btnFont);
        btnAddTeam.setDisable(!teamCanBeAdded());
        btnAddTeam.setOnAction(e -> {
            updateAddTeamInstructions();
            inputTeam.requestFocus();
            window.setScene(addTeamMenu);
        });

        btnDeleteTeam = new Button();
        btnDeleteTeam.setText("Delete Team");
        btnDeleteTeam.setFont(btnFont);
        btnDeleteTeam.setDisable(!teamCanBeRemoved());
        btnDeleteTeam.setOnAction(e -> {
            updateActiveTeamsCombo();
            teamsCombo.requestFocus();
            window.setScene(deleteTeamMenu);
        });

        btnViewStandings = new Button();
        btnViewStandings.setText("Standings");
        btnViewStandings.setFont(btnFont);
        btnViewStandings.setDisable(!leagueIsDrafted());
        btnViewStandings.setOnAction(e -> {
            setupStandingsMenu();
            window.setScene(standingsMenu);
        });

        btnDraft = new Button();
        btnDraft.setText("Draft");
        btnDraft.setFont(btnFont);
        btnDraft.setDisable(!leagueCanDraft());
        btnDraft.setOnAction(e -> {
            playersCombo.requestFocus();
            selectDraftTypeAndBeginDraft();
        });

        btnAdvanceWeek = new Button();
        btnAdvanceWeek.setText("Advance Week");
        btnAdvanceWeek.setFont(btnFont);
        btnAdvanceWeek.setDisable(!(leagueIsDrafted() && leagueCanAdvance()));
        btnAdvanceWeek.setOnAction(e -> {
            updateLeague();
            updateButtonAccess();
            updateWeekLabel();
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
        mainMenuLayout.getChildren().addAll(mainLabel, weeksLabel, btnAddTeam, btnDeleteTeam, btnViewStandings, btnDraft, btnAdvanceWeek, btnSave, btnQuit);

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
