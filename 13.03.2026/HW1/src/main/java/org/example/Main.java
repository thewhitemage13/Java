package org.example;

import org.example.model.ExtensionStat;
import org.example.model.Summary;
import org.example.service.FileScanner;
import org.example.service.ReportBuilder;
import org.example.service.ReportWriter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    private static final int TOP_LIMIT = 50;

    public static void main(String[] args) {
        List<Path> roots = new ArrayList<>();

        if (args.length > 0) {
            for (String arg : args) {
                roots.add(Paths.get(arg));
            }
        } else {
            for (Path root : FileSystems.getDefault().getRootDirectories()) {
                roots.add(root);
            }
        }

        Map<String, ExtensionStat> stats = new HashMap<>();
        Summary summary = new Summary();

        FileScanner fileScanner = new FileScanner();
        ReportBuilder reportBuilder = new ReportBuilder();
        ReportWriter reportWriter = new ReportWriter();

        for (Path root : roots) {
            System.out.println("Scanning: " + root.toAbsolutePath());
            fileScanner.scanFileTree(root, stats, summary);
        }

        String report = reportBuilder.buildReport(stats, summary, TOP_LIMIT);

        try {
            reportWriter.writeToFile("file_analysis_report.txt", report);
            System.out.println("Report saved successfully.");
        } catch (IOException e) {
            System.err.println("Failed to write report: " + e.getMessage());
        }
    }
}