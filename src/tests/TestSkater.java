package tests;

import model.Player.Position;
import model.Player.Skater;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSkater {

    private Skater McDavid;
    private Skater Boeser;
    private Skater Ovechkin;
    private Skater Hedman;
    private Skater Josi;

    @BeforeEach
    public void setup() {
        McDavid = new Skater("Connor McDavid", Position.C, 0, 0, 41, 67);
        Boeser = new Skater("Brock Boeser", Position.RW, 0, 0, 29, 26);
        Ovechkin = new Skater("Alexander Ovechkin", Position.LW, 0, 0, 49, 38);
        Hedman = new Skater("Victor Hedman", Position.D,0, 0, 17, 46);
        Josi = new Skater("Roman Josi", Position.D,0, 0, 14, 39);
    }

    @Test
    public void testConstructor() {
        Skater McDavid2 = new Skater("Connor McDavid",Position.C, 0, 0, -41, -67);
        assertEquals(McDavid2.getTotalGoals(), 0);
        assertEquals(McDavid2.getTotalAssists(), 0);
        Skater McDavid3 = new Skater("Connor McDavid",Position.C, 0, 0, -1, -1);
        assertEquals(McDavid3.getTotalGoals(), 0);
        assertEquals(McDavid3.getTotalAssists(), 0);
        Skater McDavid4 = new Skater("Connor McDavid",Position.C, 0, 0, 0, 0);
        assertEquals(McDavid4.getTotalGoals(), 0);
        assertEquals(McDavid4.getTotalAssists(), 0);
        Skater McDavid5 = new Skater("Connor McDavid",Position.C, 0, 0, 1, 1);
        assertEquals(McDavid5.getTotalGoals(), 1);
        assertEquals(McDavid5.getTotalAssists(), 1);
        Skater McDavid6 = new Skater("Connor McDavid", Position.C, 0, 0, 41, 67);
        assertEquals(McDavid6.getTotalGoals(), 41);
        assertEquals(McDavid6.getTotalAssists(), 67);
    }

    @Test
    public void testGetters() {
        assertEquals(Ovechkin.getTotalGoals(), 49);
        assertEquals(Josi.getTotalAssists(), 39);
    }

    @Test
    public void testSettersPositive() {
        int goals = 100;
        int assists = 80;
        assertEquals(McDavid.getTotalGoals(), 41);
        McDavid.setTotalGoals(goals);
        assertEquals(McDavid.getTotalGoals(), goals);
        assertEquals(Boeser.getTotalAssists(), 26);
        Boeser.setTotalAssists(assists);
        assertEquals(Boeser.getTotalAssists(), assists);
    }

    @Test
    public void testSettersNegative() {
        int goals = -75;
        int assists = -50;
        assertEquals(McDavid.getTotalGoals(), 41);
        McDavid.setTotalGoals(goals);
        assertEquals(McDavid.getTotalGoals(), 41);
        assertEquals(Boeser.getTotalAssists(), 26);
        Boeser.setTotalAssists(assists);
        assertEquals(Boeser.getTotalAssists(), 26);
    }

}
