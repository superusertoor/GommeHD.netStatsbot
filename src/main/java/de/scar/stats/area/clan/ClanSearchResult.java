package de.scar.stats.area.clan;

import de.scar.stats.area.clan.objects.ClanDescription;
import de.scar.stats.area.clan.objects.Clanname;
import de.scar.stats.area.clan.objects.Clantag;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ClanSearchResult {

    private Clanname clanName;
    private Clantag clanTag;
    private String clanPageUrl;
    private ClanDescription clanDescription;

    public ClanSearchResult(Clanname clanName, Clantag clanTag) {
        this.clanName = clanName;
        this.clanTag = clanTag;
        this.clanPageUrl = "https://www.gommehd.net/clan-profile/members/?name=" + clanName.value();
        try {
            Document descDoc = Jsoup.connect("https://www.gommehd.net/clan-profile?name=" + clanName.value()).get();
            clanDescription = new ClanDescription(descDoc.getElementsByClass("blockWrapper withPadding profileInfoText").get(0).text());
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ClanDescription getClanDescription() {
        return clanDescription;
    }

    public String getClanPageUrl() {
        return clanPageUrl;
    }

    public Clanname getClanName() {
        return clanName;
    }

    public Clantag getClanTag() {
        return clanTag;
    }
}
