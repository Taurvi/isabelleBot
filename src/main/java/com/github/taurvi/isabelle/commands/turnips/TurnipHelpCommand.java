package com.github.taurvi.isabelle.commands.turnips;

import com.github.taurvi.isabelle.commands.IsabelleCommand;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.event.message.MessageCreateEvent;

import java.awt.*;

public class TurnipHelpCommand implements IsabelleCommand {
    private static final String COMMAND = "help";
    private static final String COMMAND_WITH_PREFIX = String.format(COMMAND_FORMAT, COMMAND_PREFIX, COMMAND);
    private Provider<MessageBuilder> messageBuilderProvider;

    @Inject
    public TurnipHelpCommand(Provider<MessageBuilder> messageBuilderProvider) {
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
        return true;
    }

    @Override
    public void executeSuccess(MessageCreateEvent event, String message) {
        MessageBuilder messageBuilder = messageBuilderProvider.get();
        messageBuilder.setEmbed(new EmbedBuilder()
                .setTitle("🐕‍🦺 __**Turnip Help**__ 🦮")
                .setDescription(message)
                .setColor(Color.BLUE))
                .send(event.getChannel());
    }

    @Override
    public void executeError(MessageCreateEvent event, String message) {
        MessageBuilder messageBuilder = messageBuilderProvider.get();
        messageBuilder.setEmbed(new EmbedBuilder()
                .setTitle("__**⚠️ Error**__ ")
                .setDescription(message)
                .setColor(Color.PINK))
                .send(event.getChannel());
    }
}
