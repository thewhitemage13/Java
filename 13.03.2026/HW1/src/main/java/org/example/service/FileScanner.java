package org.example.service;

import org.example.model.ExtensionStat;
import org.example.model.Summary;
import org.example.util.FileUtils;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.EnumSet;
import java.util.Map;

public class FileScanner {

    public void scanFileTree(Path root, Map<String, ExtensionStat> stats, Summary summary) {
        try {
            Files.walkFileTree(root, EnumSet.noneOf(FileVisitOption.class), Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (!attrs.isRegularFile()) {
                        return FileVisitResult.CONTINUE;
                    }

                    long size = attrs.size();
                    String extension = FileUtils.getExtension(file.getFileName().toString());

                    stats.computeIfAbsent(extension, k -> new ExtensionStat()).addFile(size);
                    summary.addFile(size);

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println("Failed to scan root " + root + ": " + e.getMessage());
        }
    }
}