package fr.efrei.bot;

import fr.efrei.bot.command.CommandManager;
import fr.efrei.bot.modal.ModalManager;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listeners extends ListenerAdapter {


    @Override
    public void onModalInteraction(final ModalInteractionEvent event) {
        ModalManager.dispatchEvent(event);
    }

    @Override
    public void onSlashCommandInteraction(final SlashCommandInteractionEvent event) {
        CommandManager.dispatchEvent(event);
    }

}
