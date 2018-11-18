package tests;

import models.Player;
import models.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStat {
    private Stat testEvent1;
    private Stat testEvent2;
    private LocalDate testTimestamp1;
    private LocalDate testTimestamp2;
    private Player McDavid;


    @BeforeEach
    public void setup() {
        testTimestamp1 = LocalDate.of(2018, 9, 24);
        testTimestamp2 = LocalDate.of(2018, 10, 1);
        McDavid = new Player (8478402,"Connor McDavid", Player.Position.C);
    }

    @Test
    public void testConstructor() {
//        assertEquals(testEvent1.getDate(), testTimestamp1);
        assertEquals(testEvent1.getStatType(), "Goal");
        assertEquals(testEvent1.getPlayer(), McDavid);
//        assertEquals(testEvent2.getDate(), testTimestamp2);
        assertEquals(testEvent2.getStatType(), "Save");
    }
}