package de.scar.stats.discord.exceptions;

public class ClanNotFoundException extends Exception {

    public ClanNotFoundException() {
        super("Target clan couldn't be found");
    }

}
