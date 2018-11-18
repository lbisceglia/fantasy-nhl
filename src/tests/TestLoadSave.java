//package tests;
//
//import models.league.league;
//import interfaces.Loadable;
//import interfaces.Saveable;
//import models.team.GoalieList;
//import models.team.SkaterList;
//import models.team.team;
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
//    team team1 = new team("Lorenzo's team");
//    team team2 = new team("Donato's team");
//    SkaterList availableSkaters = new team("Available Skaters");
//    GoalieList availableGoalies = new team("Available Goalies");
//    league league = new league(availableSkaters, availableGoalies);
//    ArrayList<team> participants = league.getTeamMap();
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
//            ArrayList<team> participants1 = (ArrayList<team>) oi.readObject();
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
//            // TODO AutoDraft-generated catch block
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
