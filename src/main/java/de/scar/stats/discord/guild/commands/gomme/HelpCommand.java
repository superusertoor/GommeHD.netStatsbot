package de.scar.stats.discord.guild.commands.gomme;

import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.Embeds;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class HelpCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getMember().getUser().isBot()) {
            return;
        }

        final Server server = ServerHandler.getServer(e.getGuild());
        final String prefix = server.getCommandPrefix();
        final Embeds embeds = server.embeds();

        String msg = e.getMessage().getContentDisplay();
        if (msg.equalsIgnoreCase(prefix+"help")) {
            embeds.send(embeds.getHelp(), e.getTextChannel());
        }

        if (msg.toLowerCase().startsWith(prefix+"help ")) {

            String[] args = msg.substring(6).split(" ");
            if (args.length >= 1) {
                if (args[0].equalsIgnoreCase("clans")) {
                    embeds.send(embeds.getClanHelp(), e.getTextChannel());
                } if (args[0].equalsIgnoreCase("other")) {
                    embeds.send(embeds.getOtherHelp(), e.getTextChannel());
                } else if (args[0].equalsIgnoreCase("stats")) {
                    embeds.send(embeds.getStatsHelp(), e.getTextChannel());
                } else if (args[0].equalsIgnoreCase("bot")) {
                    embeds.send(embeds.getBotHelp(), e.getTextChannel());
                }else {
                    embeds.send(embeds.getHelp(), e.getTextChannel());
                }
            }else {
                embeds.send(embeds.getHelp(), e.getTextChannel());
            }
        }
    }
}