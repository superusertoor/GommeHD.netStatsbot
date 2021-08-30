package de.scar.stats.area.clan.oriented;

import de.scar.stats.area.clan.ClanMember;
import de.scar.stats.area.game.PlayerGameStatistic;

import java.util.ArrayList;

public class GamemodeOrientedClanMember extends ClanMember {

    private PlayerGameStatistic statistics;

    public GamemodeOrientedClanMember(ClanMember clanMember, String gameMode) {
        super(clanMember.getName(), clanMember.getClan(), clanMember.getClanRank());
        ArrayList<String> list = new ArrayList<>();
        list.add(gameMode);
        statistics = new PlayerGameStatistic(list, clanMember.getName());
    }

    public PlayerGameStatistic getStatistics() {
        return statistics;
    }
}
