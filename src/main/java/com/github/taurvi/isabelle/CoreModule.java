package com.github.taurvi.isabelle;

import com.github.taurvi.isabelle.clients.javacord.DiscordApiProvider;
import com.github.taurvi.isabelle.clients.javacord.MessageBuilderProvider;
import com.github.taurvi.isabelle.clients.mongo.CodecRegistryProvider;
import com.github.taurvi.isabelle.clients.mongo.MongoClientProvider;
import com.github.taurvi.isabelle.clients.mongo.MongoDatabaseProvider;
import com.github.taurvi.isabelle.clients.twitter4j.ACNHTwitterStreamProvider;
import com.github.taurvi.isabelle.clients.twitter4j.TwitterConfigurationProvider;
import com.github.taurvi.isabelle.config.BaseAppConfiguration;
import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.inject.AbstractModule;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;
import org.bson.codecs.configuration.CodecRegistry;
import org.javacord.api.DiscordApi;
import org.javacord.api.entity.message.MessageBuilder;
import twitter4j.FilterQuery;
import twitter4j.TwitterStream;
import twitter4j.conf.Configuration;

import java.time.Clock;

public class CoreModule extends AbstractModule {
    @Override
    public void configure() {
        // Configuration Bindings
        bind(IsabelleConfiguration.class).to(BaseAppConfiguration.class);
        bind(Clock.class).toInstance(Clock.systemUTC());

        // Discord
        bind(DiscordApi.class).toProvider(DiscordApiProvider.class);
        bind(MessageBuilder.class).toProvider(MessageBuilderProvider.class);

        // Twitter
        bind(Configuration.class).toProvider(TwitterConfigurationProvider.class);
        bind(TwitterStream.class).toProvider(ACNHTwitterStreamProvider.class);
        bind(FilterQuery.class).toInstance(new FilterQuery());

        // Mongo
        bind(MongoClient.class).toProvider(MongoClientProvider.class);
        bind(MongoDatabase.class).toProvider(MongoDatabaseProvider.class);
        bind(CodecRegistry.class).toProvider(CodecRegistryProvider.class);

        // Command Registry
    }
}
