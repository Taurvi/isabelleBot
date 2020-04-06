package com.github.taurvi.isabelle.repositories;

import java.util.List;

public interface Repository<E> {
    void create(E entity);

    E read(String index);

    void update(E entity);

    E delete(String index);
}
