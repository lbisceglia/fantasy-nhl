package tests;

import model.Player.Player;
import model.Team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestTeam {
    private Team team1;
    private Team team2;
    private Team team1a;
    private Player McDavid;
    private Player Ovechkin;

    @BeforeEach
    public void setup() {
        team1 = new Team("team1");
        team2 = new Team("team2");
        team1a = new Team("team1");
        McDavid = new Player(8478402, "Connor McDavid", Player.Position.C);
        Ovechkin = new Player(8471214, "Alex Ovechkin", Player.Position.LW);
    }

    @Test
    public void testConstructor() {
        assertEquals(team1.getTeamName(), "team1");
        assertEquals(team1.getFantasyPoints(), 0);
//        assertEquals(team1.getPlayerManager().getTeam(), team1);
        assertEquals(team1.getPlayers(), new HashSet<>());
//        assertEquals(team1.getLeague(), null);
    }

    @Test
    public void testSetTeamNameValidString() {
        team1.setTeamName("New Team Name");
        assertEquals(team1.getTeamName(), "New Team Name");
    }

    @Test
    public void testSetTeamNameInvalidString() {
        team1.setTeamName("");
        assertEquals(team1.getTeamName(), "team1");
    }

    @Test
    public void testAddPlayerToEmptyTeam() {
        team1.addPlayer(McDavid);
        assertTrue(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 1);
    }

    @Test
    public void testAddSamePlayerTwice() {
        team1.addPlayer(McDavid);
        team1.addPlayer(McDavid);
        assertTrue(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 1);
    }

    @Test
    public void testAddTwoPlayers() {
        team1.addPlayer(McDavid);
        team1.addPlayer(Ovechkin);

        assertTrue(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 2);
    }

    @Test
    public void testAddSamePlayerToDifferentTeams() {
        team1.addPlayer(McDavid);
        team2.addPlayer(McDavid);

        assertFalse(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 0);

        assertTrue(team2.getPlayers().contains(McDavid));
        assertEquals(team2.getPlayers().size(), 1);
    }

    @Test
    public void testRemoveFromEmptyTeam() {
        team1.removePlayer(McDavid);

        assertFalse(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 0);
    }

    @Test
    public void testRemoveFromTeamNoMatch() {
        team1.addPlayer(Ovechkin);
        team1.removePlayer(McDavid);

        assertFalse(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 1);
    }

    @Test
    public void testRemoveFromTeamWithMatch() {
        team1.addPlayer(McDavid);
        team1.removePlayer(McDavid);

        assertFalse(team1.getPlayers().contains(McDavid));
        assertEquals(team1.getPlayers().size(), 0);
    }

    @Test
    public void testEqualsSameName() {
        assertTrue(team1.equals(team1));
        assertTrue(team1.equals(team1a));
    }

    @Test
    public void testEqualsDifferentNames() {
        assertFalse(team1.equals(team2));
    }

    @Test
    public void testHashCodeSameName() {
        assertTrue(team1.hashCode() == team1.hashCode());
        assertTrue(team1.hashCode() == team1a.hashCode());
    }

    @Test
    public void testHashCodeDifferentNames() {
        assertFalse(team1.hashCode() == team2.hashCode());
    }








//
//    @Test
//    public void testGetSizeEmpty() {
//        assertEquals(baseRoster.getSize(), 0);
//    }
//
//    @Test
//    public void testGetSizeSeveral() {
//        assertEquals(roster.getSize(), 6);
//    }
//
//    @Test
//    public void testGetPlayersEmpty() {
//        assertFalse(baseRoster.getPlayers().contains(McDavid));
//    }
//
//    @Test
//    public void testGetPlayersSeveral() {
//        assertTrue(roster.getPlayers().contains(McDavid));
//        assertTrue(roster.getPlayers().contains(Boeser));
//        assertTrue(roster.getPlayers().contains(Ovechkin));
//        assertTrue(roster.getPlayers().contains(Hedman));
//        assertTrue(roster.getPlayers().contains(Josi));
//        assertTrue(roster.getPlayers().contains(Jones));
//        assertFalse(roster.getPlayers().contains(Horvat));
//    }
//
//    @Test
//    public void testGetPlayerNamesEmpty() {
//        assertEquals(baseRoster.getPlayerNames(), baseList);
//    }
//
//    @Test
//    public void testGetPlayerNamesSeveral() {
//        assertTrue(roster.getPlayerNames().contains("Connor McDavid"));
//        assertTrue(roster.getPlayerNames().contains("Brock Boeser"));
//        assertTrue(roster.getPlayerNames().contains("Alexander Ovechkin"));
//        assertTrue(roster.getPlayerNames().contains("Victor Hedman"));
//        assertTrue(roster.getPlayerNames().contains("Roman Josi"));
//        assertTrue(roster.getPlayerNames().contains("Martin Jones"));
//        assertFalse(roster.getPlayerNames().contains("Bo Horvat"));
//    }
//
//    @Test
//    public void testContainsPlayerEmptyList() {
//        assertFalse(baseRoster.containsPlayer(McDavid));
//    }
//
//    @Test
//    public void testContainsPlayerNoMatch() {
//        assertFalse(roster.containsPlayer(Horvat));
//    }
//
//    @Test
//    public void testContainsPlayerMatch() {
//        assertTrue(roster.containsPlayer(Boeser));
//    }
//
//    @Test
//    public void testContainsPlayerNameEmpty() {
//        assertFalse(baseRoster.containsPlayerName("Connor McDavid"));
//    }
//
//    @Test
//    public void testContainsPlayerNameNoMatch() {
//        assertFalse(roster.containsPlayerName("Bo Horvat"));
//    }
//
//    @Test
//    public void testContainsPlayerNameMatch() {
//        assertTrue(roster.containsPlayerName("Brock Boeser"));
//    }
//
//    @Test
//    public void testPlayerLookupEmpty() {
//        assertEquals(baseRoster.playerLookup("Connor McDavid"), null);
//    }
//
//    @Test
//    public void testPlayerLookupNoMatch() {
//        assertEquals(roster.playerLookup("Bo Horvat"), null);
//    }
//
//    @Test
//    public void testPlayerLookupMatch() {
//        assertEquals(roster.playerLookup("Brock Boeser"), Boeser);
//    }
//
//    //TODO: Verify that this is this how you properly test add/remove methods
//    @Test
//    public void testAddPlayerEmpty() {
//        assertEquals(baseRoster.getSize(), 0);
//        baseRoster.addSkater(Horvat);
//        assertEquals(baseRoster.getSize(), 1);
//    }
//
//    @Test
//    public void testAddPlayerSeveralValid() {
//        assertEquals(roster.getSize(), 6);
//        roster.addSkater(Horvat);
//        assertEquals(roster.getSize(), 7);
//    }
//
//    @Test
//    public void testAddPlayerSeveralInvalid() {
//        assertEquals(roster.getSize(), 6);
//        roster.addSkater(McDavid);
//        assertEquals(roster.getSize(), 6);
//    }
//
//    @Test
//    public void testRemovePlayerEmpty() {
//        assertEquals(baseRoster.getSize(), 0);
//        baseRoster.removeSkater(Horvat);
//        assertEquals(baseRoster.getSize(), 0);
//    }
//
//    @Test
//    public void testRemovePlayerSeveralValid() {
//        assertEquals(roster.getSize(), 6);
//        roster.removeSkater(McDavid);
//        assertEquals(roster.getSize(), 5);
//    }
//
//    @Test
//    public void testRemovePlayerSeveralInvalid() {
//        assertEquals(roster.getSize(), 6);
//        roster.removeSkater(Horvat);
//        assertEquals(roster.getSize(), 6);
//    }
}
