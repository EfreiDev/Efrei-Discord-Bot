package fr.efrei.bot.modal;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class ModalManager {

    private static Map<String, Consumer<ModalInteractionEvent>> MODALS = new HashMap<String, Consumer<ModalInteractionEvent>>();

    public static boolean registerCommand(final Modal modal, final Consumer<ModalInteractionEvent> exec) {
        final String name;

        if (modal == null || exec == null)
            return false;
        name = modal.getId() ;
        if (name == null)
            return false;
        MODALS.put(name, exec);
        return true;
    }

    public static boolean unRegisterCommand(final String name) {
        final Consumer<ModalInteractionEvent> modalExec;

        if (name == null)
            return false;
        modalExec = MODALS.remove(name);
        return modalExec != null;
    }

    public static boolean dispatchEvent(final ModalInteractionEvent event) {
        final Consumer<ModalInteractionEvent> modalExec;

        if (event == null)
            return false;
        modalExec = MODALS.get(event.getModalId());
        if (modalExec == null)
            return false;
        modalExec.accept(event);
        return true;
    }

}
