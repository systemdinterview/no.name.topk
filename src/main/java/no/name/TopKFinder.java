package no.name;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static no.name.Util.findTopK;

public class TopKFinder {

    private static final int BUFFER_SIZE = 4096;
    private static final Logger logger = LoggerFactory.getLogger(TopKFinder.class);

    public static void main(String[] args) {
       try (Scanner scanner = new Scanner(System.in)){
            final String path = scanner.nextLine();
            if (scanner.hasNextLine()) {
                logger.warn("Submitted more than one filenames. Processing only the first one...");
            }

            final BufferedReader reader = new BufferedReader(new FileReader(path), BUFFER_SIZE);
            final Entry[] results = findTopK(reader.lines().iterator(), 10);

            for (Entry r: results) {
                System.out.println(r);
            }

        } catch (NoSuchElementException ex) {
            logger.error("Failed to read filepath: {} ", ex.getMessage(), ex);

        } catch (FileNotFoundException ex) {
           logger.error("Failed to read filepath: {}", ex.getMessage(), ex);
        }
    }
}
