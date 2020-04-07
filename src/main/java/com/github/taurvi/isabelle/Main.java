package com.github.taurvi.isabelle;

import com.github.taurvi.isabelle.commands.CommandRegistry;
import com.github.taurvi.isabelle.listeners.TurnipPriceListener;
import com.github.taurvi.isabelle.repositories.TurnipRepository;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Provider;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import twitter4j.TwitterStream;

import java.awt.*;
import java.time.Clock;
import java.util.Optional;

public class Main {
    private static final String PROD_BOT_CHANNEL_ID = "696613468064710717";
    private static final String DEVO_BOT_CHANNEL_ID = "590033910415491076";

    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CoreModule());

        DiscordApi discordApi = injector.getInstance(DiscordApi.class);
        injector.getInstance(TwitterStream.class);

        Clock clock = injector.getInstance(Clock.class);
        TurnipRepository turnipRepository = injector.getInstance(TurnipRepository.class);
        Provider<MessageBuilder> messageBuilderProvider = injector.getProvider(MessageBuilder.class);
        CommandRegistry commandRegistry = new CommandRegistry(messageBuilderProvider);

        discordApi.addMessageCreateListener(new TurnipPriceListener(clock, commandRegistry, turnipRepository));

        Optional<TextChannel> botChannel = discordApi.getTextChannelById(PROD_BOT_CHANNEL_ID);

        if (botChannel.isPresent()) {
            messageBuilderProvider.get().setEmbed(new EmbedBuilder()
                    .setTitle("💝 __** I've Been Updated! **__ 💝")
                    .setDescription("Fixed issue with numbering due to deleted turnip prices.")
                    .setColor(Color.green))
                    .send(botChannel.get());
        }



        System.out.println("You can invite the bot by using the following url: " + discordApi.createBotInvite());
    }
}
