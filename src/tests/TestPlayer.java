package tests;

import model.Player.Goalie;
import model.Player.Position;
import model.Player.Skater;
import model.exceptions.InvalidPositionException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static junit.framework.TestCase.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestPlayer {

    protected Skater McDavid;
    protected Skater Ovechkin;
    protected Skater Hedman;
    protected Skater Josi;
    protected Goalie Jones;

    @BeforeEach
    public void setup() {
        McDavid = new Skater("Connor McDavid", Position.C, 41, 67);
        Ovechkin = new Skater("Alexander Ovechkin", Position.LW, 49, 38);
        Hedman = new Skater("Victor Hedman", Position.D, 17, 46);
        Josi = new Skater("Roman Josi", Position.D, 14, 39);
        Jones = new Goalie("Martin Jones", 0.915, 2.55);
    }

    @Test
    public void testConstructorPlayerValidPositionNoExceptions() {
        try {
            assertEquals(McDavid.getPlayerName(), "Connor McDavid");
            assertEquals(McDavid.getPlayerPosition(), Position.C);
            assertEquals(McDavid.getTotalGoals(), 41);
            assertEquals(McDavid.getTotalAssists(), 67);
            assertEquals(McDavid.getWeekFantasyPoints(), 0);
            assertEquals(McDavid.getTotalFantasyPoints(), 0);
        } catch (InvalidPositionException e) {
            fail("No Exception should be thrown -- all parameters valid.");
        }
    }

    @Test
    public void testConstructorPlayerInvalidPositionNoExceptions() {
        try {
            Skater McGoalie = new Skater("Connor McDavid", Position.G, 41, 67);
            fail("Invalid Position exception should be thrown.");
        } catch (InvalidPositionException e) {
            // Nothing required here
        }
    }
}