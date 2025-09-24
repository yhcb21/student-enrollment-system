package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Utility class for recursive file operations.
 */
public class RecursiveFileUtils {

    /**
     * Recursively computes the total size of a directory.
     * Demonstrates: Recursion via Stream API (Files.walk).
     */
    public static long calculateDirectorySize(Path path) {
        AtomicLong size = new AtomicLong(0);
        try {
            Files.walk(path)
                 .filter(Files::isRegularFile)
                 .forEach(p -> {
                     try {
                         size.addAndGet(Files.size(p));
                     } catch (IOException e) {
                         System.err.println("Failed to get size for " + p + ": " + e.getMessage());
                     }
                 });
        } catch (IOException e) {
            System.err.println("Error walking the directory tree: " + e.getMessage());
        }
        return size.get();
    }
}