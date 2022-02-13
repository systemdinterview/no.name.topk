package no.name;

import java.util.Arrays;
import java.util.Iterator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Util {

    private static final Logger logger = LoggerFactory.getLogger(Util.class);

    public static Entry[] findTopK(Iterator<String> lines, int limit) {
        final long[] counts = new long[limit + 1];
        final String[] urls = new String[limit + 1];

        Arrays.fill(counts, Long.MIN_VALUE);
        Arrays.fill(urls, "");

        int lineNumber = 0;

        while (lines.hasNext()) {
            final String line = lines.next();
            lineNumber++;

            final String[] parts = line.split(" ");

            if (parts.length != 2) {
                logger.warn("Skipping the broken line at {} (numbering starts at 1)", lineNumber);
                continue;
            }

            urls[0] = parts[0];
            try {
                counts[0] = Long.parseLong(parts[1]);

            } catch (NumberFormatException e) {
                logger.warn("Skipping a broken line at {}. Failed to parse a number (numbering starts at 1)", lineNumber);
                continue;
            }

            for (int j = 0; j < counts.length - 1; j++) {

                if (counts[j] > counts[j+1]) {
                    final long count = counts[j+1];
                    final String url = urls[j+1];

                    counts[j+1] = counts[j];
                    urls[j+1] = urls[j];

                    counts[j] = count;
                    urls[j] = url;
                }
            }
        }

        int firstValid = 1;
        for (; firstValid < counts.length; firstValid++) {
            if (counts[firstValid] != Long.MIN_VALUE) {
                break;
            }
        }

        final Entry[] result = new Entry[counts.length - firstValid];

        for (int i = result.length - 1; i >= 0; i--, firstValid++) {
            String url = urls[firstValid];
            long count = counts[firstValid];

            result[i] = new Entry(url, count);
        }

        return result;
    }
}
