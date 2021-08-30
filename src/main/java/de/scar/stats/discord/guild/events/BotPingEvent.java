package de.scar.stats.discord.guild.events;

import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.main.StatsBot;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class BotPingEvent extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Server s = ServerHandler.getServer(event.getGuild());
        if(event.getMessage().getContentRaw().equalsIgnoreCase("<@!868154474793930772>")) {
            s.embeds().send(s.embeds().getHelp(), event.getTextChannel());
        }
    }
}