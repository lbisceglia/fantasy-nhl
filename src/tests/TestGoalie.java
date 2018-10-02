package tests;

import model.Player.Goalie;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestGoalie {

    private Goalie Jones;

    @BeforeEach
    public void setup() {
        Jones = new Goalie("Martin Jones", 0, 0, 0.915, 2.55);
    }

    @Test
    public void testConstructor() {
        Goalie Jones2 = new Goalie("Martin Jones", 0, 0, -0.915, -2.55);
        assertEquals(Jones2.getSavePercentage(), 1);
        assertEquals(Jones2.getGoalsAgainstAverage(), 0);
        Goalie Jones3 = new Goalie("Martin Jones", 0, 0, -0.001, -0.001);
        assertEquals(Jones3.getSavePercentage(), 1);
        assertEquals(Jones3.getGoalsAgainstAverage(), 0);
        Goalie Jones4 = new Goalie("Martin Jones", 0, 0, 0, 0);
        assertEquals(Jones4.getSavePercentage(), 0);
        assertEquals(Jones4.getGoalsAgainstAverage(), 0);
        Goalie Jones5 = new Goalie("Martin Jones", 0, 0, 0.001, 0.001);
        assertEquals(Jones5.getSavePercentage(), 0.001);
        assertEquals(Jones5.getGoalsAgainstAverage(), 0.001);
        Goalie Jones6 = new Goalie("Martin Jones", 0, 0, 0.915, 2.55);
        assertEquals(Jones6.getSavePercentage(), 0.915);
        assertEquals(Jones6.getGoalsAgainstAverage(), 2.55);
    }

    @Test
    public void testGetters() {
        assertEquals(Jones.getSavePercentage(), 0.915);
        assertEquals(Jones.getGoalsAgainstAverage(), 2.55);
    }

    @Test
    public void testSavePercentage() {
        double big = 100;
        double topBoundHigh = 1.001;
        double topBound = 1;
        double topBoundLow = 0.999;
        double inRange = 0.5;
        double bottomBoundHigh = 0.001;
        double bottomBound = 0;
        double bottomBoundLow = -0.001;
        double small = -100;

        assertEquals(Jones.getSavePercentage(), 0.915);
        Jones.setSavePercentage(big);
        assertEquals(Jones.getSavePercentage(), 0.915);
        Jones.setSavePercentage(topBoundHigh);
        assertEquals(Jones.getSavePercentage(), 0.915);
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
        Jones.setSavePercentage(bottomBoundLow);
        assertEquals(Jones.getSavePercentage(), bottomBound);
        Jones.setSavePercentage(small);
        assertEquals(Jones.getSavePercentage(), bottomBound);
    }

    @Test
    public void testSGoalsAgainstAverage() {
        double inRange = 0.5;
        double bottomBoundHigh = 0.001;
        double bottomBound = 0;
        double bottomBoundLow = -0.001;
        double small = -100;

        Jones.setGoalsAgainstAverage(inRange);
        assertEquals(Jones.getGoalsAgainstAverage(), inRange);
        Jones.setGoalsAgainstAverage(bottomBoundHigh);
        assertEquals(Jones.getGoalsAgainstAverage(), bottomBoundHigh);
        Jones.setGoalsAgainstAverage(bottomBound);
        assertEquals(Jones.getGoalsAgainstAverage(), bottomBound);
        Jones.setGoalsAgainstAverage(bottomBoundLow);
        assertEquals(Jones.getGoalsAgainstAverage(), bottomBound);
        Jones.setGoalsAgainstAverage(small);
        assertEquals(Jones.getGoalsAgainstAverage(), bottomBound);
    }

}

