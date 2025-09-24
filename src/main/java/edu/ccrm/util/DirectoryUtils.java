package edu.ccrm.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

//enables directory operations
public final class DirectoryUtils{

    //private constructor
    private DirectoryUtils(){
        // This constructor is intentionally left empty.
    }

    public static long getDirectorySize(Path path) throws IOException{
        if (!Files.isDirectory(path)){
            return Files.size(path); //base case for the recursion
        }

        long size = 0;
        try (Stream<Path> stream = Files.list(path)){
            for (Path entry : stream.collect(java.util.stream.Collectors.toList())){
                size += getDirectorySize(entry); //Recursive call
            }
        }
        return size;
    }
}