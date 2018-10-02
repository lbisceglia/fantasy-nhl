package model.League;

import model.Team.Team;

import java.util.ArrayList;
import java.util.HashSet;

public interface TeamList {
    HashSet<Team> getTeams();
    ArrayList<String> getTeamNames();
    Team teamLookup (String teamName);
    boolean containsTeam(Team team);
    boolean containsTeamName(String teamName);
    void addTeam(String teamName);
    void removeTeam(Team team);
}
