package de.scar.stats.main;

import de.scar.stats.area.association.AssociateCommand;
import de.scar.stats.discord.guild.commands.gomme.ClanCommand;
import de.scar.stats.discord.guild.commands.gomme.HelpCommand;
import de.scar.stats.discord.guild.commands.gomme.InviteCommand;
import de.scar.stats.discord.guild.commands.gomme.StatsCommand;
import de.scar.stats.discord.guild.commands.mojang.MojangCommands;
import de.scar.stats.discord.guild.events.BotPingEvent;
import de.scar.stats.discord.guild.events.ServerReadyEvent;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

import javax.security.auth.login.LoginException;

public class StatsBot {

    private static String token = "token";
    @SuppressWarnings("deprecation")
    private static JDA jda;

    public static void main(String[] args) throws LoginException{
        createBot();
    }

    public static void createBot() throws LoginException {
        jda = JDABuilder.createDefault(token)
            .addEventListeners(new StatsCommand())
            .addEventListeners(new ClanCommand())
                .addEventListeners(new AssociateCommand())
                .addEventListeners(new BotPingEvent())
            .addEventListeners(new HelpCommand())
            .addEventListeners(new MojangCommands())
            .addEventListeners(new InviteCommand())
            .addEventListeners(new ServerReadyEvent())
            .setActivity(Activity.of(Activity.ActivityType.LISTENING, "@GommeHD.net Stats"))
            .setStatus(OnlineStatus.DO_NOT_DISTURB)
            .build();
    }
}
