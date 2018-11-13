package model.Stat;

import model.exceptions.InvalidStatException;

import java.io.Serializable;

public class StatValidator implements Serializable {

    public static boolean isNaturalNumberStat(String statType, int statValue) throws InvalidStatException {
        if(statValue >= 0) {
            return true;
        } else {
            throw new InvalidStatException(statType+ " cannot be negative.");
        }
    }

    public static boolean isNonNegativeStat(String statType, double statValue) throws InvalidStatException {
        if (statValue >= 0) {
            return true;
        } else {
            throw new InvalidStatException(statType+ " cannot be negative.");
        }
    }

    public static boolean isValidPercentageStat(String statType, double statValue) throws InvalidStatException {
        if (statValue >= 0 && statValue <= 1) {
            return true;
        } else {
            throw new InvalidStatException(statType+ " must be between 0 and 1.");
        }
    }
}
