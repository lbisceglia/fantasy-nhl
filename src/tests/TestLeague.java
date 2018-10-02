package tests;

import model.League.League;
import model.Team.Team;
import org.junit.jupiter.api.BeforeEach;

public class TestLeague {

    private Team baseRoster;
    private Team roster;
    private League baseLeague;
    private League league;

    @BeforeEach
    public void setup() {
        baseRoster = new Team("Base Roster");
        roster = new Team("Roster");

        baseLeague = new League(baseRoster);
        league = new League(baseRoster);

        league.addTeam("Base Roster");
        league.addTeam("Roster");
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
