package tests;

import model.Player.Goalie;
import model.Player.Position;
import model.Player.Skater;
import model.Team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTeam {

    private Team team1;
    private Team baseRoster;
    private Team teamOfOne;
    private Team roster;
    private ArrayList<String> baseList;
    private Skater McDavid;
    private Skater Boeser;
    private Skater Ovechkin;
    private Skater Hedman;
    private Skater Josi;
    private Goalie Jones;
    private Skater Horvat;

    @BeforeEach
    public void setup() {
        baseRoster = new Team("Base");
        teamOfOne = new Team("One");
        roster = new Team("Roster");

        baseList = new ArrayList<String>();

        team1 = new Team("Lorenzo's Team");

        McDavid = new Skater("Connor McDavid", Position.C, 0, 0 ,41, 67);
        Boeser = new Skater("Brock Boeser", Position.RW, 0, 0, 29, 26);
        Ovechkin = new Skater("Alexander Ovechkin", Position.LW, 0, 0, 49, 38);
        Hedman = new Skater("Victor Hedman", Position.D, 0, 0, 17, 46);
        Josi = new Skater("Roman Josi", Position.D, 0,0, 14, 39);
        Jones = new Goalie("Martin Jones", 0, 0, 0, 0);
        Horvat = new Skater("Bo Horvat", Position.C, 0, 0, 22, 22);

        teamOfOne.addPlayer(Horvat);

        roster.addPlayer(McDavid);
        roster.addPlayer(Boeser);
        roster.addPlayer(Ovechkin);
        roster.addPlayer(Hedman);
        roster.addPlayer(Josi);
        roster.addPlayer(Jones);
    }

    @Test
    public void testGetTeamName() {
        assertEquals(team1.getTeamName(), "Lorenzo's Team");
    }

    @Test
    public void testGetSizeEmpty() {
        assertEquals(baseRoster.getSize(), 0);
    }

    @Test
    public void testGetSizeSeveral() {
        assertEquals(roster.getSize(), 6);
    }

    @Test
    public void testGetPlayersEmpty() {
        assertFalse(baseRoster.getPlayers().contains(McDavid));
        assertFalse(baseRoster.getPlayers().contains(Boeser));
        assertFalse(baseRoster.getPlayers().contains(Ovechkin));
        assertFalse(baseRoster.getPlayers().contains(Hedman));
        assertFalse(baseRoster.getPlayers().contains(Josi));
        assertFalse(baseRoster.getPlayers().contains(Jones));
        assertFalse(baseRoster.getPlayers().contains(Horvat));
    }

    @Test
    public void testGetPlayersSeveral() {
        assertTrue(roster.getPlayers().contains(McDavid));
        assertTrue(roster.getPlayers().contains(Boeser));
        assertTrue(roster.getPlayers().contains(Ovechkin));
        assertTrue(roster.getPlayers().contains(Hedman));
        assertTrue(roster.getPlayers().contains(Josi));
        assertTrue(roster.getPlayers().contains(Jones));
        assertFalse(roster.getPlayers().contains(Horvat));
    }

    @Test
    public void testGetPlayerNamesEmpty() {
        assertEquals(baseRoster.getPlayerNames(), baseList);
    }

    @Test
    public void testGetPlayerNamesSeveral() {
        assertTrue(roster.getPlayerNames().contains("Connor McDavid"));
        assertTrue(roster.getPlayerNames().contains("Brock Boeser"));
        assertTrue(roster.getPlayerNames().contains("Alexander Ovechkin"));
        assertTrue(roster.getPlayerNames().contains("Victor Hedman"));
        assertTrue(roster.getPlayerNames().contains("Roman Josi"));
        assertTrue(roster.getPlayerNames().contains("Martin Jones"));
        assertFalse(roster.getPlayerNames().contains("Bo Horvat"));
    }

    @Test
    public void testContainsPlayerEmptyList() {
        assertFalse(baseRoster.containsPlayer(McDavid));
    }

    @Test
    public void testContainsPlayerNoMatch() {
        assertFalse(roster.containsPlayer(Horvat));
    }

    @Test
    public void testContainsPlayerMatch() {
        assertTrue(roster.containsPlayer(Boeser));
    }

    @Test
    public void testContainsPlayerNameEmpty() {
        assertFalse(baseRoster.containsPlayerName("Connor McDavid"));
    }

    @Test
    public void testContainsPlayerNameNoMatch() {
        assertFalse(roster.containsPlayerName("Bo Horvat"));
    }

    @Test
    public void testContainsPlayerNameMatch() {
        assertTrue(roster.containsPlayerName("Brock Boeser"));
    }

    @Test
    public void testPlayerLookupEmpty() {
        assertEquals(baseRoster.playerLookup("Connor McDavid"), null);
    }

    @Test
    public void testPlayerLookupNoMatch() {
        assertEquals(roster.playerLookup("Bo Horvat"), null);
    }

    @Test
    public void testPlayerLookupMatch() {
        assertEquals(roster.playerLookup("Brock Boeser"), Boeser);
    }

    //TODO: Verify that this is this how you properly test add/remove methods
    @Test
    public void testAddPlayerEmpty() {
        assertEquals(baseRoster.getSize(), 0);
        baseRoster.addPlayer(Horvat);
        assertEquals(baseRoster.getSize(), 1);
    }

    @Test
    public void testAddPlayerSeveralValid() {
        assertEquals(roster.getSize(), 6);
        roster.addPlayer(Horvat);
        assertEquals(roster.getSize(), 7);
    }

    @Test
    public void testAddPlayerSeveralInvalid() {
        assertEquals(roster.getSize(), 6);
        roster.addPlayer(McDavid);
        assertEquals(roster.getSize(), 6);
    }

    @Test
    public void testRemovePlayerEmpty() {
        assertEquals(baseRoster.getSize(), 0);
        baseRoster.removePlayer(Horvat);
        assertEquals(baseRoster.getSize(), 0);
    }

    @Test
    public void testRemovePlayerSeveralValid() {
        assertEquals(roster.getSize(), 6);
        roster.removePlayer(McDavid);
        assertEquals(roster.getSize(), 5);
    }

    @Test
    public void testRemovePlayerSeveralInvalid() {
        assertEquals(roster.getSize(), 6);
        roster.removePlayer(Horvat);
        assertEquals(roster.getSize(), 6);
    }

    //    @Test
//    public void testGetTotalTeamGoalsEmpty() {
//        assertEquals(baseRoster.getTotalTeamGoals(), 0);
//    }
//
//    @Test
//    public void testGetTotalTeamGoalsZero() {
//        baseRoster.addPlayer(Jones);
//        assertEquals(baseRoster.getTotalTeamGoals(), 0);
//    }
//
//    @Test
//    public void testGetTotalTeamGoalsSeveral() {
//        assertEquals(roster.getTotalTeamGoals(), 150);
//    }
//
//    @Test
//    public void testGetTotalTeamAssistsEmpty() {
//        assertEquals(baseRoster.getTotalTeamAssists(), 0);
//    }
//
//    @Test
//    public void testGetTotalTeamAssistsZero() {
//        baseRoster.addPlayer(Jones);
//        assertEquals(baseRoster.getTotalTeamAssists(), 0);
//    }
//
//    @Test
//    public void testGetTotalTeamAssistsSeveral() {
//        assertEquals(roster.getTotalTeamAssists(), 216);
//    }
}
