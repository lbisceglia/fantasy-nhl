package ui;

public class Main {
    // Fantasy Points conversion rate for goals and assists
    private static int GOAL_FP = 10;
    private static int ASSIST_FP = 5;

    // NHL player's name and stats for the week
    private static String PLAYER = "Bo Horvat";
    private static int HORVAT_GOALS = 6;
    private static int HORVAT_ASSISTS = 7;

    public static void main(String[] args) {
        printPoints(PLAYER, HORVAT_GOALS, HORVAT_ASSISTS);
        printTopPerformer(PLAYER);
    }
    // Print Fantasy Point player update
    public static void printPoints(String player, int goals, int assists){
        String points = Integer.toString(calculatePoints(goals, assists));

        System.out.println(player + " has earned you " + points + " Fantasy Points this week!");
    }
    // Award Fantasy Points per goal and assist
    public static int calculatePoints(int goals, int assists) {
       return (goals * GOAL_FP) + (assists * ASSIST_FP);
    }
    // Print top performer of the week
    public static void printTopPerformer(String player) {
        System.out.println(player + " is your top performer of the week.");
    }
}
