package models;

import exceptions.InvalidTeamException;
import managers.LeagueInitializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

// TODO: Get it to implement loadable again (or move all that functionality over to the UI)
public class League implements Serializable {
    public static final int MIN_PARTICIPANTS = 4;
    public static final int MAX_PARTICIPANTS = 4;
    private List<Team> teams;
    private Set<Player> availablePlayers;
    private LeagueInitializer leagueInitializer;

    // EFFECTS: Constructs a league with the list of available players
    public League() {
        teams = new ArrayList<>();
        leagueInitializer = new LeagueInitializer(this);
    }

    // EFFECTS: Returns a list of teams in this league
    public List<Team> getTeams() {
        return teams;
    }

    // EFFECTS: Returns a list of available players in this league
    public Set<Player> getAvailablePlayers() {
        return availablePlayers;
    }

    public void setAvailablePlayers(Set<Player> availablePlayers) {
        this.availablePlayers = availablePlayers;
    }

    // MODIFIES: this
    // EFFECTS: Adds a new team to the league if it is valid
    //          Throws an InvalidTeamException if not valid
    public void addTeam(Team team) throws InvalidTeamException {
        checkIfTeamIsUnique(team);
        teams.add(team);
    }

    // MODIFIES: this
    // EFFECTS: Removes the team from the fantasy league
    //          Adds the team's players back to the available player pool
    public void removeTeam(Team team) {
        if (teams.contains(team)) {
            for (Player p : team.getPlayers()) {
                p.removeObserver(team);
            }
            teams.remove(team);
        }
    }

    // EFFECTS: Does nothing if the team is unique
    //          Throws an InvalidTeamException if the given team name is not unique in the league
    public void checkIfTeamIsUnique(Team team) throws InvalidTeamException {
        if (teams.contains(team)) {
            throw new InvalidTeamException("Sorry, a team with that name already exists.");
        }
    }
}


