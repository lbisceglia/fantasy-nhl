package tests;

import model.Player.Goalie;
import model.exceptions.InvalidStatException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGoalie extends TestPlayer {
    private static final double big = 100;
    private static final double topBoundHigh = 1.001;
    private static final double topBound = 1;
    private static final double topBoundLow = 0.999;
    private static final double inRange = 0.5;
    private static final double bottomBoundHigh = 0.001;
    private static final double bottomBound = 0;
    private static final double bottomBoundLow = -0.001;
    private static final double small = -100;

    @BeforeEach
    public void setup() {
        super.setup();
    }

    @Test
    public void testBasicConstructor() {
        Goalie Jones2 = new Goalie("Martin Jones");
        assertEquals(Jones2.getSavePercentage(), 1);
        assertEquals(Jones2.getGoalsAgainstAverage(), 0);
    }

    @Test
    public void testConstructorBothInRangeNoExceptions() {
        try {
            Goalie Jones2 = new Goalie("Martin Jones", 0.915, 2.55);
            assertEquals(Jones2.getSavePercentage(), 0.915);
            assertEquals(Jones2.getGoalsAgainstAverage(), 2.55);
        } catch (InvalidStatException e) {
            fail("No Exception should be thrown -- all parameters valid.");
        }
    }

    @Test
    public void testConstructorNegativeSVPException() {
        try {
            Goalie Jones2 = new Goalie("Martin Jones", bottomBoundLow, 2.55);
            fail("Exception should be thrown for negative SvP");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testConstructorSVPAboveOneException() {
        try {
            Goalie Jones2 = new Goalie("Martin Jones", topBoundHigh, 2.55);
            fail("Exception should be thrown for SvP greater than 1");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testConstructorNegativeGAAException() {
        try {
            Goalie Jones2 = new Goalie("Martin Jones", 0.915, bottomBoundLow);
            fail("Exception should be thrown for negative GAA");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testGetters() {
        assertEquals(Jones.getSavePercentage(), 0.915);
        assertEquals(Jones.getGoalsAgainstAverage(), 2.55);
    }

    @Test
    public void testSetSavePercentageNoExceptions() {
        try {
            Jones.setSavePercentage(topBound);
            assertEquals(Jones.getSavePercentage(), topBound);

            Jones.setSavePercentage(topBoundLow);
            assertEquals(Jones.getSavePercentage(), topBoundLow);

            Jones.setSavePercentage(inRange);
            assertEquals(Jones.getSavePercentage(), inRange);

            Jones.setSavePercentage(bottomBoundHigh);
            assertEquals(Jones.getSavePercentage(), bottomBoundHigh);

            Jones.setSavePercentage(bottomBound);
            assertEquals(Jones.getSavePercentage(), bottomBound);
        } catch (InvalidStatException e) {
            fail("No Exception should be thrown -- all parameters valid.");
        }
    }

    @Test
    public void testSetSavePercentageVeryBigException() {
        try {
            Jones.setSavePercentage(big);
            fail("Exception should be thrown for SvP greater than 1.");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testSetSavePercentageAboveOneException() {
        try {
            Jones.setSavePercentage(topBoundHigh);
            fail("Exception should be thrown for SvP greater than 1.");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testSetSavePercentageNegativeException() {
        try {
            Jones.setSavePercentage(bottomBoundLow);
            fail("Exception should be thrown for negative SvP");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testSetSavePercentageVerySmallException() {
        try {
            Jones.setSavePercentage(small);
            fail("Exception should be thrown for negative SvP");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testSetGoalsAgainstAverageNoExceptions() {
        try {
            Jones.setGoalsAgainstAverage(big);
            assertEquals(Jones.getGoalsAgainstAverage(), big);

            Jones.setGoalsAgainstAverage(inRange);
            assertEquals(Jones.getGoalsAgainstAverage(), inRange);

            Jones.setGoalsAgainstAverage(bottomBoundHigh);
            assertEquals(Jones.getGoalsAgainstAverage(), bottomBoundHigh);

            Jones.setGoalsAgainstAverage(bottomBound);
            assertEquals(Jones.getGoalsAgainstAverage(), bottomBound);
        } catch (InvalidStatException e) {
            fail("No Exception should be thrown -- all parameters valid.");
        }
    }

    @Test
    public void testSetGoalsAgainstAverageNegativeException() {

        try {
            Jones.setGoalsAgainstAverage(bottomBoundLow);
            fail("Exception should be thrown for negative GAA");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }

    @Test
    public void testSetGoalsAgainstAverageVerySmallException() {

        try {
            Jones.setGoalsAgainstAverage(small);
            fail("Exception should be thrown for negative GAA");
        } catch (InvalidStatException e) {
            // Nothing required here
        }
    }
}

