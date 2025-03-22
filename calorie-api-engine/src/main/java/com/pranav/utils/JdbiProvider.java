package com.pranav.utils;

import com.google.inject.Provider;
import com.google.inject.Singleton;
import lombok.Data;
import lombok.Setter;
import org.jdbi.v3.core.Jdbi;


@Singleton
@Setter
@Data
public class JdbiProvider implements Provider<Jdbi> {
    private Jdbi jdbi;

    @Override
    public Jdbi get() {
        if (jdbi == null) {
            throw new IllegalStateException("Jdbi has not been initialized");
        }
        return jdbi;
    }
}