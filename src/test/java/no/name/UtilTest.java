package no.name;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    private final String[] shortList = {
            "http://api.tech.com/item/121345 9",
            "http://api.tech.com/item/122345 350",
            "http://api.tech.com/item/123345 25",
            "http://api.tech.com/item/124345 231",
            "http://api.tech.com/item/125345 111",
    };

    @Test
    public void testEmpty() {
        Iterator<String> iterator = Collections.<String>emptyList().iterator();
        Entry[] topK = Util.findTopK(iterator, 10);

        assertEquals(0, topK.length);
    }

    @Test
    public void testShorter() {
        Iterator<String> iterator = Arrays.asList(shortList).iterator();
        Entry[] topK = Util.findTopK(iterator, 10);

        Entry[] entries = sortAndGet(shortList, 10);
        assertArrayEquals(entries, topK);
    }

    @Test
    public void testLonger() {
        String[] input = new String[1_000];

        final String baseUrl = "https://api.tech.com/item/";
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (int i = 0; i < input.length; i++) {
            int value = random.nextInt(1_000_000);
            input[i] = baseUrl + value + " " + value;
        }

        Iterator<String> iterator = Arrays.asList(input).iterator();
        Entry[] topK = Util.findTopK(iterator, 10);

        Entry[] entries = sortAndGet(input, 10);
        assertArrayEquals(entries, topK);
    }

    @Test
    public void testZeroLimit() {
        Iterator<String> iterator = Arrays.asList(shortList).iterator();
        Entry[] topK = Util.findTopK(iterator, 0);

        assertEquals(0, topK.length);
    }

    private Entry[] sortAndGet(String[] source, int limit) {
        Entry[] temp = new Entry[source.length];

        for (int i = 0; i < temp.length; i++) {
            final String[] parts = source[i].split(" ");

            String url = parts[0];
            int count = Integer.parseInt(parts[1]);

            temp[i] = new Entry(url, count);
        }

        Arrays.sort(temp, Comparator.comparingLong(Entry::count).reversed());
        Entry[] result = Arrays.copyOfRange(temp, 0, Math.min(temp.length, limit));

        return result;
    }

    @Test
    public void testBar() {
        assertTrue(true);
    }
}