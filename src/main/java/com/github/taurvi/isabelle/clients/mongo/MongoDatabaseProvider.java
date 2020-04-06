package com.github.taurvi.isabelle.clients.mongo;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public class MongoDatabaseProvider implements Provider<MongoDatabase> {
    private IsabelleConfiguration appConfig;
    private MongoClient mongoClient;

    @Inject
    public MongoDatabaseProvider(IsabelleConfiguration appConfig, MongoClient mongoClient) {
        this.appConfig = appConfig;
        this.mongoClient = mongoClient;
    }

    @Override
    public MongoDatabase get() {
        return mongoClient.getDatabase(appConfig.getMongoDatabase());
    }
}
