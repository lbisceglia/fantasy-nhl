package tests;

        import models.Player;
        import managers.StatManager;
        import org.junit.jupiter.api.BeforeEach;

        import java.util.ArrayList;
        import java.util.List;

public class TestStatManager {
    private Player Ovechkin;
    private StatManager statManager;
    int gameID;
    List<Integer> gameIDs;

    @BeforeEach
    public void setup() {
        gameID = 2017021244;
        gameIDs = new ArrayList<>();
        gameIDs.add(gameID);
        Ovechkin = new Player(8471214, "Alex Ovechkin", Player.Position.LW);
    }

//    @Test
//    public void testAddGameStats() {
//        try {
//            Ovechkin.getStatManager().addGameStats(gameIDs);
//
//        } catch (IOException e) {
//            System.out.println("Whoopsie Daisy!");
//        }
//    }

}
