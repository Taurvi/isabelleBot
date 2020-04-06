package com.github.taurvi.isabelle.listeners;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.inject.Inject;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.util.Collection;

public class ACNHStatusListener implements StatusListener {
    private static final String BASE_STATUS_URL = "https://twitter.com/%s/status/%s";
    private IsabelleConfiguration appConfig;
    private DiscordApi discordApi;
    private MessageBuilder discordMessageBuilder;

    @Inject
    public ACNHStatusListener(IsabelleConfiguration appConfig, DiscordApi discordApi, MessageBuilder discordMessageBuilder) {
        this.appConfig = appConfig;
        this.discordApi = discordApi;
        this.discordMessageBuilder = discordMessageBuilder;
    }

    @Override
    public void onStatus(Status status) {
        sendStatusToDiscord(status);
    }

    @Override
    public void onDeletionNotice(StatusDeletionNotice statusDeletionNotice) {

    }

    @Override
    public void onTrackLimitationNotice(int numberOfLimitedStatuses) {

    }

    @Override
    public void onScrubGeo(long userId, long upToStatusId) {

    }

    @Override
    public void onStallWarning(StallWarning warning) {

    }

    @Override
    public void onException(Exception exception) {
        exception.printStackTrace();
    }

    private void sendStatusToDiscord(Status status) {
        Collection<TextChannel> channels = discordApi.getTextChannels();
        channels.forEach(channel -> {
            String linkToTweet = generateLinkToTweet(status.getId());
            discordMessageBuilder.append(status.getText())
                    .appendNewLine()
                    .appendNewLine()
                    .append(linkToTweet)
                    .send(channel);
        });
    }

    private String generateLinkToTweet(Long id) {
        return String.format(BASE_STATUS_URL, appConfig.getACNHTwitterID(), id);
    }
}
