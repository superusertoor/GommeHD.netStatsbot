package de.scar.stats.area.game;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

public class GommeHDNetwork {

    public static Integer getOnlineCount() {
        JBrowserDriver driver = new JBrowserDriver();
        driver.get("https://www.gommehd.net/");
        Document d = Jsoup.parse(driver.getPageSource());
        AtomicReference<String> online = new AtomicReference<>("");
        d.getElementsByClass("player-count").forEach(el -> {
            el.getElementsByTag("span").forEach(el1 -> {
                online.set(online.get() + el1.text());
            });
        });
        return Integer.valueOf(online.get());
    }


    public static Integer getClansCount() {
        try {
            Document document = Jsoup.connect("https://www.gommehd.net/clans").get();
            Element element = document.getElementById("total-clans");
            String el = element.toString().split("data-total-clans")[1].split(" style=")[0].substring(2);
            String clans = el.substring(0, el.length() - 1);
            return Integer.valueOf(clans);
        } catch (IOException e) {
            return null;
        }
    }
}