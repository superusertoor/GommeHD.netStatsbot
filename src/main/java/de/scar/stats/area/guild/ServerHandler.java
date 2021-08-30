package de.scar.stats.area.guild;

import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;

public class ServerHandler {

    private static HashMap<Guild, Server> servers = new HashMap<>();

    public static Server getServer(Guild guild) {
        return servers.get(guild);
    }

    public static void cacheServer(Server server) {
        servers.put(server.getGuild(), server);
    }

    public static void uncacheServer(Server server) {
        servers.remove(server.getGuild());
    }
}