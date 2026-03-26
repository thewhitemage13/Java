package org.example.service;

import org.example.model.ExtensionStat;
import org.example.model.Summary;
import org.example.util.FileUtils;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ReportBuilder {

    public String buildReport(Map<String, ExtensionStat> stats, Summary summary, int topLimit) {
        List<Map.Entry<String, ExtensionStat>> topExtensions = stats.entrySet()
                .stream()
                .sorted((e1, e2) -> {
                    int byCount = Long.compare(e2.getValue().getCount(), e1.getValue().getCount());
                    if (byCount != 0) {
                        return byCount;
                    }
                    return Long.compare(e2.getValue().getBytes(), e1.getValue().getBytes());
                })
                .limit(topLimit)
                .collect(Collectors.toList());

        DecimalFormat df = new DecimalFormat("0.00");
        StringBuilder sb = new StringBuilder();

        String horizontal = "+"
                + FileUtils.repeat("-", 4) + "+"
                + FileUtils.repeat("-", 18) + "+"
                + FileUtils.repeat("-", 12) + "+"
                + FileUtils.repeat("-", 16) + "+"
                + FileUtils.repeat("-", 18) + "+"
                + FileUtils.repeat("-", 15) + "+\n";

        String headerFormat = "| %-2s | %-16s | %-10s | %-14s | %-16s | %-13s |\n";
        String rowFormat = "| %-2d | %-16s | %-10d | %-14d | %-16s | %-13s |\n";
        String totalFormat = "| %-23s | %-10d | %-14d | %-16s | %-13s |\n";

        sb.append("TOP-50 most popular file extensions in the system\n");
        sb.append("Files without extension are marked as ").append(FileUtils.NO_EXTENSION).append("\n\n");

        sb.append(horizontal);
        sb.append(String.format(headerFormat,
                "№", "розширення", "кіл-ть", "об'єм в Б", "% від кількості", "% від об'єму"));
        sb.append(horizontal);

        int index = 1;
        for (Map.Entry<String, ExtensionStat> entry : topExtensions) {
            String ext = entry.getKey();
            ExtensionStat stat = entry.getValue();

            double percentCount = summary.getTotalFiles() == 0
                    ? 0.0
                    : (stat.getCount() * 100.0 / summary.getTotalFiles());

            double percentBytes = summary.getTotalBytes() == 0
                    ? 0.0
                    : (stat.getBytes() * 100.0 / summary.getTotalBytes());

            sb.append(String.format(rowFormat,
                    index++,
                    ext,
                    stat.getCount(),
                    stat.getBytes(),
                    df.format(percentCount),
                    df.format(percentBytes)));
        }

        sb.append(horizontal);
        sb.append(String.format(totalFormat,
                "TOTAL:",
                summary.getTotalFiles(),
                summary.getTotalBytes(),
                "100.00",
                "100.00"));
        sb.append(horizontal);

        return sb.toString();
    }
}