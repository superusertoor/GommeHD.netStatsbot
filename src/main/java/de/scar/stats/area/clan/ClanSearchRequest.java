package de.scar.stats.area.clan;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import de.scar.stats.area.clan.enums.ClanType;
import de.scar.stats.discord.exceptions.ClanNotFoundException;

import java.util.List;

public class ClanSearchRequest {

    private JBrowserDriver driver;
    private String searchInput;
    private List<ClanSearchResult> clanSearchResults;

    public ClanSearchRequest(String searchInput, ClanType searchType, JBrowserDriver driver) throws ClanNotFoundException {
        this.driver = driver;
        this.searchInput = searchInput;
        this.clanSearchResults = ClanResultHandler.getResults(driver, searchType, searchInput);
    }

    public List<ClanSearchResult> getClanSearchResults() {
        return clanSearchResults;
    }

    public ClanSearchResult getTopSearchResult() {
        return clanSearchResults.get(0);
    }
}