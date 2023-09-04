package fr.efrei.bot.command;

import fr.efrei.bot.Main;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.Command;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class CommandManager {


    private static class RegisteredCommandData {

        private long id;
        private Consumer<SlashCommandInteractionEvent> exec;

        private RegisteredCommandData(final long id, final Consumer<SlashCommandInteractionEvent> exec) {
            this.id = id;
            this.exec = exec;
        }

    }

    private static Map<String, RegisteredCommandData> COMMANDS = new HashMap<String, RegisteredCommandData>();

    public static boolean registerCommand(final SlashCommandData slashCommand, final Consumer<SlashCommandInteractionEvent> exec) {
        final String name;

        if (slashCommand == null || exec == null)
            return false;
        name = slashCommand.getName();
        if (name == null)
            return false;
        Main.getJda().updateCommands().addCommands(slashCommand).queue(commands -> {
            for (final Command command : commands) {
                CommandManager.COMMANDS.put(command.getName(), new RegisteredCommandData(command.getIdLong(), exec));
            }
        });
        return true;
    }

    public static boolean unRegisterCommand(final String name) {
        final RegisteredCommandData commandData;

        if (name == null)
            return false;
        commandData = COMMANDS.get(name);
        if (commandData == null)
            return false;
        Main.getJda().deleteCommandById(commandData.id).queue(unused -> {
            CommandManager.COMMANDS.remove(name);
        });
        return true;
    }

    public static boolean dispatchEvent(final SlashCommandInteractionEvent event) {
        final RegisteredCommandData commandData;

        if (event == null || event.getCommandType() != Command.Type.SLASH)
            return false;
        commandData = COMMANDS.get(event.getName());
        if (commandData == null)
            return false;
        commandData.exec.accept(event);
        return true;
    }

}
