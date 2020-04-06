package com.github.taurvi.isabelle.clients.mongo;

import com.google.inject.Provider;
import com.mongodb.MongoClientSettings;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

public class CodecRegistryProvider implements Provider<CodecRegistry> {
    @Override
    public CodecRegistry get() {
        CodecRegistry defaultRegistry = MongoClientSettings.getDefaultCodecRegistry();
        PojoCodecProvider codecProvider = PojoCodecProvider.builder().automatic(true).build();
        return CodecRegistries.fromRegistries(defaultRegistry, CodecRegistries.fromProviders(codecProvider));
    }
}
