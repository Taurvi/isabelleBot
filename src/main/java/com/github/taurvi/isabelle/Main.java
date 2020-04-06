package com.github.taurvi.isabelle;

import com.github.taurvi.isabelle.commands.CommandRegistry;
import com.github.taurvi.isabelle.listeners.TurnipPriceListener;
import com.github.taurvi.isabelle.repositories.TurnipRepository;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageBuilder;

import java.time.Clock;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CoreModule());

        DiscordApi discordApi = injector.getInstance(DiscordApi.class);
        // injector.getInstance(TwitterStream.class);

        Clock clock = injector.getInstance(Clock.class);
        TurnipRepository turnipRepository = injector.getInstance(TurnipRepository.class);
        Provider<MessageBuilder> messageBuilderProvider = injector.getProvider(MessageBuilder.class);
        CommandRegistry commandRegistry = new CommandRegistry(messageBuilderProvider);

        discordApi.addMessageCreateListener(new TurnipPriceListener(clock, commandRegistry, turnipRepository));


        System.out.println("You can invite the bot by using the following url: " + discordApi.createBotInvite());
    }
}
