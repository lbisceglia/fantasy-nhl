package model.Observers;

import model.Stat.GameStat;

public interface Observer {

    public void update(GameStat gameStat);
}
