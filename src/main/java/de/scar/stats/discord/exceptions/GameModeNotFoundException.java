package de.scar.stats.discord.exceptions;

public class GameModeNotFoundException extends Exception {

    public GameModeNotFoundException() {
        super("Target gamemode couldn't be found");
    }
}