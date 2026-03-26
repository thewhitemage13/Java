package org.example.model;

public class Summary {
    private long totalFiles;
    private long totalBytes;

    public void addFile(long size) {
        totalFiles++;
        totalBytes += size;
    }

    public long getTotalFiles() {
        return totalFiles;
    }

    public long getTotalBytes() {
        return totalBytes;
    }
}