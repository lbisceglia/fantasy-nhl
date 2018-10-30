//package tests;
//
//import model.League.League;
//import model.Player.Player;
//import model.Team.Team;
//import model.exceptions.NoMatchException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.fail;
//
//public class TestLeague extends TestPlayer {
//
//    private Team baseTeam;
//    private Team roster;
//    private League baseLeague;
//    private League league;
//    private ArrayList<Player> players;
//    private ArrayList<Player> basePlayers;
//    private Map<Team, ArrayList<Player>> teamPlayerMap;
//    private Map<Team, ArrayList<Player>> baseMap;
//
//    @BeforeEach
//    public void setup() {
//
//        baseTeam = new Team("Base Team");
//        roster = new Team("Roster");
//
//        players = new ArrayList<>();
//        basePlayers = new ArrayList<>();
//
//        players.add(McDavid);
//        players.add(Ovechkin);
//        players.add(Hedman);
//        players.add(Josi);
//        players.add(Horvat);
//        players.add(Jones);
//
//        basePlayers.add(Boeser);
//
//        teamPlayerMap = new HashMap<>();
//        teamPlayerMap.put(roster, players);
//
//        baseMap = new HashMap<>();
//        baseMap.put(baseTeam, basePlayers);
//
//        league = new League(teamPlayerMap, basePlayers);
//
//        baseLeague = new League(baseMap, players);
//
////        try {
////            league.addTeam("Roster");
////        } catch (DuplicateMatchException e) {
////            e.printStackTrace();
////            System.out.println("The team already exists in the league.");
////        }
//    }
//
//    @Test
//    public void testAddPlayerToLeagueNoExceptions() {
//        try {
//            league.addPlayerToFantasyTeam("Roster", "Brock Boeser");
//            assertEquals(teamPlayerMap.size(), 1);
//            // Nothing required here
//        } catch (NoMatchException e) {
//            fail("No exception should be thrown -- player matches the team.");
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
////        assertEmpty(baseLeague.getTeams(), empty);
////    }
//
////    @Test
////    public void testGetTeamsSeveral() {
////        assertTrue(league.getTeams().contains(baseRoster));
////        assertTrue(league.getTeams().contains(roster));
////    }
//
//
//    }
