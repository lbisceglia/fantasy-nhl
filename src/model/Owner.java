package model;

import model.Team.Team;
import model.exceptions.InvalidLoginException;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Owner implements Serializable {
    String username;
    String password;
    Set<Team> teams;

    public Owner(String username, String password) throws InvalidLoginException {
        this.username = username;
        this.password = password;
        teams = new HashSet<>();
    }
}
