package tests;

import model.Player.Position;
import model.Player.Skater;
import model.exceptions.InvalidStatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSkater extends TestPlayer {

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testBasicConstructor() {
        Skater McDavid2 = new Skater("Connor McDavid", Position.C);
        assertEquals(McDavid2.getTotalGoals(), 0);
        assertEquals(McDavid2.getTotalAssists(), 0);
    }

    @Test
    public void testConstructorPositiveOnlyNoExceptions() {
        try {
            Skater McDavid2 = new Skater("Connor McDavid", Position.C, 41, 67);
            assertEquals(McDavid2.getTotalGoals(), 41);
            assertEquals(McDavid2.getTotalAssists(), 67);
        } catch (InvalidStatException e) {
            fail("No Exception should be thrown -- all parameters valid.");
        }
    }

    @Test
    public void testConstructorNegativeGoalsException() {
        try {
            Skater McDavid2 = new Skater("Connor McDavid", Position.C, -1, 67);
            fail("Exception should be thrown for negative goals.");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testConstructorNegativeAssistsException() {
        try {
            Skater McDavid2 = new Skater("Connor McDavid", Position.C, 41, -1);
            fail("Exception should be thrown for negative assists.");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testConstructorNegativeGoalsAndAssistsException() {
        try {
            Skater McDavid2 = new Skater("Connor McDavid", Position.C, -1, -1);
            fail("Exception should be thrown for negative goals (thrown before assists).");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testGetters() {
        assertEquals(Ovechkin.getTotalGoals(), 49);
        assertEquals(Josi.getTotalAssists(), 39);
    }

    @Test
    public void testSettersPositiveOnlyNoExceptions() {
        int goals = 100;
        int assists = 80;
        try {
            McDavid.setTotalGoals(goals);
            assertEquals(McDavid.getTotalGoals(), goals);
            McDavid.setTotalAssists(assists);
            assertEquals(McDavid.getTotalAssists(), assists);
        } catch (InvalidStatException e) {
            fail("Exception should not be thrown -- all parameters valid.");
        }
    }

    @Test
    public void testSetGoalsNegativeGoalsException() {
        int goals = -75;
        try {
            McDavid.setTotalGoals(goals);
            fail("Exception should have been thrown for negative goals.");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testSetAssistsNegativeAssistsException() {
        int assists = -100;
        try {
            McDavid.setTotalAssists(assists);
            fail("Exception should have been thrown for negative goals.");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }
}
