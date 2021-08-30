package de.scar.stats.discord.guild.commands.gomme;

import de.scar.stats.area.clan.*;
import de.scar.stats.area.clan.enums.ClanType;
import de.scar.stats.area.clan.objects.Clantag;
import de.scar.stats.area.clan.oriented.GamemodeOrientedClan;
import de.scar.stats.area.game.GommeHDNetwork;
import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.CustomEmbed;
import de.scar.stats.area.other.Embeds;
import de.scar.stats.discord.exceptions.ClanNotFoundException;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Timer;
import java.util.TimerTask;

public class ClanCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if (event.getMember().getUser().isBot()) {
            return;
        }

        String msg = event.getMessage().getContentDisplay();
        final Server server = ServerHandler.getServer(event.getGuild());
        final String prefix = server.getCommandPrefix();
        final Embeds embeds = server.embeds();

        if (msg.equalsIgnoreCase(prefix+"Clans")) {
            event.getTextChannel().sendTyping().queue();
            new CustomEmbed().title(String.valueOf(GommeHDNetwork.getClansCount()), "https://www.gommehd.net/clans").author("Clans").send(event.getTextChannel());
            return;
        }

        if (msg.equalsIgnoreCase(prefix+"Online")) {
            event.getTextChannel().sendTyping().queue();
            new CustomEmbed().title(String.valueOf(GommeHDNetwork.getOnlineCount()), "https://www.gommehd.net/").author("Spieler").send(event.getTextChannel());
            return;
        }

        if(msg.equalsIgnoreCase(prefix+"Clan")) {
            event.getTextChannel().sendTyping().queue();
            embeds.send(embeds.getClanHelp(), event.getTextChannel());
            return;
        }

        if (msg.toLowerCase().startsWith(prefix+"clan ")) {
            String[] args = msg.substring(6).split(" ");
            if(args.length == 2) {
                final String input = args[1];
                Clan clan;
                event.getTextChannel().sendTyping().queue();
                if (args[0].equalsIgnoreCase("tinfo")) {
                    if(!ClantagCache.isClanTagCached(input)) {
                        clan = new Clan(ClanType.CLAN_TAG, input, server.getDriver());
                        clan.fetchClan();
                        clan.createEmbed();
                        event.getTextChannel().sendMessage(clan.toEmbed()).queue();
                        clan.setCached();
                        ClantagCache.cache(clan.getClantag(), clan);
                        ClannameCache.cache(clan.getClanname(), clan);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                ClantagCache.remove(clan.getClantag());
                                ClannameCache.remove(clan.getClanname());
                            }
                        }, 1000*60*10);
                    }else {
                        Clantag ctag = new Clantag(input);
                        clan = ClantagCache.getClan(ctag);
                        event.getTextChannel().sendMessage(clan.toEmbed()).queue();
                    }

                } else if (args[0].equalsIgnoreCase("ninfo")) {
                    if(!ClannameCache.isClannameCached(input)) {
                        clan = new Clan(ClanType.CLAN_TAG, input, server.getDriver());
                        clan.fetchClan();
                        clan.createEmbed();
                        event.getTextChannel().sendMessage(clan.toEmbed()).queue();
                        clan.setCached();
                        ClantagCache.cache(clan.getClantag(), clan);
                        ClannameCache.cache(clan.getClanname(), clan);
                        new Timer().schedule(new TimerTask() {
                            @Override
                            public void run() {
                                ClantagCache.remove(clan.getClantag());
                                ClannameCache.remove(clan.getClanname());
                            }
                        }, 1000*60*10);
                    }else {
                        Clantag ctag = new Clantag(input);
                        clan = ClantagCache.getClan(ctag);
                        event.getTextChannel().sendMessage(clan.toEmbed()).queue();
                    }

                } else if (args[0].equalsIgnoreCase("history")) {
                    event.getTextChannel().sendTyping().queue();
                    ClanHistory history;
                    try {
                        history = new ClanHistory(args[1], 1, event.getTextChannel());
                        history.retrieve();
                    } catch (ClanNotFoundException e) {
                        new CustomEmbed().error(e.getMessage()).send(event.getTextChannel());
                    }
                } else {
                    embeds.send(embeds.getClanHelp(), event.getTextChannel());
                }
            }else if(args.length == 3) {
                if(args[0].equalsIgnoreCase("history")) {
                    event.getTextChannel().sendTyping().queue();
                    int i = 0;
                    try {
                        i = Integer.parseInt(args[2]);
                        try {
                            new ClanHistory(args[1], i, event.getTextChannel()).retrieve();
                        } catch (ClanNotFoundException e) {
                            new CustomEmbed().error(e.getMessage()).send(event.getTextChannel());
                        }
                    }catch(NumberFormatException e) {
                        new CustomEmbed().error(i + " ist keine gültige Zahl").send(event.getTextChannel());
                    }
                }else {
                    embeds.send(embeds.getClanHelp(), event.getTextChannel());
                }
            }else if(args.length == 4) {
                final String input = args[1]; // clan
                final String gamemode = args[2]; // gamemode
                final String entry = args[3]; // entry
                GamemodeOrientedClan clan = null;
                event.getTextChannel().sendMessage("Das kann jetzt 'ne Weile dauern...").queue();
                event.getTextChannel().sendTyping().queue();
                if(args[0].equalsIgnoreCase("tinfo")) {
                    clan = new GamemodeOrientedClan(ClanType.CLAN_TAG, input, server.getDriver(), gamemode, entry);
                    clan.fetchClan();
                    clan.createEmbed();
                }else if(args[0].equalsIgnoreCase("ninfo")) {
                    clan = new GamemodeOrientedClan(ClanType.CLAN_NAME, input, server.getDriver(), gamemode, entry);
                    clan.fetchClan();
                    clan.createEmbed();
                }else {
                    embeds.send(embeds.getClanHelp(), event.getTextChannel());
                }
                event.getTextChannel().sendMessage(clan.toEmbed()).queue();
            }else {
                embeds.send(embeds.getClanHelp(), event.getTextChannel());
            }
        return;
        }
    }
}
