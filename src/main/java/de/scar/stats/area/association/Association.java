package de.scar.stats.area.association;

import de.scar.stats.area.clan.objects.Clantag;

import java.util.ArrayList;
import java.util.List;

public class Association {

    private String name;
    private boolean inClan;
    private Clantag clantag;
    private List<String> otherAccounts = new ArrayList<>();

    public Association(String name) {
        this.name = name;
        this.inClan = false;
    }

    public boolean isInClan() {
        return inClan;
    }

    public Association addAccount(String name) {
        otherAccounts.add(name);
        return this;
    }

    public Association setClan(Clantag clantag) {
        this.clantag = clantag;
        this.inClan = true;
        return this;
    }

    public Clantag getClantag() {
        return clantag;
    }

    public String getName() {
        return name;
    }

    public List<String> getOtherAccounts() {
        return otherAccounts;
    }

}
