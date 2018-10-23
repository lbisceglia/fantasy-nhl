package tests;

import model.League.League;
import model.Team.Team;
import model.exceptions.DuplicateMatchException;
import org.junit.jupiter.api.BeforeEach;

public class TestLeague {

    private Team baseSkaters;
    private Team baseGoalies;
    private Team roster;
    private League baseLeague;
    private League league;

    @BeforeEach
    public void setup() {
        baseSkaters = new Team("Base Skaters");
        baseGoalies = new Team("Base Goalies");

        roster = new Team("Roster");

        baseLeague = new League(baseSkaters, baseGoalies);
        league = new League(baseSkaters, baseGoalies);

        try {
            league.addTeam("Base Roster");
            league.addTeam("Roster");
        } catch (DuplicateMatchException e) {
            e.printStackTrace();
            System.out.println("The team already exists in the league.");
        }
    }

//    @Test
//    public void testGetTeamsEmpty() {
//        assertEmpty(baseLeague.getTeams(), empty);
//    }

//    @Test
//    public void testGetTeamsSeveral() {
//        assertTrue(league.getTeams().contains(baseRoster));
//        assertTrue(league.getTeams().contains(roster));
//    }


}
