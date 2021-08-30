package de.scar.stats.area.game;

public class Alias {

    String outputRaw = "";
    String beautified = "";

    public Alias(String string) {
        switch(string.toLowerCase()) {
            case "bw":
                outputRaw = "bedwars";
                beautified = "BedWars";
                break;
            case "bedwars":
                outputRaw = "bedwars";
                beautified = "BedWars";
                break;
            case "gg":
                outputRaw = "gungame";
                beautified = "GunGame";
                break;
            case "gungame":
                outputRaw = "gungame";
                beautified = "GunGame";
                break;
            case "ffa":
                outputRaw = "hardcore";
                beautified = "FFA";
                break;
            case "hc":
                outputRaw = "hardcore";
                beautified = "FFA";
                break;
            case "hardcore":
                outputRaw = "hardcore";
                beautified = "FFA";
                break;
        }
    }

    public String getBeautified() {
        return beautified;
    }

    public String getOutputRaw() {
        return outputRaw;
    }

}
