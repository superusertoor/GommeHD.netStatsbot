package de.scar.stats.area.clan;

import de.scar.stats.area.clan.enums.ClanRank;

public class ClanMember {

    private String name;
    private Clan clan;
    private ClanRank clanRank;

    public ClanMember(String name, Clan clan, ClanRank clanRank) {
        this.name = name;
        this.clan = clan;
        this.clanRank = clanRank;
    }

    public ClanMember get() {
        return this;
    }

    public ClanRank getClanRank() {
        return clanRank;
    }

    public Clan getClan() {
        return clan;
    }

    public String getName() {
        return name;
    }

}