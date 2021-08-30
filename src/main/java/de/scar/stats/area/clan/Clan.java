package de.scar.stats.area.clan;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.sun.security.auth.callback.TextCallbackHandler;
import de.scar.stats.area.clan.enums.ClanRank;
import de.scar.stats.area.clan.enums.ClanType;
import de.scar.stats.area.clan.objects.ClanDescription;
import de.scar.stats.area.clan.objects.Clanname;
import de.scar.stats.area.clan.objects.Clantag;
import de.scar.stats.discord.exceptions.ClanNotFoundException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

public class Clan {

    private JBrowserDriver driver;
    private Document document = null;
    private EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.decode("0x2F3136"));
    public List<ClanMember> memberList = new ArrayList<>();
    private List<Element> elements = new ArrayList<>();
    private ClanSearchRequest searchRequest;
    private ClanSearchResult topResult;
    public List<String> sources = new ArrayList<>();
    private Clantag clantag;
    private Clanname clanname;
    private ClanDescription clanDescription;
    private boolean found = true;

    public Clan(ClanType searchType, String input, JBrowserDriver driver){
        this.driver = driver;
        try {
            this.searchRequest = new ClanSearchRequest(input, searchType, driver);
            this.topResult = searchRequest.getTopSearchResult();
            this.clantag = topResult.getClanTag();
            this.clanname = topResult.getClanName();
            this.clanDescription = topResult.getClanDescription();
        }catch (ClanNotFoundException e) {
            found = false;
        }
    }

    public Clan(JBrowserDriver driver, Clanname clanname, Clantag clantag) {
        this.driver = driver;
        this.searchRequest = null;
        this.topResult = new ClanSearchResult(clanname, clantag);
        this.clantag = topResult.getClanTag();
        this.clanname = topResult.getClanName();
        this.clanDescription = topResult.getClanDescription();
        fetchClan();
    }

    public List<ClanMember> fetchClan() {
        driver.get(topResult.getClanPageUrl());
        document = Jsoup.parse(driver.getPageSource());
        try {
            Document doc = Jsoup.connect("https://www.gommehd.net/clan-profile/members/?name=" + topResult.getClanName().value()).get();
            doc.getElementsByClass("avatarContainer").get(0).getElementsByTag("img").forEach(e -> {
                String url = e.absUrl("src");
                if(!url.equalsIgnoreCase("https://www.gommehd.net/images/noimage.png")) {
                    sources.add(e.absUrl("src"));
                }
            });
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        Element memberOverview = document.getElementById("clanMembersList");
        memberOverview.getAllElements().parallelStream().forEach(el -> {
            if (el.hasClass("panel-title")) {
                String title = el.getElementsByClass("panel-title").text();
                if (title.equalsIgnoreCase("Clan Leader")) {
                    el.parent().parent().getElementsByClass("media").forEach(element -> {
                        memberList.add(new ClanMember(element.getElementsByClass("media-heading name").text(), this, ClanRank.LEADER));
                    });
                } else if (title.equalsIgnoreCase("Clan Mods")) {
                    el.parent().parent().getElementsByClass("media").forEach(element -> {
                        memberList.add(new ClanMember(element.getElementsByClass("media-heading name").text(), this, ClanRank.MODERATOR));
                    });
                } else if (title.equalsIgnoreCase("Clan Member")) {
                    el.parent().parent().getElementsByClass("media").forEach(element -> {
                        memberList.add(new ClanMember(element.getElementsByClass("media-heading name").text(), this, ClanRank.MEMBER));
                    });
                }
            }
        });
        driver.get("https://www.gommehd.net/clans");
        return memberList;
    }

    public void createEmbed() {
        embedBuilder.setAuthor("GommeHD.net Stats");
        if (!found) {
            embedBuilder.setDescription("Der Clan wurde nicht gefunden");
        }else {
            embedBuilder.setTitle(topResult.getClanName().value() + " [" + topResult.getClanTag().value() + "]", topResult.getClanPageUrl());
            embedBuilder.appendDescription("**Name:** " + topResult.getClanName().value().replaceAll("_", "\\\\_"));
            embedBuilder.appendDescription("\n**Tag:** " + topResult.getClanTag().value().replaceAll("_", "\\\\_"));
            embedBuilder.appendDescription("\n**Member:** " + getAllClanMembers().size());
            embedBuilder.appendDescription("\n**Beschreibung:** " + getDescription().value() + "\n");
            embedBuilder.appendDescription(generateList(getLeaders(), "Leader"));
            embedBuilder.appendDescription(generateList(getModerators(), "Moderatoren"));
            embedBuilder.appendDescription(generateList(getMembers(), "Member"));
            if (sources.size() == 2) {
                embedBuilder.setThumbnail(sources.get(0));
                embedBuilder.setImage(sources.get(1));
            } else if (sources.size() == 1) {
                embedBuilder.setImage(sources.get(0));
            }
        }
    }

    public String generateList(List<ClanMember> list, String key) {
        StringBuilder stringBuilder = new StringBuilder();
        if(list.size() > 0) {
            stringBuilder.append("\n**" + key + "** (" + list.size() + ")");
            list.forEach(cm -> {
                stringBuilder.append("\n ∙ " + cm.getName().replaceAll("_", "\\\\_"));
            });
        }else {
            stringBuilder.append("\n**" + key + "**\n ∙ **Keine " + key + " :(**");
        }
        return stringBuilder.toString();
    }

    public void setCached() {
        embedBuilder.setFooter("Die Statistiken dieser Übersicht könnten abweichen");
    }

    public Clan get() {
        return this;
    }

    public JBrowserDriver getDriver() {
        return driver;
    }

    public ClanSearchResult getTopResult() {
        return topResult;
    }

    public ClanDescription getDescription() {
        return clanDescription;
    }

    public Clantag getClantag() {
        return clantag;
    }

    public Clanname getClanname() {
        return clanname;
    }

    public List<ClanMember> getAllClanMembers() {
        return memberList;
    }

    public List<ClanMember> getLeaders() {
        return memberList.stream()
                .filter(x -> x.getClanRank().equals(ClanRank.LEADER))
                .collect(Collectors.toList());
    }

    public List<ClanMember> getModerators() {
        return memberList.stream()
                .filter(x -> x.getClanRank().equals(ClanRank.MODERATOR))
                .collect(Collectors.toList());
    }

    public List<ClanMember> getMembers() {
        return memberList.stream()
                .filter(x -> x.getClanRank().equals(ClanRank.MEMBER))
                .collect(Collectors.toList());
    }

    public MessageEmbed toEmbed() {
        return embedBuilder.build();
    }
}