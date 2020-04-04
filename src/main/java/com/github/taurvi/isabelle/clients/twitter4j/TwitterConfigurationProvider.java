package com.github.taurvi.isabelle.clients.twitter4j;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConfigurationProvider implements Provider<Configuration> {
    private IsabelleConfiguration appConfig;

    @Inject
    public TwitterConfigurationProvider(IsabelleConfiguration appConfig) {
        this.appConfig = appConfig;
    }

    @Override
    public Configuration get() {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();

        return configurationBuilder.setDebugEnabled(appConfig.getTwitterDebugMode())
                .setOAuthConsumerKey(appConfig.getTwitterAPIConsumerKey())
                .setOAuthConsumerSecret(appConfig.getTwitterAPIConsumerSecret())
                .setOAuthAccessToken(appConfig.getTwitterAPIAccessToken())
                .setOAuthAccessTokenSecret(appConfig.getTwitterAPIAccessTokenSecret())
                .build();
    }
}
