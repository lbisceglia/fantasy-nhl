package tests;

import model.GoalieStat;
import model.Player.Goalie;
import model.Player.Position;
import model.Player.Skater;
import model.SkaterStat;
import model.Stat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestStat {
    private Stat testEvent1;
    private Stat testEvent2;
    private LocalDate testTimestamp1;
    private LocalDate testTimestamp2;
    private Skater McDavid;
    private Goalie Jones;


    @BeforeEach
    public void setup() {
        testTimestamp1 = LocalDate.of(2018, 9, 24);
        testTimestamp2 = LocalDate.of(2018, 10, 1);
        McDavid = new Skater("Connor McDavid", Position.C, 41, 67);
        Jones = new Goalie("Martin Jones", 0.915, 2.55);
        testEvent1 = new SkaterStat(1, McDavid, "Goal");
        testEvent2 = new GoalieStat(2, Jones, "Save");
    }

    @Test
    public void testGetters() {
//        assertEquals(testEvent1.getTimestamp(), testTimestamp1);
        assertEquals(testEvent1.getEventType(), "Goal");
        assertEquals(testEvent1.getPlayer(), McDavid);
//        assertEquals(testEvent2.getTimestamp(), testTimestamp2);
        assertEquals(testEvent2.getEventType(), "Save");
        assertEquals(testEvent2.getPlayer(), Jones);
    }
}