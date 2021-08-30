package de.scar.stats.discord.guild.commands.gomme;

import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.Embeds;
import de.scar.stats.main.StatsBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class InviteCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if(e.getMember().getUser().isBot()){
            return;
        }

        final Server server = ServerHandler.getServer(e.getGuild());
        final String prefix = server.getCommandPrefix();
        final Embeds embeds = server.embeds();

        String msg = e.getMessage().getContentDisplay();
        if (msg.equalsIgnoreCase(prefix+"invite")) {
            embeds.send(embeds.getInviteCommand(), e.getTextChannel());
        }

        if (msg.toLowerCase().startsWith(prefix+"invite ")) {
            String[] args = msg.substring(8).split(" ");
            if (args.length >= 1) {
                embeds.send(embeds.getInviteCommand(), e.getTextChannel());
            }
        }
    }
}
