package de.scar.stats.area.game;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayerGameStatistic extends Thread {

    private Document document;
    private Element element;
    private String userName;
    private ArrayList<String> gameModes;
    private EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");
    private boolean isValid = true;
    private String errorMessage = "";
    private List<Gamemode> gamemodes = new ArrayList<>();

    public PlayerGameStatistic(ArrayList<String> gamemodes, String userName) {
        this.gameModes = gamemodes;
        this.userName = userName;
        start();
    }

    @Override
    public void start() {
        try {
            document = Jsoup.connect("https://www.gommehd.net/player/index?playerName=" + userName).get();
            gameModes.forEach(gameMode -> {
                element = document.getElementById(new Alias(gameMode).getOutputRaw());
                if (element == null || document.title().equalsIgnoreCase("Statistiken")) {
                    isValid = false;
                    this.errorMessage = "Die Statistiken von " + userName + " konnten nicht geladen werden";
                    return;
                }
                Gamemode gamemode = new Gamemode(new Alias(gameMode));
                for(Element el : element.select("li")) {
                    String score = el.toString().split("score")[1].split("</span> <span ")[0].substring(2);
                    String mode = el.toString().split("></span> ")[1].split(" </li>")[0];
                    gamemode.add(mode, Integer.parseInt(score));
                }
                gamemodes.add(gamemode);
            });
        } catch (IOException exception) {
            isValid = false;
        }
    }

    public int getValue(String gamemode, String score) {
        AtomicInteger v = new AtomicInteger();
        getCachedGamemodes().forEach(gm -> {
            if(gamemode.equalsIgnoreCase(gamemode)) {
                System.out.println(1);
                gm.getEntries().forEach((mode, sc) -> {
                    System.out.println(2);
                    System.out.println(mode + "::" + score);
                    if(mode.equalsIgnoreCase(score)) {
                        System.out.println(3);
                        v.set(sc.intValue());
                    }
                });
            }
        });
        return v.get();
    }

    public boolean isValid() {
        return isValid;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public List<Gamemode> getCachedGamemodes() {
        return gamemodes;
    }

    public PlayerGameStatistic send(TextChannel textChannel) {
        embedBuilder.setTitle(userName, "https://www.gommehd.net/player/index?playerName=" + userName);
        embedBuilder.setThumbnail("https://minotar.net/armor/bust/" + userName + "/512.png");
        getCachedGamemodes().forEach(gamemode -> {
            embedBuilder.addField("Spielmodus", "```" + gamemode.getGamemodeName() + "```", true);
            gamemode.getEntries().forEach((key, score) -> {
                embedBuilder.addField(key, "```" + score + "```", true);
            });
            if(gamemode.getEntries().size() == 1) {
                embedBuilder.addBlankField(true);
            }
        });
        textChannel.sendMessage(embedBuilder.build()).queue();
        return this;
    }
}