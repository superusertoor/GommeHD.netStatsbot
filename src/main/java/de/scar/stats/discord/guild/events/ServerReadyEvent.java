package de.scar.stats.discord.guild.events;

import de.scar.stats.area.guild.Server;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ServerReadyEvent extends ListenerAdapter {

    @Override
    public void onGuildReady(GuildReadyEvent event) {
        new Server(event.getGuild()).cache();
    }
}