package model.Team;

import model.Player.Player;

import java.util.ArrayList;
import java.util.HashSet;

public interface PlayerList {
    HashSet<Player> getPlayers();
    ArrayList<String> getPlayerNames();
    int getSize();
    boolean containsPlayer(Player p);
    boolean containsPlayerName(String pn);
    Player playerLookup(String playerName);
    void addPlayer(Player player);
    void removePlayer(Player player);
}
