package com.github.taurvi.isabelle.repositories;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.github.taurvi.isabelle.models.TurnipPrice;
import com.google.api.client.util.Lists;
import com.google.common.collect.ImmutableMap;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import javax.inject.Inject;
import java.util.List;

public class TurnipRepository implements Repository<TurnipPrice> {
    private MongoCollection<TurnipPrice> turnipsCollection;

    @Inject
    public TurnipRepository(IsabelleConfiguration appConfig, MongoDatabase mongoDatabase) {
        this.turnipsCollection = mongoDatabase.getCollection(appConfig.getMongoCollectionForTurnips(), TurnipPrice.class);
    }

    @Override
    public void create(TurnipPrice entity) {
        turnipsCollection.insertOne(entity);
    }

    @Override
    public TurnipPrice read(String index) {
        return turnipsCollection.find(convertIndexToBSON(index)).first();
    }

    @Override
    public void update(TurnipPrice entity) {
        turnipsCollection.findOneAndReplace(entity.getIdAsBSON(), entity);
    }

    @Override
    public TurnipPrice delete(String index) {
        return turnipsCollection.findOneAndDelete(convertIndexToBSON(index));
    }

    public List<TurnipPrice> getAllSortedByPrice() {
        List<TurnipPrice> turnipPriceList = Lists.newArrayList();
        FindIterable<TurnipPrice> turnipPriceFindIterable = turnipsCollection.find().sort(sortByPrice());
        for(TurnipPrice turnipPrice : turnipPriceFindIterable) {
            turnipPriceList.add(turnipPrice);
        }
        return turnipPriceList;
    }

    private Document convertIndexToBSON(String index) {
        return new Document(ImmutableMap.of("_id", Long.valueOf(index)));
    }

    private Document sortByPrice() {
        return new Document(ImmutableMap.of("price", -1));
    }
}
