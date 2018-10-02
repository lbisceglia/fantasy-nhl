package tests;

import model.Player.Position;
import model.Player.Skater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayer {

    private Skater McDavid;
    private Skater Ovechkin;
    private Skater Hedman;
    private Skater Josi;

    @BeforeEach
    public void setup() {
        McDavid = new Skater("Connor McDavid", Position.C, 0, 0, 41, 67);
        Ovechkin = new Skater("Alexander Ovechkin", Position.LW, 0, 0, 49, 38);
        Hedman = new Skater("Victor Hedman", Position.D, 0, 0, 17, 46);
        Josi = new Skater("Roman Josi", Position.D, 0, 0, 14, 39);
    }

    @Test
    public void testConstructor() {
        Skater McDavid2 = new Skater("Connor McDavid", Position.C, -10, 20, -41, 67);
        assertEquals(McDavid2.getWeekFantasyPoints(), 0);
        Skater McDavid3 = new Skater("Connor McDavid", Position.C, -1, 20, -1, 67);
        assertEquals(McDavid3.getWeekFantasyPoints(), 0);
        Skater McDavid4 = new Skater("Connor McDavid", Position.C, 0, 20, 0, 67);
        assertEquals(McDavid4.getWeekFantasyPoints(), 0);
        Skater McDavid5 = new Skater("Connor McDavid", Position.C, 1, 20, 1, 67);
        assertEquals(McDavid5.getWeekFantasyPoints(), 1);
        Skater McDavid6 = new Skater("Connor McDavid", Position.C, 10, 20, 41, 67);
        assertEquals(McDavid6.getWeekFantasyPoints(), 10);

        Skater Boeser2 = new Skater("Brock Boeser", Position.RW, 10, -20, 29, -26);
        assertEquals(Boeser2.getTotalFantasyPoints(), 0);
        Skater Boeser3 = new Skater("Brock Boeser", Position.RW, 10, -1, 29, -1);
        assertEquals(Boeser3.getTotalFantasyPoints(), 0);
        Skater Boeser4 = new Skater("Brock Boeser", Position.RW, 10, 0, 29, 0);
        assertEquals(Boeser4.getTotalFantasyPoints(), 0);
        Skater Boeser5 = new Skater("Brock Boeser", Position.RW, 10, 1, 29, 1);
        assertEquals(Boeser5.getTotalFantasyPoints(), 1);
        Skater Boeser6 = new Skater("Brock Boeser", Position.RW, 10, 20, 29, 26);
        assertEquals(Boeser6.getTotalFantasyPoints(), 20);
    }

    @Test
    public void testGetters() {
        assertEquals(McDavid.getPlayerName(), "Connor McDavid");
        assertEquals(Hedman.getPlayerPosition(), Position.D);
        assertEquals(Ovechkin.getTotalGoals(), 49);
        assertEquals(Josi.getTotalAssists(), 39);
    }
}
