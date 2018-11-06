package tests;

import model.Player.Goalie;
import model.Player.Position;
import model.Player.Skater;
import model.Team.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayer {

    protected Skater McDavid;
    protected Skater Ovechkin;
    protected Skater Hedman;
    protected Skater Josi;
    protected Skater Boeser;
    protected Skater Horvat;
    protected Goalie Jones;
    protected Team team;

    @BeforeEach
    public void setup() {
        McDavid = new Skater("Connor McDavid", Position.C, 41, 67);
        Ovechkin = new Skater("Alexander Ovechkin", Position.LW, 49, 38);
        Hedman = new Skater("Victor Hedman", Position.D, 17, 46);
        Josi = new Skater("Roman Josi", Position.D, 14, 39);
        Boeser = new Skater("Brock Boeser", Position.RW, 29, 26);
        Horvat = new Skater("Bo Horvat", Position.C, 22, 22);
        Jones = new Goalie("Martin Jones", 0.915, 2.55);
        team = new Team("team");

    }

    @Test
    public void testConstructor() {
        assertEquals(McDavid.getPlayerName(), "Connor McDavid");
        assertEquals(McDavid.getPlayerPosition(), Position.C);
        assertEquals(McDavid.getWeekFantasyPoints(), 0);
        assertEquals(McDavid.getTotalFantasyPoints(), 0);
        assertEquals(McDavid.getTeam(), null);
    }

    @Test
    public void testSetTeam() {

    }
}