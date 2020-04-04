package com.github.taurvi.isabelle;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.javacord.api.DiscordApi;
import twitter4j.TwitterStream;

public class Main {
    public static void main(String[] args) {
        Injector injector = Guice.createInjector(new CoreModule());

        DiscordApi discordApi = injector.getInstance(DiscordApi.class);
        injector.getInstance(TwitterStream.class);

        System.out.println("You can invite the bot by using the following url: " + discordApi.createBotInvite());

    }
}
