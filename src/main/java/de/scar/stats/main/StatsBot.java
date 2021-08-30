package de.scar.stats.main;

import com.google.common.base.Strings;
import de.scar.stats.area.association.AssociateCommand;
import de.scar.stats.discord.guild.commands.gomme.ClanCommand;
import de.scar.stats.discord.guild.commands.gomme.HelpCommand;
import de.scar.stats.discord.guild.commands.gomme.InviteCommand;
import de.scar.stats.discord.guild.commands.gomme.StatsCommand;
import de.scar.stats.discord.guild.commands.mojang.MojangCommands;
import de.scar.stats.discord.guild.events.BotPingEvent;
import de.scar.stats.discord.guild.events.ServerReadyEvent;
import me.kbrewster.exceptions.APIException;
import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.entities.Activity;
import org.jsoup.internal.StringUtil;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatsBot {

    private static String token = "ODY4MTU0NDc0NzkzOTMwNzcy.YPrh3A.UtTib2CbaZXZTtGBVhZ377sAIA0";
    @SuppressWarnings("deprecation")
    private static JDA jda;

    public static void main(String[] args) throws LoginException, IOException, InterruptedException, APIException {
        // TODO
        // » !Gamemodes (displays all gamemodes)
        // » !Top10 <Gamemode> <Alltime | Monthly>
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
