package de.scar.stats.discord.guild.commands.mojang;

import com.google.common.collect.Lists;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.CustomEmbed;
import jdk.internal.util.xml.impl.ReaderUTF8;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.mojangapi.MojangAPI;
import me.kbrewster.mojangapi.authentication.AuthenticatedUser;
import me.kbrewster.mojangapi.profile.Name;
import me.kbrewster.mojangapi.profile.Profile;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

public class MojangCommands extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMember().getUser().isBot()) {
            return;
        }

        final Server server = ServerHandler.getServer(event.getGuild());
        final String prefix = server.getCommandPrefix();

        if(event.getMessage().getContentDisplay().equalsIgnoreCase("!skin")) {
            new CustomEmbed().error("Benutze: !Skin <Name>");
            return;
        }

        if(event.getMessage().getContentDisplay().toLowerCase().startsWith(prefix+"skin ")) {
            String n = event.getMessage().getContentDisplay().toLowerCase().split(prefix + "skin ")[1];
            try {
                UUID uuid = MojangAPI.getUUID(n);
                Document d = null;
                try {
                    String json = Jsoup.connect("https://api.capes.dev/load/" + n).ignoreContentType(true).ignoreHttpErrors(true).get().wholeText();
                    JSONObject jsonObject = new JSONObject(json);
                    new CustomEmbed()
                            //.thumbnail("https://minotar.net/cube/" + n + "/512.png")
                            .thumbnail(jsonObject.getJSONObject("optifine").getJSONObject("imageUrls").getJSONObject("base").getString("front") + ".png")
                            //.img("https://crafatar.com/renders/body/" + uuid + ".png?size=512&overlay").title(n + "'s Skin")
                            .img("https://mc-heads.net/body/" + uuid.toString().replaceAll("-", "") + "/128.png")
                            .send(event.getTextChannel());
                } catch (IOException exception) {
                    new CustomEmbed().error("Der angegebene Spieler existiert nicht").send(event.getTextChannel());
                }
            } catch (IOException exception) {
                new CustomEmbed().error("Ein Fehler ist aufgetreten").send(event.getTextChannel());
            } catch (APIException e) {
                new CustomEmbed().error("Der angegebene Spieler existiert nicht").send(event.getTextChannel());
            }
        }else if(event.getMessage().getContentDisplay().toLowerCase().startsWith(prefix+"cape ")) {
            String n = event.getMessage().getContentDisplay().toLowerCase().split(prefix + "cape ")[1];
            try {
                String json = Jsoup.connect("https://api.capes.dev/load/" + n).ignoreContentType(true).ignoreHttpErrors(true).get().wholeText();
                JSONObject jsonObject = new JSONObject(json);
                try {
                    new CustomEmbed()
                            .img(jsonObject.getJSONObject("optifine").getJSONObject("imageUrls").getJSONObject("base").getString("front") + ".png").send(event.getTextChannel());
                }catch(JSONException e) {
                    new CustomEmbed().error(n + " hat kein Cape").send(event.getTextChannel());
                }
            } catch (IOException exception) {
                new CustomEmbed().error("Der angegebene Spieler existiert nicht").send(event.getTextChannel());
            }
        }else if(event.getMessage().getContentDisplay().toLowerCase().startsWith(prefix+"namehistory ")) {
            String n = event.getMessage().getContentDisplay().split(prefix+"namehistory ")[1];

            EmbedBuilder embedBuilder = new EmbedBuilder();
            embedBuilder.setColor(Color.decode("0x2F3136"));
            try {
                embedBuilder.setThumbnail("https://mc-heads.net/body/" + MojangAPI.getUUID(n).toString().replaceAll("-", "") + "/128.png");
                embedBuilder.setAuthor(n + "'s Namehistory");

                try {
                    List<Name> list = Lists.reverse(MojangAPI.getNameHistory(n));
                    AtomicInteger i = new AtomicInteger(list.size());
                    list.forEach(s -> {
                        if(s.getChangedToAt() > 0) {
                            embedBuilder.appendDescription("```" + i.get() + ") " + new SimpleDateFormat("dd.MM.yyyy @ hh:mm:ss").format(s.getChangedToAt()) + " | " + s.getName() + "```\n");
                        }else {
                            embedBuilder.appendDescription("```" + i.get() + ") " + s.getName() + "```\n");
                        }
                        i.getAndDecrement();
                    });
                    event.getTextChannel().sendMessage(embedBuilder.build()).queue();
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (APIException e) {
                e.printStackTrace();
            }
            } catch (IOException exception) {
                new CustomEmbed().error("Ein Fehler ist aufgetreten").send(event.getTextChannel());
            } catch (APIException e) {
                new CustomEmbed().error("Der angegebene Spieler existiert nicht").send(event.getTextChannel());
            }catch(InvalidPlayerException e) {
                new CustomEmbed().error("Der angegebene Spieler existiert nicht").send(event.getTextChannel());
            }
        }
    }
}