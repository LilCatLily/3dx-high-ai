package io.github.lilcatlily.bot.data.manager;

import java.io.Closeable;
import java.util.function.Supplier;

public interface DataManager<T> extends Supplier<T>, Closeable {
    void save();

    @Override
    default void close() {
        save();
    }
}
