package model.Team;

import java.io.Serializable;
import java.util.Objects;

public class Team implements Serializable {
    private String teamName;
    private int fantasyPoints;

    public Team(String teamName) {
        this.teamName = teamName;
        fantasyPoints = 0;
    }


    // EFFECTS: Returns the team's name
    public String getTeamName() {
        return teamName;
    }


    // EFFECTS: Returns the team's fantasy points
    public int getFantasyPoints() {
        return fantasyPoints;
    }

    // EFFECTS: Sets the team's name to the given string
    //          Does nothing if the string is negative
    public void setTeamName(String teamName) {
        if (!teamName.equals("")) {
            this.teamName = teamName;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {

        return Objects.hash(teamName);
    }
}
