package org.example.service;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class ReportWriter {

    public void writeToFile(String fileName, String content) throws IOException {
        Path output = Paths.get(fileName);

        try (BufferedWriter writer = Files.newBufferedWriter(
                output,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING,
                StandardOpenOption.WRITE)) {
            writer.write(content);
        }
    }
}