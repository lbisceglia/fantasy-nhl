package model.League;

import model.Team.Team;
import model.exceptions.DuplicateMatchException;

import java.util.ArrayList;

public interface TeamList {
    ArrayList<Team> getTeams();

    ArrayList<String> getTeamNames();

    Team teamLookup(String teamName);

    boolean containsTeam(Team team);

    boolean containsTeamName(String teamName);

    void addTeam(String teamName) throws DuplicateMatchException;

    void removeTeam(Team team);
}
