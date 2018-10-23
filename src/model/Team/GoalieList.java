package model.Team;

import model.Player.Goalie;

import java.util.ArrayList;

public interface GoalieList extends PlayerList {

    ArrayList<Goalie> getGoalies();

    void addGoalie(Goalie goalie);

    void removeGoalie(Goalie goalie);

    Goalie goalieLookup(String goalieName);
}
