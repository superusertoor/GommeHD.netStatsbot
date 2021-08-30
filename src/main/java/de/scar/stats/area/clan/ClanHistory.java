package de.scar.stats.area.clan;

import de.scar.stats.discord.exceptions.ClanNotFoundException;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.awt.*;
import java.io.IOException;

public class ClanHistory {

    private EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.decode("0x2F3136"));
    private String clanName;
    private Document document;
    private TextChannel textChannel;
    private int page;

    public void retrieve() throws ClanNotFoundException {
        try {
            document = Jsoup.connect("https://www.gommehd.net/clan-profile/history/?name=" + clanName + "&page=" + page).get();
        } catch (IOException exception) {
            throw new ClanNotFoundException();
        }
        embedBuilder.setTitle(clanName + " (Seite " + page + ")", "https://www.gommehd.net/clan-profile/history/?name=" + clanName);
        if (document.title().contains("Not Found")) {
            throw new ClanNotFoundException();
        }
        Element history = document.getElementsByTag("tbody").get(0);
        history.getElementsByTag("tr").forEach(el -> {
            Elements elements = el.getElementsByAttributeValueNot("class", "col-md-1;");
            if (elements.size() > 0) {
                try {
                    Element changelog = elements.get(0);
                    final String text = changelog.text();
                    final String date = text.split(" ")[0];
                    final String time = text.split(" ")[1];
                    final String content = text.split(time)[1].substring(1);
                    embedBuilder.appendDescription("```" + date + ", " + time + ": " + content + "```");
                }catch(ArrayIndexOutOfBoundsException e ){

                }
            }
        });
        send();
    }

    public ClanHistory(final String clanName, int page, TextChannel textChannel) {
        this.clanName = clanName;
        this.textChannel = textChannel;
        this.page = page;
    }

    public void send() {
        textChannel.sendMessage(embedBuilder.build()).queue();
    }
}