//package tests;
//
//import model.League.League;
//import model.Loadable;
//import model.Saveable;
//import model.Team.GoalieList;
//import model.Team.SkaterList;
//import model.Team.Team;
//import org.junit.jupiter.api.Test;
//
//import java.io.*;
//import java.util.ArrayList;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//public class TestLoadSave implements Loadable, Saveable, Serializable {
//
//    Team team1 = new Team("Lorenzo's Team");
//    Team team2 = new Team("Donato's Team");
//    SkaterList availableSkaters = new Team("Available Skaters");
//    GoalieList availableGoalies = new Team("Available Goalies");
//    League league = new League(availableSkaters, availableGoalies);
//    ArrayList<Team> participants = league.getTeams();
//
//    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
//    // TODO: add specification and tests for this method
//    public void save() {
//        try {
//            FileOutputStream f = new FileOutputStream(new File("fantasyLeague.txt"));
//            ObjectOutputStream o = new ObjectOutputStream(f);
//
//            // Write objects to file
//            o.writeObject(this.participants);
//            o.writeObject(this.availableSkaters);
//            o.writeObject(this.availableGoalies);
//
//
//            o.close();
//            f.close();
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        }
//
//    }
//
//    @Override
//    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
//    // TODO: add specification and tests for this method
//    public void load() {
//        try {
//            FileInputStream fi = new FileInputStream(new File("fantasyLeague.txt"));
//            ObjectInputStream oi = new ObjectInputStream(fi);
//
//            // Read objects
//            ArrayList<Team> participants1 = (ArrayList<Team>) oi.readObject();
//            SkaterList availableSkaters1 = (SkaterList) oi.readObject();
//            GoalieList availableGoalies1 = (GoalieList) oi.readObject();
//
//            this.participants = participants1;
//            this.availableSkaters = availableSkaters1;
//            this.availableGoalies = availableGoalies1;
//
//            oi.close();
//            fi.close();
//
//        } catch (FileNotFoundException e) {
//            System.out.println("File not found");
//        } catch (IOException e) {
//            System.out.println("Error initializing stream");
//        } catch (ClassNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }
//
//    // Modeled after stack overflow forum: [https://stackoverflow.com/questions/35891225/java-how-can-i-test-save-method]
//    @Test
//    public void testSave() {
//        participants.add(team1);
//        participants.add(team2);
//        save();
//
//        assertTrue("fantasyLeagueTest.txt".length() > 0);
//    }
//
//    // Modeled after stack overflow forum: [https://stackoverflow.com/questions/35891225/java-how-can-i-test-save-method]
//    @Test
//    public void testLoad() {
//        participants.add(team1);
//        participants.add(team2);
//        save();
//
//        assertTrue("fantasyLeagueTest.txt".length() > 0);
//
//        participants.remove(team1);
//        save();
//        load();
//
//        assertEquals(1, participants.size());
//    }
//}
