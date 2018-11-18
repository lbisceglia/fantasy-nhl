package tests;

import exceptions.InvalidTeamException;
import managers.DraftManager;
import models.Player;
import models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static models.League.loadAvailablePlayers;

public class TestDraftManager {
    Player Hedman;
    Player Josi;
    Set<Player> players;
    Team t1;
    Team t2;
    Team t3;
    Team t4;
    List<Team> teams;

    @BeforeEach
    public void setup() {
        Hedman = new Player(8475167, "Victor Hedman", Player.Position.D);
        Josi = new Player(8474600, "Roman Josi", Player.Position.D);
        players = loadAvailablePlayers();
        players.add(Hedman);
        players.add(Josi);
//        players.add(McDavid);
//        players.add(Ovechkin);
//        players.add(MacKinnon);
        try {
            t1 = new Team("team1");
            t2 = new Team("team2");
            t3 = new Team("team3");
            t4 = new Team("team4");

        } catch (InvalidTeamException e) {

        }
        teams = new ArrayList<>();
        teams.add(t1);
        teams.add(t2);
        teams.add(t3);
        teams.add(t4);
    }

    @Test
    public void testConstructor() {
            DraftManager d1 = new DraftManager(teams, players, DraftManager.DraftType.Regular);
    }
}
