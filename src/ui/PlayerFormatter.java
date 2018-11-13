package ui;

import model.Player.Player;

public class PlayerFormatter {

    PlayerFormatter() {

    }

    public static String getPlayerNameAndPosition(Player p) {
        String title = p.getPlayerName() + " (" + p.getPosition() + ")";
        return title;
    }
}
