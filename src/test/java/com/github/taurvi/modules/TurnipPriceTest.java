package com.github.taurvi.modules;

import com.github.taurvi.isabelle.models.TurnipPrice;
import org.bson.Document;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class TurnipPriceTest {
    private static final long MOCK_ID = 0;
    private static final String MOCK_TIMESTAMP = "timestamp";
    private static final String MOCK_USERNAME = "username";
    private static final int MOCK_PRICE = 0;

    private TurnipPrice sut;

    @Test
    public void should_return_id_as_BSON_Document() {
        // Given
        sut = buildTurnip();

        // When
        Document idAsBSON = sut.getIdAsBSON();

        // Then
        assertThat(idAsBSON).containsKey("_id");
        assertThat(idAsBSON).containsValue(MOCK_ID);
    }

    private TurnipPrice buildTurnip() {
        return TurnipPrice.builder()
                .id(MOCK_ID)
                .timestamp(MOCK_TIMESTAMP)
                .userName(MOCK_USERNAME)
                .price(MOCK_PRICE)
                .build();
    }
}
