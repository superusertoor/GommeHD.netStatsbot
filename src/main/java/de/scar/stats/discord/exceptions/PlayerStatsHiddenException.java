package de.scar.stats.discord.exceptions;

public class PlayerStatsHiddenException extends Exception {

    public PlayerStatsHiddenException() {
        super("Target player's stats are hidden");
    }
}