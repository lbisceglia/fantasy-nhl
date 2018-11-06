package ui;

import model.Player.Goalie;
import model.Player.Player;
import model.Player.Skater;

public class PlayerFormatter {

    PlayerFormatter() {

    }

    public static String getPlayerPosition(Player p) {
        String position = "";
        if(p instanceof Skater) {
            position = ((Skater) p).getPlayerPosition().toString();
        } else if (p instanceof Goalie) {
            position = "G";
        }
        return position;
    }

    public static String getPlayerNameAndPosition(Player p) {
        String title = p.getPlayerName() + " (" + getPlayerPosition(p) + ")";
        return title;
    }
}
