package de.scar.stats.area.other;

import de.scar.stats.area.association.Association;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public class CustomEmbed {

    private EmbedBuilder embedBuilder = new EmbedBuilder().setColor(Color.decode("0x2F3136")).setTitle("GommeHD.net Stats");

    public CustomEmbed() {

    }

    public CustomEmbed association(Member member, Association association) {
        embedBuilder.setDescription("Dein Account wurde verknüpft.");
        embedBuilder.addField("Discord", "```" + member.getUser().getAsTag() + "```", true);
        embedBuilder.addField("Minecraft", "```" + association.getName() + "```", true);
        return this;
    }

    public CustomEmbed error(String error) {
        embedBuilder.setColor(Color.decode("0x330000"));
        embedBuilder.addField("Fehler", "```" + error + "```", true);
        return this;
    }

    public CustomEmbed desc(String desc) {
        embedBuilder.appendDescription(desc);
        return this;
    }

    public CustomEmbed f(String k, String v) {
        embedBuilder.addField(k, v, true);
        return this;
    }

    public CustomEmbed thumbnail(String url) {
        embedBuilder.setThumbnail(url);
        return this;
    }

    public CustomEmbed img(String url) {
        embedBuilder.setImage(url);
        return this;
    }

    public CustomEmbed author(String s) {
        embedBuilder.setAuthor(s);
        return this;
    }

    public CustomEmbed title(String string, String url) {
        embedBuilder.setTitle(string, url);
        return this;
    }

    public CustomEmbed title(String string) {
        embedBuilder.setTitle(string);
        return this;
    }

    public CustomEmbed color(Color color) {
        embedBuilder.setColor(color);
        return this;
    }

    public void send(TextChannel textChannel) {
        textChannel.sendMessage(embedBuilder.build()).queue();
    }
}