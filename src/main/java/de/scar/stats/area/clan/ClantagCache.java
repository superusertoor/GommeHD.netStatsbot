package de.scar.stats.area.clan;

import de.scar.stats.area.clan.objects.Clantag;

import java.util.HashMap;
import java.util.stream.Collectors;

public class ClantagCache {

    private static HashMap<String, Clan> clantagCache = new HashMap<>();

    public static void cache(Clantag clantag, Clan clan) {
        clantagCache.put(clantag.value(), clan);
    }
    public static Clan getClan(Clantag clantag) {
        return clantagCache.get(clantag.value());
    }

    public static void remove(Clantag clantag) {
        clantagCache.remove(clantag.value());
    }

    public static boolean isClanTagCached(String string) {
        return clantagCache.keySet().stream().filter(x->x.equalsIgnoreCase(string)).collect(Collectors.toList()).size() > 0;
    }
}