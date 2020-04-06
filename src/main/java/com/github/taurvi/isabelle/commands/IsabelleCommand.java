package com.github.taurvi.isabelle.commands;

import org.javacord.api.event.message.MessageCreateEvent;

public interface IsabelleCommand {
    static final String COMMAND_FORMAT = "!%s-%s";
    static final String COMMAND_FORMAT_WITH_SPACE = COMMAND_FORMAT.concat(" ");
    static final String COMMAND_PREFIX = "turnip";

    String getFormattedCommand();

    boolean isCommand(String message);

    boolean isValid(String message);

    void executeSuccess(MessageCreateEvent event, String message);

    void executeError(MessageCreateEvent event, String message);
}
