package tests;

import models.League;
import models.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static models.League.loadAvailablePlayers;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestLeague {
    private Set<Player> availablePlayers;
    private League league;

    @BeforeEach
    public void setup() {
        availablePlayers = loadAvailablePlayers();
        league = new League(availablePlayers);
//
//        baseTeam = new team("Base team");
//        roster = new team("Roster");
//
//        basePlayers = new ArrayList<>();
//
//        basePlayers.add(Boeser);
//
//        teamPlayerMap = new HashMap<>();
//        teamPlayerMap.put(roster, players);
//
//        baseMap = new HashMap<>();
//        baseMap.put(baseTeam, basePlayers);
//
//        league = new league(teamPlayerMap, basePlayers);
//
//        baseLeague = new league(baseMap, players);
    }



//        try {
//            league.addTeam("Roster");
//        } catch (DuplicateMatchException e) {
//            e.printStackTrace();
//            System.out.println("The team1 already exists in the league.");
//        }

    @Test
    public void testGetWeeklyGameIDs() {
        LocalDate startDate = LocalDate.of(2018,1,2);
        LocalDate endDate = LocalDate.of(2018,1,9);
        List<Integer> gameIDs = league.getWeeklyGameIDs(startDate, endDate);
        assertEquals(gameIDs.size(), 57);
    }

    }
//
//    @Test
//    public void testAddPlayerToLeagueNoExceptions() {
//        try {
//            league.addPlayerToFantasyTeam("Roster", "Brock Boeser");
//            assertEquals(teamPlayerMap.size(), 1);
//            // Nothing required here
//        } catch (NoMatchException e) {
//            fail("No exception should be thrown -- player matches the team1.");
//        }
//    }
//
//    @Test
//    public void testAddPlayerToLeagueNoMatchException() {
//        try {
//            league.addPlayerToFantasyTeam("Roster", "Connor McDavid");
//            fail("No Match Exception should be thrown.");
//        } catch (NoMatchException e) {
//            // Nothing required here
//        }
//    }
//
//
////    @Test
////    public void testGetTeamsEmpty() {
////        assertEmpty(baseLeague.getTeamMap(), empty);
////    }
//
////    @Test
////    public void testGetTeamsSeveral() {
////        assertTrue(league.getTeamMap().contains(baseRoster));
////        assertTrue(league.getTeamMap().contains(roster));
////    }
//
//
//    }
