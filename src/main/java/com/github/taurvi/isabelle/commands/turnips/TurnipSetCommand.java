package com.github.taurvi.isabelle.commands.turnips;

import com.github.taurvi.isabelle.commands.IsabelleCommand;
import com.google.inject.Provider;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.Color;


public class TurnipSetCommand implements IsabelleCommand {
    private static final String COMMAND = "set";
    private static final String COMMAND_WITH_PREFIX = String.format(COMMAND_FORMAT_WITH_SPACE, COMMAND_PREFIX, COMMAND);
    private Provider<MessageBuilder> messageBuilderProvider;

    public TurnipSetCommand(Provider<MessageBuilder> messageBuilderProvider) {
        this.messageBuilderProvider = messageBuilderProvider;
    }

    @Override
    public String getFormattedCommand() {
        return COMMAND_WITH_PREFIX;
    }

    @Override
    public boolean isCommand(String message) {
        return message.startsWith(COMMAND_WITH_PREFIX);
    }

    @Override
    public boolean isValid(String message) {
        String parameter = message.substring(COMMAND_WITH_PREFIX.length());
        try {
            Integer.parseInt(parameter);
        } catch (NumberFormatException exception) {
            return false;
        }
        return true;
    }

    @Override
    public void executeSuccess(MessageCreateEvent event, String message) {
        MessageBuilder messageBuilder = messageBuilderProvider.get();
        messageBuilder.setEmbed(new EmbedBuilder()
                .setTitle("✅ Success!")
                .setDescription(message)
                .setColor(Color.GREEN))
                .send(event.getChannel());
    }

    @Override
    public void executeError(MessageCreateEvent event, String message) {
        MessageBuilder messageBuilder = messageBuilderProvider.get();
        messageBuilder.setEmbed(new EmbedBuilder()
                .setTitle("⚠️ Error")
                .setDescription(message)
                .setColor(Color.RED))
                .send(event.getChannel());
    }
}
