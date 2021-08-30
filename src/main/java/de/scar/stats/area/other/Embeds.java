package de.scar.stats.area.other;

import de.scar.stats.area.guild.Server;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class Embeds {

    private Server server;
    private String prefix;

    private EmbedBuilder help= new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");
    private EmbedBuilder clanHelp= new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");
    private EmbedBuilder inviteCommand= new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");
    private EmbedBuilder botHelp= new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");
    private EmbedBuilder statsHelp = new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");
    private EmbedBuilder otherHelp = new EmbedBuilder().setColor(Color.decode("0x2F3136")).setAuthor("GommeHD.net Stats");

    public Embeds(Server server) {
        this.server = server;
        this.prefix = server.getCommandPrefix();
        createEmbeds();
    }

    public Embeds send(EmbedBuilder embedBuilder, TextChannel textChannel) {
        textChannel.sendMessage(embedBuilder.build()).queue();
        return this;
    }

    private void createEmbeds() {
        help.addField("Stats", "```!Help Stats```", true);
        help.addField("Clans", "```!Help Clans```", true);
        help.addField("General", "```!Help Bot```", true);
        help.addField("Other", "```!Help Other```", true);
        help.setThumbnail("https://cdn.discordapp.com/avatars/868154474793930772/590bd99ab05fc1b67051dc145ba34b06.webp?size=512");

        clanHelp.addField("Clans", "```!Clans```", false);
        clanHelp.addField("Clans", "```!Clan [tinfo/ninfo] [name/tag]```", false);
        clanHelp.addField("Clans", "```!Clan [tinfo/ninfo] [name/tag] [Spielmodus] [Statistik]```", false);
        clanHelp.addField("Clans", "```!Clan history [name/tag] [seite]```", true);
        clanHelp.setThumbnail("https://cdn.discordapp.com/avatars/868154474793930772/590bd99ab05fc1b67051dc145ba34b06.webp?size=512");

        statsHelp.addField("Stats", "```!Stats [Spielername] [Spielmodus]```", false);
        statsHelp.setThumbnail("https://cdn.discordapp.com/avatars/868154474793930772/590bd99ab05fc1b67051dc145ba34b06.webp?size=512");

        inviteCommand.setTitle("Invite Link", "https://discord.com/oauth2/authorize?client_id=868154474793930772&scope=bot&permissions=8");
        inviteCommand.setThumbnail("https://cdn.discordapp.com/avatars/868154474793930772/590bd99ab05fc1b67051dc145ba34b06.webp?size=512");

        otherHelp.addField("Online", "```!Online```", true);
        otherHelp.addField("Name History", "```!Namehistory <Name>```", true);
        otherHelp.addField("Optifine Cape", "```!Cape <Name>```", true);
        otherHelp.setThumbnail("https://cdn.discordapp.com/avatars/868154474793930772/590bd99ab05fc1b67051dc145ba34b06.webp?size=512");

        botHelp.addField("Developer", "```hannes#8632```", false);
        botHelp.addField("!Invite", "```Erhalte einen Einladungslink für den Bot```", false);
        botHelp.setThumbnail("https://cdn.discordapp.com/avatars/833147580044017706/6aa6cd9b99b37eb044f93711c85c7248.webp?size=256");
    }

    public EmbedBuilder getHelp() {
        return help;
    }

    public EmbedBuilder getClanHelp() {
        return clanHelp;
    }

    public EmbedBuilder getInviteCommand() {
        return inviteCommand;
    }

    public EmbedBuilder getBotHelp() {
        return botHelp;
    }

    public EmbedBuilder getStatsHelp() {
        return statsHelp;
    }

    public EmbedBuilder getOtherHelp() {
        return otherHelp;
    }
}