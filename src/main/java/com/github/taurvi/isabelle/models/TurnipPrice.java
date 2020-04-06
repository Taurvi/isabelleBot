package com.github.taurvi.isabelle.models;

import com.google.common.collect.ImmutableMap;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.Document;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TurnipPrice {
    private long id;
    private String timestamp;
    private String userName;
    private int price;

    public Document getIdAsBSON() {
        return new Document(ImmutableMap.of("_id", id));
    }
}
