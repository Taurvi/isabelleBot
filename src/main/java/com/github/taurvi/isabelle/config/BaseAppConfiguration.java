package com.github.taurvi.isabelle.config;

public class BaseAppConfiguration implements IsabelleConfiguration {
    @Override
    public String getDiscordAPIToken() {
        return "YOUR_API_TOKEN_HERE";
    }

    @Override
    public boolean getTwitterDebugMode() {
        return false;
    }

    @Override
    public String getTwitterAPIConsumerKey() {
        return "YOUR_TWITTER_API_CONSUMER_KEY";
    }

    @Override
    public String getTwitterAPIConsumerSecret() {
        return "YOUR_TWITTER_API_CONSUMER_SECRET";
    }

    @Override
    public String getTwitterAPIAccessToken() {
        return "YOUR_TWITTER_API_ACCESS_TOKEN";
    }

    @Override
    public String getTwitterAPIAccessTokenSecret() {
        return "YOUR_TWITTER_API_ACCESS_TOKEN_SECRET";
    }

    @Override
    public long getACNHTwitterID() {
        return 1377451009;
    };
}
