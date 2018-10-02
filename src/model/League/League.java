package model.League;

import model.Loadable;
import model.Player.Player;
import model.Team.PlayerList;
import model.Saveable;
import model.Team.Team;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;

public class League implements TeamList, Loadable, Saveable, Serializable {
    HashSet<Team> participants;
    PlayerList availablePlayers;

    public League(PlayerList availablePlayers) {
        this.participants = new HashSet<>();
        this.availablePlayers = availablePlayers;
    }

    @Override
    // EFFECTS: Returns a list of teams in this league
    public HashSet<Team> getTeams() {
        return participants;
    }

    @Override
    public ArrayList<String> getTeamNames() {
        ArrayList<String> participants = new ArrayList<>();
        for (Team t : this.participants) {
            String team = t.getTeamName();
            participants.add(team);
        }
        return participants;
    }

    // EFFECTS: Returns a list of the players available in this league
    public PlayerList getAvailablePlayers() {
        return availablePlayers;
    }

    // EFFECTS: Returns a list of the players names available in this league
    public ArrayList<String> getAvailablePlayerNames() {
        return availablePlayers.getPlayerNames();
    }

    @Override
    // EFFECTS: Returns true if the team already exists in this league
    //          Returns false if the team does not already exist in this league
    public boolean containsTeam(Team team) {
        return participants.contains(team);
    }

    @Override
    // EFFECTS: Returns true if the team name already exists in this league
    //          Returns false if the name is unqiue in this league
    public boolean containsTeamName(String teamName) {
        for (Team t : this.participants) {
            if (teamName.equals(t.getTeamName())) {
                return true;
            }
        }
        return false;
    }

    public boolean containsAvailablePlayerName(String playerName) {
        return availablePlayers.containsPlayerName(playerName);
    }


    @Override
    // MODIFIES: this
    // EFFECTS: Creates a new Team adds it to the league if the name isn't already taken
    //          Does nothing if name is already taken
    public void addTeam(String teamName) {
        if (!this.containsTeamName(teamName)) {
            Team team = new Team(teamName);
            participants.add(team);
        }
    }


    // MODIFIES: this, TODO: ADD WHAT ELSE IT MODIFIES
    // EFFECTS: Adds a player to a Team and removes them from the League available players list
    public void addPlayerToFantasyTeam(String teamName, String playerName) {
        if (this.containsTeamName(teamName)) {
            if (this.containsAvailablePlayerName(playerName)) {
                // The player name is valid and player added to the team, removed from available players
                this.teamLookup(teamName).addPlayer(availablePlayers.playerLookup(playerName));
                this.removeAvailablePlayer(playerName);
            }
        }
    }

    @Override
    // EFFECTS: Returns the team with the corresponding team name
    public Team teamLookup(String teamName) {
        for (Team t : this.participants) {
            if (t.getTeamName().equals(teamName)) {
                return t;
            }
        }
        return null;
    }


    // MODIFIES: this
    // EFFECTS: Removes player from the list if they are on the list
    //          Does nothing if player is not on the list
    private void removeAvailablePlayer(String playerName) {
        if (this.containsAvailablePlayerName(playerName)) {
            availablePlayers.removePlayer(availablePlayers.playerLookup(playerName));
        }
    }

    @Override
    // MODIFIES: this
    // EFFECTS: removes team from the fantasy league, removes its players and adds its players back to the available players list
    public void removeTeam(Team team) {
        participants.remove(team);
        for (Player p : team.getPlayers()) {
            team.removePlayer(p);
            availablePlayers.addPlayer(p);
        }
    }

    // EFFECTS: Prints the team name and roster for every team in the league
    public void printTeamsAndPlayers() {
        for (Team t : participants) {
            t.printTeamAndPlayers();
        }
    }

    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void save() {
        try {
            FileOutputStream f = new FileOutputStream(new File("fantasyLeague.txt"));
            ObjectOutputStream o = new ObjectOutputStream(f);

            // Write objects to file
            o.writeObject(this.participants);
            o.writeObject(this.availablePlayers);

            o.close();
            f.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        }

    }

    @Override
    //Modeled after Object Stream tutorial, 2018-10-01 [https://www.mkyong.com/java/how-to-read-and-write-java-object-to-a-file/]
    // TODO: add specification and tests for this method
    public void load() {
        try {
            FileInputStream fi = new FileInputStream(new File("fantasyLeague.txt"));
            ObjectInputStream oi = new ObjectInputStream(fi);

            // Read objects
            HashSet<Team> participants1 = (HashSet<Team>) oi.readObject();
            PlayerList availableplayers1 = (PlayerList) oi.readObject();

            this.participants = participants1;
            this.availablePlayers = availableplayers1;

            oi.close();
            fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            System.out.println("Error initializing stream");
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}


