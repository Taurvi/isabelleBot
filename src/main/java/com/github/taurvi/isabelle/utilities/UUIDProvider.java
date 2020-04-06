package com.github.taurvi.isabelle.utilities;

import com.google.inject.Provider;

import java.util.UUID;

public class UUIDProvider implements Provider<UUID> {
    @Override
    public UUID get() {
        return UUID.randomUUID();
    }
}
