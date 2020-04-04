package com.github.taurvi.isabelle.clients.javacord;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;

public class DiscordApiProvider implements Provider<DiscordApi> {
    private String token;

    @Inject
    public DiscordApiProvider(IsabelleConfiguration appConfig) {
        this.token = appConfig.getDiscordAPIToken();
    }

    @Override
    public DiscordApi get() {
        return new DiscordApiBuilder().setToken(token).login().join();
    }
}
