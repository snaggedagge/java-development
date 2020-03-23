package dkarlsso.smartmirror.javafx.model;

import java.time.Instant;
import java.util.*;
import java.util.function.Consumer;

public class CachedList<T> implements Iterable<T> {

    private final int timeToLive;

    private final LinkedHashMap<T, Instant> entries = new LinkedHashMap <>();

    public CachedList(int timeToLive) {
        this.timeToLive = timeToLive;
    }

    public void add(T entry) {
        entries.put(entry, Instant.now());
    }

    public int size() {
        clearOldEntries();
        return entries.size();
    }

    public void clear() {
        entries.clear();
    }

    public void remove(T entry) {
        entries.remove(entry);
    }

    public List<T> getAll() {
        return new ArrayList<>(entries.keySet());
    }

    private void clearOldEntries() {
        final List<T> elementsToRemove = new ArrayList<>();
        entries.forEach((key, value) -> {
            if (value.plusSeconds(timeToLive).isBefore(Instant.now())) {
                elementsToRemove.add(key);
            }
        });
        elementsToRemove.forEach(entries::remove);
    }

    @Override
    public void forEach(Consumer action) {
        clearOldEntries();
        entries.keySet().forEach(action::accept);
    }

    @Override
    public Iterator<T> iterator() {
        clearOldEntries();
        return entries.keySet().iterator();
    }

    @Override
    public Spliterator<T> spliterator() {
        clearOldEntries();
        return entries.keySet().spliterator();
    }

}
