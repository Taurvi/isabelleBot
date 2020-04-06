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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.Map;

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

    public List<TurnipPrice> getAllSortedByDateAndPrice() {
        List<TurnipPrice> turnipPriceList = Lists.newArrayList();
        FindIterable<TurnipPrice> turnipPriceFindIterable = turnipsCollection.find().sort(sortByDateAndPrice());
        turnipPriceFindIterable.forEach(turnipPriceList::add);
        return turnipPriceList;
    }

    private Document convertIndexToBSON(String index) {
        return new Document(ImmutableMap.of("_id", Long.valueOf(index)));
    }

    private Document sortByDateAndPrice() {
        return new Document(ImmutableMap.of("price", -1));
    }
}
