package com.github.taurvi.repositories;

import com.github.taurvi.isabelle.config.IsabelleConfiguration;
import com.github.taurvi.isabelle.models.TurnipPrice;
import com.github.taurvi.isabelle.repositories.TurnipRepository;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class TurnipRepositoryTest {
    private static final String MOCK_COLLECTION_NAME = "collection";
    private static final long MOCK_ID = 0;
    private static final String MOCK_ID_AS_STRING = Long.toString(MOCK_ID);
    private static final String MOCK_TIMESTAMP = "timestamp";
    private static final String MOCK_USERNAME = "username";
    private static final int MOCK_PRICE_LOW = 1;
    private static final int MOCK_PRICE_HIGH = 2;
    @Mock
    IsabelleConfiguration mockConfig;
    @Mock
    MongoDatabase mockDatabase;
    @Mock
    MongoCollection mockCollection;
    @Mock
    FindIterable<TurnipPrice> mockIterable;
    @Mock
    MongoCursor mockMongoCursor;
    @Mock
    Iterator<TurnipPrice> mockIterator;

    private TurnipPrice mockTurnipWithLowPrice;
    private Document indexAsBSON;
    private TurnipRepository sut;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        given(mockConfig.getMongoCollectionForTurnips()).willReturn(MOCK_COLLECTION_NAME);
        given(mockDatabase.getCollection(eq(MOCK_COLLECTION_NAME), eq(TurnipPrice.class))).willReturn(mockCollection);
        sut = new TurnipRepository(mockConfig, mockDatabase);
        mockTurnipWithLowPrice = createMockTurnipPriceEntity(MOCK_PRICE_LOW);
        indexAsBSON = convertIndexToBSON(MOCK_ID_AS_STRING);
    }

    @Test
    public void should_create_entity_in_collection() {
        // Given
        // When
        sut.create(mockTurnipWithLowPrice);

        // Then
        verify(mockCollection).insertOne(eq(mockTurnipWithLowPrice));
    }

    @Test
    public void should_read_and_return_entity_in_collection() {
        // Given
        given(mockIterable.first()).willReturn(mockTurnipWithLowPrice);
        given(mockCollection.find(eq(indexAsBSON))).willReturn(mockIterable);

        // When
        TurnipPrice response = sut.read(Long.toString(MOCK_ID));

        // Then
        assertThat(response).isEqualTo(mockTurnipWithLowPrice);
    }

    @Test
    public void should_update_entity_in_collection() {
        // Given
        // When
        sut.update(mockTurnipWithLowPrice);

        // Then
        verify(mockCollection).findOneAndReplace(eq(indexAsBSON), eq(mockTurnipWithLowPrice));
    }

    @Test
    public void should_delete_and_return_entity_in_collection() {
        // Given
        given(mockCollection.findOneAndDelete(eq(indexAsBSON))).willReturn(mockTurnipWithLowPrice);

        // When
        TurnipPrice response = sut.delete(MOCK_ID_AS_STRING);

        // Then
        assertThat(response).isEqualTo(mockTurnipWithLowPrice);
    }

    @Test
    public void should_return_all_entities_sorted_by_price() {
        // Given
        TurnipPrice mockTurnipWithHighPrice = createMockTurnipPriceEntity(MOCK_PRICE_HIGH);
        List<TurnipPrice> mockResponseList = ImmutableList.of(mockTurnipWithHighPrice, mockTurnipWithLowPrice);
        given(mockCollection.find()).willReturn(mockIterable);
        given(mockIterable.sort(eq(createSortByPriceBSON()))).willReturn(mockIterable);
        given(mockIterable.iterator()).willReturn(mockMongoCursor);
        given(mockMongoCursor.hasNext()).willReturn(true).willReturn(true).willReturn(false);
        given(mockMongoCursor.next()).willReturn(mockResponseList.get(0)).willReturn(mockResponseList.get(1));

        // When
        List<TurnipPrice> response = sut.getAllSortedByPrice();

        // Then
        assertThat(response).isEqualTo(mockResponseList);
    }

    private Document convertIndexToBSON(String index) {
        return new Document(ImmutableMap.of("_id", Long.valueOf(index)));
    }

    private TurnipPrice createMockTurnipPriceEntity(int price) {
        return TurnipPrice.builder()
                .id(MOCK_ID)
                .timestamp(MOCK_TIMESTAMP)
                .userName(MOCK_USERNAME)
                .price(price)
                .build();
    }

    private Document createSortByPriceBSON() {
        return new Document(ImmutableMap.of("price", -1));
    }
}
