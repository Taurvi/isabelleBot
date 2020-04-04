package com.github.taurvi.isabelle.config;

public interface IsabelleConfiguration {
    String getDiscordAPIToken();

    boolean getTwitterDebugMode();

    String getTwitterAPIConsumerKey();

    String getTwitterAPIConsumerSecret();

    String getTwitterAPIAccessToken();

    String getTwitterAPIAccessTokenSecret();

    long getACNHTwitterID();
}
