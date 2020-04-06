package com.github.taurvi.isabelle.clients.mongo;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.common.collect.ImmutableList;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecRegistry;

public class MongoClientProvider implements Provider<MongoClient> {
    private IsabelleConfiguration appConfig;
    private CodecRegistry codecRegistry;

    @Inject
    public MongoClientProvider(IsabelleConfiguration appConfig, CodecRegistry codecRegistry) {
        this.appConfig = appConfig;
        this.codecRegistry = codecRegistry;
    }

    @Override
    public MongoClient get() {
        return MongoClients.create(buildSettings());
    }

    private MongoClientSettings buildSettings() {
        ServerAddress databaseAddress = new ServerAddress(appConfig.getMongoClientAddress(), appConfig.getMongoClientPort());
        return MongoClientSettings.builder()
                .applyToClusterSettings(builder -> builder.hosts(ImmutableList.of(databaseAddress)))
                .codecRegistry(codecRegistry)
                .build();
    }
}
