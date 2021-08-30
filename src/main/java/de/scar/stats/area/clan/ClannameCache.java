package de.scar.stats.area.clan;

import de.scar.stats.area.clan.objects.Clanname;
import de.scar.stats.area.clan.objects.Clantag;

import java.util.HashMap;
import java.util.stream.Collectors;

public class ClannameCache {

    private static HashMap<String, Clan> clannameCache = new HashMap<>();

    public static void cache(Clanname clanname, Clan clan) {
        clannameCache.put(clanname.value(), clan);
    }

    public static Clan getClan(Clanname clanname) {
        return clannameCache.get(clanname.value());
    }

    public static void remove(Clanname clanname) {
        clannameCache.remove(clanname.value());
    }

    public static boolean isClannameCached(String string) {
        return clannameCache.keySet().stream().filter(x->x.equalsIgnoreCase(string)).collect(Collectors.toList()).size() > 0;
    }

}