package de.scar.stats.area.association;

import de.scar.stats.area.clan.objects.Clantag;
import de.scar.stats.area.guild.Server;
import de.scar.stats.area.guild.ServerHandler;
import de.scar.stats.area.other.CustomEmbed;
import me.kbrewster.exceptions.APIException;
import me.kbrewster.exceptions.InvalidPlayerException;
import me.kbrewster.mojangapi.MojangAPI;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class AssociateCommand extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) {
            return;
        }

        final Server server = ServerHandler.getServer(event.getGuild());

        if(event.getMessage().getContentDisplay().equalsIgnoreCase(server.getCommandPrefix()+"associate")) {
            new CustomEmbed().error("Du musst einen Spielernamen eingeben").send(event.getTextChannel());
            return;
        }

        if(event.getMessage().getContentDisplay().equalsIgnoreCase(server.getCommandPrefix()+"p")) {
            if(AssociationsHandler.isUserAssociated(event.getMember().getIdLong())) {
                Association association = AssociationsHandler.getAssociation(event.getMember().getIdLong());
                CustomEmbed embed = new CustomEmbed();
                embed.thumbnail("https://minotar.net/armor/bust/" + association.getName() + "/512.png");
                embed.desc("Dein Profil");
                embed.f("Name", "**" + association.getName() + "**");
                if(association.isInClan()) {
                    embed.f("Clan", "**[" + association.getClantag().value() + "]**");
                }else {
                    embed.f("Clan", "Kein Clan");
                }
                if(association.getOtherAccounts().size() > 0) {
                    embed.f("Accounts", "» " + String.join("\n » ", association.getOtherAccounts()));
                }else {
                    embed.f("Accounts", "Keine Accounts verknüpft");
                }
                embed.send(event.getTextChannel());
            }else {
                new CustomEmbed().error("Du hast keinen Minecraft Account mit deinem Discord Account verknüpft").send(event.getTextChannel());
            }
        }

        if (event.getMessage().getContentDisplay().toLowerCase().startsWith(server.getCommandPrefix() + "associate")) {
            String[] args = event.getMessage().getContentDisplay().split(" ");
            if (args.length == 2) {
                final String name = args[1];
                if (!AssociationsHandler.isUserAssociated(event.getMember().getIdLong())) {
                    try {
                        MojangAPI.getUUID(name);
                        new CustomEmbed().association(event.getMember(), new Association(name)).thumbnail("https://minotar.net/armor/bust/" + name + "/512.png").send(event.getTextChannel());
                        AssociationsHandler.associate(event.getMember().getIdLong(), name);
                    } catch (IOException exception) {
                        new CustomEmbed().error("Es wurde kein Spieler mit dem Namen *" + args[0] + " gefunden").send(event.getTextChannel());
                    } catch (APIException e) {
                        new CustomEmbed().error("Es wurde kein Spieler mit dem Namen *" + args[0] + " gefunden").send(event.getTextChannel());
                    } catch (InvalidPlayerException e) {
                        new CustomEmbed().error("Es wurde kein Spieler mit dem Namen *" + args[0] + " gefunden").send(event.getTextChannel());
                    }
                } else {
                    new CustomEmbed().error("Du hast deinen Account bereits verknüpft").send(event.getTextChannel());
                }
            } else if (args.length == 3) {
                if (AssociationsHandler.isUserAssociated(event.getMember().getIdLong())) {
                    if (args[1].equalsIgnoreCase("clan")) {
                        String clantag = args[1];
                        //check
                        new CustomEmbed().desc("Deinem Profil wurde der Clan **" + args[2] + "** hinzugefügt").send(event.getTextChannel());
                        AssociationsHandler.getAssociation(event.getMember().getIdLong()).setClan(new Clantag(args[2]));
                    } else if (args[1].equalsIgnoreCase("account")) {
                        new CustomEmbed().desc("Deinem Profil wurde der Account **" + args[2] + "** hinzugefügt").send(event.getTextChannel());
                        AssociationsHandler.getAssociation(event.getMember().getIdLong()).addAccount(args[2]);
                    } else {
                        new CustomEmbed().error(args[1] + " ist ungültig").send(event.getTextChannel());
                    }
                }else {
                    new CustomEmbed().error("Du hast keinen Minecraft Account mit deinem Discord Account verknüpft").send(event.getTextChannel());
                }
            }
        }
    }
}
