package tests;

import exceptions.InvalidTeamException;
import models.Player;
import models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestPlayer {

    protected Player McDavid;
    protected Player McDavid2;
    protected Player McDavid3;
    protected Player McDavid4;
    protected Player McDavid5;
    //    protected StatManager statManager;
    protected Team team1;
    protected Team team2;

    @BeforeEach
    public void setup() {
        McDavid = new Player(8478402, "Connor McDavid", Player.Position.C);
        McDavid2 = new Player(8478402, "Connor McDavid", Player.Position.C);
        McDavid3 = new Player(8478403, "Connor McDavid", Player.Position.C);
        McDavid4 = new Player(8478402, "Konnor MacDavid", Player.Position.C);
        McDavid5 = new Player(8478403, "Konnor MacDavid", Player.Position.C);
//        statManager = McDavid.getStatManager();
        try {
            team1 = new Team("team1");
            team2 = new Team("team2");
        } catch (InvalidTeamException e) {
            System.out.println(e.getMsg());
        }
    }

    @Test
    public void testConstructor() {
        assertEquals(McDavid.getPlayerID(), 8478402);
        assertEquals(McDavid.getPlayerName(), "Connor McDavid");
        assertEquals(McDavid.getPosition(), Player.Position.C);
//        assertEquals(McDavid.getTeam(), null);
//        assertEquals(McDavid.getStats(), new HashSet<>());
//        assertEquals(McDavid.getStatManager().getPlayer(), McDavid);
    }

//    @Test
//    public void testSetTeamCurrentTeamNull() {
//        McDavid.setTeam(team1);
//        assertEquals(McDavid.getTeam(), team1);
//        assertTrue(team1.getPlayerManager().containsPlayer(McDavid));
//    }
//
//    @Test
//    public void testSetTeamSameAsCurrentTeam() {
//        McDavid.setTeam(team1);
//        McDavid.setTeam(team1);
//        assertEquals(McDavid.getTeam(), team1);
//        assertTrue(team1.getPlayerManager().containsPlayer(McDavid));
//    }
//
//    @Test
//    public void testSetTeamSDifferentFromCurrentTeam() {
//        McDavid.setTeam(team1);
//        McDavid.setTeam(team2);
//        assertEquals(McDavid.getTeam(), team2);
//        assertFalse(team1.getPlayerManager().containsPlayer(McDavid));
//        assertTrue(team2.getPlayerManager().containsPlayer(McDavid));
//    }

    @Test
    public void testEqualsSameIDAndName() {
        assertTrue(McDavid.equals(McDavid));
        assertTrue(McDavid.equals(McDavid2));
    }

    @Test
    public void testEqualsDifferentIDSameName() {
        assertFalse(McDavid.equals(McDavid3));
    }

    @Test
    public void testEqualsSameIDDifferentName() {
        assertFalse(McDavid.equals(McDavid4));
    }

    @Test
    public void testEqualsDifferentIDAndName() {
        assertFalse(McDavid.equals(McDavid5));
    }

    @Test
    public void testHashCodeSameIDAndName() {
        assertTrue(McDavid.hashCode() == McDavid.hashCode());
        assertTrue(McDavid2.hashCode() == McDavid2.hashCode());
    }

    @Test
    public void testHashCodeDifferentIDSameName() {
        assertFalse(McDavid.hashCode() == McDavid3.hashCode());
    }

    @Test
    public void testHashCodeSameIDDifferentName() {
        assertFalse(McDavid.hashCode() == McDavid4.hashCode());
    }

    @Test
    public void testHashCodeDifferentIDAndName() {
        assertFalse(McDavid.hashCode() == McDavid5.hashCode());
    }
}