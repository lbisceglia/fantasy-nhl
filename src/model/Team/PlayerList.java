package model.Team;

import model.Player.Player;

import java.util.ArrayList;

public interface PlayerList {

    ArrayList<Player> getPlayers();

    ArrayList<String> getPlayerNames();

    int getSize();

    boolean containsPlayer(Player p);

    boolean containsPlayerName(String pn);

    Player playerLookup(String playerName);
}
