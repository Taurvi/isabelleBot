package com.github.taurvi.isabelle.clients.twitter4j;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.github.taurvi.isabelle.listeners.ACNHStatusListener;
import com.google.inject.Inject;
import com.google.inject.Provider;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.conf.Configuration;

public class ACNHTwitterStreamProvider implements Provider<TwitterStream> {
    private IsabelleConfiguration appConfig;
    private Configuration twitterConfig;
    private FilterQuery filterQuery;
    private ACNHStatusListener listener;

    @Inject
    public ACNHTwitterStreamProvider(IsabelleConfiguration appConfig, Configuration twitterConfig, FilterQuery filterQuery, ACNHStatusListener listener) {
        this.appConfig = appConfig;
        this.twitterConfig = twitterConfig;
        this.filterQuery = filterQuery;
        this.listener = listener;
    }
    @Override
    public TwitterStream get() {
        return configureInstance(createInstance());
    }

    private TwitterStream createInstance() {
        return new TwitterStreamFactory(twitterConfig)
                .getInstance();
    }

    private TwitterStream configureInstance(TwitterStream twitterStream) {
        filterQuery.follow(appConfig.getACNHTwitterID());
        return twitterStream.addListener(listener).filter(filterQuery);
    }
}
