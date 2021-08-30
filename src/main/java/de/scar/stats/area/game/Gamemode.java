package de.scar.stats.area.game;

import java.util.HashMap;

public class Gamemode {

    private HashMap<String, Integer> entries = new HashMap<>();
    private  Alias alias;

    public Gamemode(Alias alias) {
        this.alias = alias;
    }

    public HashMap<String, Integer> getEntries() {
        return entries;
    }

    public Gamemode add(String key, int value) {
        entries.put(key, value);
        return this;
    }

    public String getGamemodeName() {
        return alias.getBeautified();
    }
}
