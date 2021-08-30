package de.scar.stats.area.clan.oriented;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import de.scar.stats.area.clan.Clan;
import de.scar.stats.area.clan.ClanMember;
import de.scar.stats.area.clan.enums.ClanRank;
import de.scar.stats.area.clan.enums.ClanType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;

public class GamemodeOrientedClan extends Clan {

    private Document document;
    private String statistic;
    private String gamemode;

    public GamemodeOrientedClan(ClanType searchType, String input, JBrowserDriver driver, String gamemode, String statistic) {
        super(searchType, input, driver);
        this.gamemode = gamemode;
        this.statistic = statistic;
    }

    @Override
    public List<ClanMember> fetchClan() {
        super.getDriver().get(super.getTopResult().getClanPageUrl());
        document = Jsoup.parse(super.getDriver().getPageSource());
        try {
            Document doc = Jsoup.connect("https://www.gommehd.net/clan-profile/members/?name=" + super.getClanname().value()).get();
            doc.getElementsByClass("avatarContainer").get(0).getElementsByTag("img").forEach(e -> {
                super.sources.add(e.absUrl("src"));
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Element memberOverview = document.getElementById("clanMembersList");
        for (Element el : memberOverview.getAllElements()) {
            if (el.hasClass("panel-title")) {
                String title = el.getElementsByClass("panel-title").text();
                if (title.equalsIgnoreCase("Clan Leader")) {
                    el.parent().parent().getElementsByClass("media").forEach(element -> {
                        memberList.add(new GamemodeOrientedClanMember(new ClanMember(element.getElementsByClass("media-heading name").text(), super.get(), ClanRank.LEADER), gamemode));
                    });
                } else if (title.equalsIgnoreCase("Clan Mods")) {
                    el.parent().parent().getElementsByClass("media").forEach(element -> {
                        memberList.add(new GamemodeOrientedClanMember(new ClanMember(element.getElementsByClass("media-heading name").text(), super.get(), ClanRank.MODERATOR), gamemode));
                    });
                } else if (title.equalsIgnoreCase("Clan Member")) {
                    el.parent().parent().getElementsByClass("media").forEach(element -> {
                        memberList.add(new GamemodeOrientedClanMember(new ClanMember(element.getElementsByClass("media-heading name").text(), super.get(), ClanRank.MEMBER), gamemode));
                    });
                }
            }
        }
        super.getDriver().get("https://www.gommehd.net/clans");
        return memberList;
    }

    @Override
    public String generateList(List<ClanMember> list, String key) {
        StringBuilder stringBuilder = new StringBuilder();
        if(list.size() > 0) {
            stringBuilder.append("\n**" + key + "** (" + list.size() + ")");
            list.parallelStream().forEach(cm -> {
                GamemodeOrientedClanMember gocm = new GamemodeOrientedClanMember(cm, gamemode);
                stringBuilder.append("\n ∙ " + cm.getName().replaceAll("_", "\\\\_") + " hat **" + new DecimalFormat().format(Double.valueOf(gocm.getStatistics().getValue(gamemode, statistic))) + "** " + statistic.toUpperCase());
            });
        }else {
            stringBuilder.append("\n**" + key + "**\n ∙ **Keine " + key + " :(**");
        }
        return stringBuilder.toString();
    }
}