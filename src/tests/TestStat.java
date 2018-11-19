package tests;

import models.Player;
import models.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStat {
    private Stat stat;
    private Player McDavid;


    @BeforeEach
    public void setup() {
        McDavid = new Player (8478402,"Connor McDavid", Player.Position.C);

    }

    @Test
    public void testConstructor() {
        assertEquals(stat.getStatType(), "Goal");
        assertEquals(stat.getPlayer(), McDavid);
        assertEquals(stat.getStatType(), "Save");
    }
}