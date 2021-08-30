package de.scar.stats.area.guild;

import com.machinepublishers.jbrowserdriver.JBrowserDriver;
import com.machinepublishers.jbrowserdriver.Settings;
import de.scar.stats.area.other.Embeds;
import net.dv8tion.jda.api.entities.Guild;

public class Server {

    private Guild guild;
    private JBrowserDriver driver;
    private Embeds embeds;

    public Server(Guild guild) {
        this.guild = guild;
        driver = new JBrowserDriver(Settings.builder().blockAds(true).cache(true).build());
        driver.manage().ime().deactivate();
        driver.get("https://www.gommehd.net/clans");
        embeds = new Embeds(this);
    }

    public Embeds embeds() {
        return embeds;
    }

    public String getCommandPrefix() {
        return "!";
    }

    public JBrowserDriver getDriver() {
        return driver;
    }

    public Guild getGuild() {
        return guild;
    }

    public void uncache() {
        ServerHandler.uncacheServer(this);
    }

    public void cache() {
        ServerHandler.cacheServer(this);
    }
}