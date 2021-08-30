package de.scar.stats.discord.guild.commands.gomme;

import de.scar.stats.area.game.GommeHDNetwork;
import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.CustomEmbed;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class OnlineCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {

        if (e.getMember().getUser().isBot()) {
            return;
        }

        final Server server = ServerHandler.getServer(e.getGuild());
        final String prefix = server.getCommandPrefix();

        String msg = e.getMessage().getContentDisplay();
        if (msg.equalsIgnoreCase(prefix+"online")) {
            new CustomEmbed().desc("Es sind **" + GommeHDNetwork.getOnlineCount() + "** Spieler online").send(e.getTextChannel());
        }

        if(msg.toLowerCase().startsWith(prefix+"online ")) {
            String[] args = msg.substring(8).split(" ");
            if (args.length >= 1) {
                new CustomEmbed().desc("Es sind **" + GommeHDNetwork.getOnlineCount() + "** Spieler online").send(e.getTextChannel());
            }
        }
    }
}