package tests;

import model.Player.Skater;
import model.Team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestTeam extends TestPlayer{

    private Team team1;
    private Team baseRoster;
    private Team teamOfOne;
    private Team roster;
    private ArrayList<String> baseList;
    private Skater Boeser;
    private Skater Horvat;

    @BeforeEach
    public void setup() {
        super.setup();


        baseRoster = new Team("Base");
        teamOfOne = new Team("One");
        roster = new Team("Roster");

        baseList = new ArrayList<>();

        team1 = new Team("Lorenzo's Team");

        teamOfOne.addSkater(Horvat);

        roster.addSkater(McDavid);
        roster.addSkater(Boeser);
        roster.addSkater(Ovechkin);
        roster.addSkater(Hedman);
        roster.addSkater(Josi);
        roster.addGoalie(Jones);
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
        baseRoster.addSkater(Horvat);
        assertEquals(baseRoster.getSize(), 1);
    }

    @Test
    public void testAddPlayerSeveralValid() {
        assertEquals(roster.getSize(), 6);
        roster.addSkater(Horvat);
        assertEquals(roster.getSize(), 7);
    }

    @Test
    public void testAddPlayerSeveralInvalid() {
        assertEquals(roster.getSize(), 6);
        roster.addSkater(McDavid);
        assertEquals(roster.getSize(), 6);
    }

    @Test
    public void testRemovePlayerEmpty() {
        assertEquals(baseRoster.getSize(), 0);
        baseRoster.removeSkater(Horvat);
        assertEquals(baseRoster.getSize(), 0);
    }

    @Test
    public void testRemovePlayerSeveralValid() {
        assertEquals(roster.getSize(), 6);
        roster.removeSkater(McDavid);
        assertEquals(roster.getSize(), 5);
    }

    @Test
    public void testRemovePlayerSeveralInvalid() {
        assertEquals(roster.getSize(), 6);
        roster.removeSkater(Horvat);
        assertEquals(roster.getSize(), 6);
    }
}
