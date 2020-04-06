package com.github.taurvi.isabelle.config;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivateKey;

public interface IsabelleConfiguration {
    String getDiscordAPIToken();

    boolean getTwitterDebugMode();

    String getTwitterAPIConsumerKey();

    String getTwitterAPIConsumerSecret();

    String getTwitterAPIAccessToken();

    String getTwitterAPIAccessTokenSecret();

    long getACNHTwitterID();

    String getMongoClientAddress();

    int getMongoClientPort();

    String getMongoDatabase();

    String getMongoCollectionForTurnips();
}
