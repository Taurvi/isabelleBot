package com.github.taurvi.isabelle.listeners;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.MessageBuilder;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;

import java.util.Optional;

public class ACNHStatusListener implements StatusListener {
    private static final String BASE_STATUS_URL = "https://twitter.com/%s/status/%s";
    private static final String PROD_TWITTER_CHANNEL_ID = "696119299294888046";
    private static final String DEVO_TWITTER_CHANNEL_ID = "590033910415491076";

    private IsabelleConfiguration appConfig;
    private DiscordApi discordApi;
    private Provider<MessageBuilder> messageBuilderProvider;

    @Inject
    public ACNHStatusListener(IsabelleConfiguration appConfig, DiscordApi discordApi, Provider<MessageBuilder> messageBuilderProvider) {
        this.appConfig = appConfig;
        this.discordApi = discordApi;
        this.messageBuilderProvider = messageBuilderProvider;
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
        if (status.getUser().getId() == appConfig.getACNHTwitterID()) {
            MessageBuilder discordMessageBuilder = messageBuilderProvider.get();
            String linkToTweet = generateLinkToTweet(status.getId());

            Optional<TextChannel> twitterChannel = discordApi.getTextChannelById(PROD_TWITTER_CHANNEL_ID);
            if (twitterChannel.isPresent()) {
                discordMessageBuilder.append(status.getText())
                        .appendNewLine()
                        .appendNewLine()
                        .append(linkToTweet)
                        .send(twitterChannel.get());
            }
        }
    }

    private String generateLinkToTweet(Long id) {
        return String.format(BASE_STATUS_URL, appConfig.getACNHTwitterID(), id);
    }
}
