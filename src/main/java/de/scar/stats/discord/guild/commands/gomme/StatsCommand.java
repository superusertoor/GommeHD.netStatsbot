package de.scar.stats.discord.guild.commands.gomme;

import de.scar.stats.area.association.Association;
import de.scar.stats.area.association.AssociationsHandler;
import de.scar.stats.area.game.PlayerGameStatistic;
import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.CustomEmbed;
import de.scar.stats.area.other.Embeds;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getMember().getUser().isBot()) {
            return;
        }

        final Server server = ServerHandler.getServer(e.getGuild());
        final String prefix = server.getCommandPrefix();
        final Embeds embeds = server.embeds();

        String msg = e.getMessage().getContentDisplay();
        if (msg.equalsIgnoreCase(prefix+"Stats")) {
            embeds.send(embeds.getStatsHelp(), e.getTextChannel());
            return;
        }

        if (msg.toLowerCase().startsWith(prefix+"stats ")) {
            String[] args = msg.substring(7).split(" ");

            if(args.length == 1) {
                if(AssociationsHandler.isUserAssociated(e.getMember().getIdLong())) {
                    Association association = AssociationsHandler.getAssociation(e.getMember().getIdLong());
                    ArrayList<String> list = new ArrayList<>();
                    list.add(args[0]);
                    PlayerGameStatistic stats = new PlayerGameStatistic(list, association.getName());
                    if (stats.isValid()) {
                        stats.send(e.getTextChannel());
                    }else {
                        new CustomEmbed().error(stats.getErrorMessage()).send(e.getTextChannel());
                    }
                }else {
                    new CustomEmbed().error("Du hast keinen Minecraft Account mit deinem Discord Account verknüpft").send(e.getTextChannel());
                }
            }else if (!(args.length >= 2)) {
                embeds.send(embeds.getStatsHelp(), e.getTextChannel());
            } else {
                String username = args[0];

                List<String> arguments = new ArrayList<>(Arrays.asList(msg.split(" ")));
                ArrayList<String> list = new ArrayList<>();
                for(int i = 2; i < arguments.size(); i++) {
                    list.add(arguments.get(i));
                }

                PlayerGameStatistic stats = new PlayerGameStatistic(list, username);
                if (stats.isValid()) {
                    stats.send(e.getTextChannel());
                }else {
                    new CustomEmbed().error(stats.getErrorMessage()).send(e.getTextChannel());
                }
            }
        }
    }
}
