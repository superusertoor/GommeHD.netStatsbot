package de.scar.stats.area.clan.objects;

public class ClanDescription {

    private String value;

    public ClanDescription(String value) {
        this.value = value;
        this.value.replaceAll("_", "\\\\_")
                .replaceAll("\\*", "\\\\*")
                .replaceAll("~~", "\\\\~~");
    }

    public String value() {
        return value;
    }

    public ClanDescription get() {
        return this;
    }
}
