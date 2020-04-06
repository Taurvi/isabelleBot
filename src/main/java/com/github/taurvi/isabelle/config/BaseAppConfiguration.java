package com.github.taurvi.isabelle.config;

public class BaseAppConfiguration implements IsabelleConfiguration {
    @Override
    public String getDiscordAPIToken() {
        return "";
    }

    @Override
    public boolean getTwitterDebugMode() {
        return false;
    }

    @Override
    public String getTwitterAPIConsumerKey() {
        return "";
    }

    @Override
    public String getTwitterAPIConsumerSecret() {
        return "";
    }

    @Override
    public String getTwitterAPIAccessToken() {
        return "";
    }

    @Override
    public String getTwitterAPIAccessTokenSecret() {
        return "";
    }

    @Override
    public long getACNHTwitterID() {
        return 0;
    }

    @Override
    public String getMongoClientAddress() {
        return "";
    }

    @Override
    public int getMongoClientPort() {
        return 0;
    }

    @Override
    public String getMongoDatabase() {
        return "";
    }

    @Override
    public String getMongoCollectionForTurnips() {
        return "";
    }
}
