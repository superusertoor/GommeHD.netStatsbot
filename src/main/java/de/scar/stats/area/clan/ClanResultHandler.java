package de.scar.stats.area.clan;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import de.scar.stats.area.clan.enums.ClanType;
import de.scar.stats.area.clan.objects.Clanname;
import de.scar.stats.area.clan.objects.Clantag;
import de.scar.stats.discord.exceptions.ClanNotFoundException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.ArrayList;
import java.util.List;

public class ClanResultHandler {

    public static String script = " $(\"#searchSubmitButton\").html('<i class=\"fa fa-spinner fa-spin\"></i>');\n" +
            "        $(\"#searchSubmitButton\").attr(\"disabled\", \"disabled\");\n" +
            "        $(\"#searchSubmitButton\").css(\"background-color\", \"#fff\");\n" +
            "        $(\"#searchSubmitButton\").css(\"border-color\", \"#fff\");\n" +
            "        $(\"#searchQuery\").attr(\"disabled\", \"disabled\");\n" +
            "        $(\"#searchQuery\").css(\"background-color\", \"#fff\");\n" +
            "        $(\"#searchValue\").val('{\"page\": 1, \"gamemode\": \"null\", \"type\": \"TYPEHERE\", \"query\": \"" + "CLANHERE" + "\", \"filters\":[]}');\n" +
            "        $(\"#searchForm\").submit();";


    public static List<ClanSearchResult> getResults(JBrowserDriver driver, ClanType searchType, String input) throws ClanNotFoundException{
        List<ClanSearchResult> results = new ArrayList<>();
        if (!driver.getTitle().contains("Dein Minecraft Netzwerk")) {
            driver.get("https://www.gommehd.net/clans/search-clan");
        }
        driver.executeScript(script.replace("TYPEHERE", searchType.name()).replace("CLANHERE", input));
        driver.pageWait();

        Document document = Jsoup.parse(driver.getPageSource());

        final int resultsSize = Integer.parseInt(document.getElementsContainingOwnText("Suchergebnisse").first().ownText().split("\\(")[1].replace(")", ""));

        if(resultsSize > 0) {
            document.getElementsByClass("list-group-item").forEach(element -> {
                results.add(new ClanSearchResult(new Clanname(element.getElementsByTag("span").get(0).ownText()), new Clantag(element.getElementsByClass("clanTag").text().replace("[", "").replace("]", ""))));
            });
        }else {
            throw new ClanNotFoundException();
        }
        return results;
    }
}