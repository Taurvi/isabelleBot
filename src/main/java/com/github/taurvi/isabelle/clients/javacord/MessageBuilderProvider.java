package com.github.taurvi.isabelle.clients.javacord;

import com.google.inject.Provider;
import org.javacord.api.entity.message.MessageBuilder;

public class MessageBuilderProvider implements Provider<MessageBuilder> {
    @Override
    public MessageBuilder get() {
        return new MessageBuilder();
    }
}
