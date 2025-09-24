package edu.ccrm.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

//handle backups
public class BackupService{

    //creates a backup of src directory
    public void performBackup(Path sourceDir, Path backupRootDir){
        System.out.println("Starting data backup...");
        try{
            //create a unique folder name using the current date and time
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
            Path specificBackupPath = backupRootDir.resolve("backup_" + timestamp);
            Files.createDirectories(specificBackupPath);

            //use Files.walk to stream all files from the source and copy them
            try (Stream<Path> fileStream = Files.walk(sourceDir)){
                fileStream
                    .filter(Files::isRegularFile)
                    .forEach(sourcePath -> {
                        try{
                            Path destinationPath = specificBackupPath.resolve(sourceDir.relativize(sourcePath));
                            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException e){
                            System.err.println("Could not copy file: " + sourcePath);
                        }
                    });
            }
            System.out.println("Backup created successfully at: " + specificBackupPath);
        } catch (IOException e){
            System.err.println("Error: Failed to create backup. " + e.getMessage());
        }
    }
}