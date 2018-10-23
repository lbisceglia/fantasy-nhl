package model.Team;

import model.Player.Skater;

import java.util.ArrayList;

public interface SkaterList extends PlayerList {

    ArrayList<Skater> getSkaters();

    void addSkater(Skater skater);

    void removeSkater(Skater skater);

    Skater skaterLookup(String skaterName);
}
