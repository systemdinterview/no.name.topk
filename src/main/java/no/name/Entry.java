package no.name;

import java.util.Objects;

public class Entry {
    private final String url;
    private final long count;

    public Entry(String url, long count) {
        this.url = url;
        this.count = count;
    }

    @Override
    public String toString() {
        return url + " " + count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Entry entry = (Entry) o;
        return count == entry.count && Objects.equals(url, entry.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url, count);
    }

    public long count() {
        return count;
    }
}