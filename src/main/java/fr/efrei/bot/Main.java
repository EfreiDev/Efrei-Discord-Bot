package fr.efrei.bot;

import fr.efrei.bot.command.CommandManager;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.cache.CacheFlag;
import org.apache.commons.cli.*;

import java.util.Arrays;

public class Main {

    private static JDA jda;

    public static JDA buildJDA(final String token) {
        final JDABuilder jdaBuilder = JDABuilder.create(token, GatewayIntent.getIntents(GatewayIntent.ALL_INTENTS));

        jdaBuilder.enableCache(Arrays.asList(CacheFlag.values()));
        jdaBuilder.setAutoReconnect(true);
        return jdaBuilder.build();
    }

    public static void main(String[] args) {
        final Options options = new Options();
        final HelpFormatter helpFormatter;
        final CommandLineParser parser;
        final CommandLine cmdLine;
        final String token;

        options.addRequiredOption("t", "token", true, "Discord bot token.");
        parser = new DefaultParser();
        try {
            cmdLine = parser.parse(options, args);
        } catch (ParseException exception) {
            System.err.println(exception.getMessage());
            helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("Discord bot", options);
            return;
        }
        if (cmdLine.hasOption("h")) {
            helpFormatter = new HelpFormatter();
            helpFormatter.printHelp("Discord bot", options);
            return;
        }
        Main.jda = buildJDA(cmdLine.getOptionValue("t"));
        try {
            Main.jda.awaitReady();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
        Main.jda.addEventListener(new Listeners());
        Setup.commands();
    }

    public static JDA getJda() {
        return Main.jda;
    }

}