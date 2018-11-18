package ui.formatters;

import models.Player;

public class PlayerFormatter {

    PlayerFormatter() {

    }

    public static String getPlayerNameAndPosition(Player p) {
        String title = p.getPlayerName() + " (" + p.getPosition() + ")";
        return title;
    }
}
