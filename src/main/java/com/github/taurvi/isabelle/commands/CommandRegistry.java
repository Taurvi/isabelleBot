package com.github.taurvi.isabelle.commands;

import com.github.taurvi.isabelle.commands.turnips.TurnipGetCommand;
import com.github.taurvi.isabelle.commands.turnips.TurnipSetCommand;
import com.google.common.collect.Maps;
import com.google.inject.Provider;
import lombok.Getter;
import org.javacord.api.entity.message.MessageBuilder;

import java.util.Map;

public class CommandRegistry {
    private Provider<MessageBuilder> messageBuilderProvider;
    @Getter
    private Map<String, IsabelleCommand> commandMap;

    public CommandRegistry(Provider<MessageBuilder> messageBuilderProvider) {
        this.messageBuilderProvider = messageBuilderProvider;
        this.commandMap = Maps.newHashMap();
        registerCommands();
    }

    private void registerCommands() {
        commandMap.put("turnip-set", new TurnipSetCommand(messageBuilderProvider));
        commandMap.put("turnip-get", new TurnipGetCommand(messageBuilderProvider));
    }
}
