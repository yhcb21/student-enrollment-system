package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import edu.ccrm.config.AppConfig;
import edu.ccrm.util.RecursiveFileUtils;

/**
 * Handles backup operations using NIO.2.
 */
public class BackupService {

    public void createBackup() {
        AppConfig config = AppConfig.getInstance();
        Path sourceDir = Paths.get(config.getDataDirectory());
        
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        Path backupParentDir = Paths.get(config.getBackupDirectory());
        Path backupDestDir = backupParentDir.resolve("backup_" + timestamp);

        try {
            if (!Files.exists(sourceDir)) {
                 System.out.println("Source data directory does not exist. Run export first.");
                 return;
            }
            
            Files.createDirectories(backupDestDir);

            // Using Files.walk to copy files
            try (Stream<Path> stream = Files.walk(sourceDir)) {
                stream.forEach(source -> {
                    try {
                        Files.copy(source, backupDestDir.resolve(sourceDir.relativize(source)), StandardCopyOption.REPLACE_EXISTING);
                    } catch (IOException e) {
                        System.err.println("Failed to copy file: " + source + " -> " + e.getMessage());
                    }
                });
            }
            System.out.println("Backup created successfully at: " + backupDestDir);
            
            // Demonstrate recursive size calculation
            long size = RecursiveFileUtils.calculateDirectorySize(backupDestDir);
            System.out.printf("Total size of backup directory '%s' is %d bytes.\n", backupDestDir.getFileName(), size);

        } catch (IOException e) {
            System.err.println("Could not create backup: " + e.getMessage());
        }
    }
}