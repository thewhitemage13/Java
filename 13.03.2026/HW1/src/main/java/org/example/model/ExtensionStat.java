package org.example.model;

public class ExtensionStat {
    private long count;
    private long bytes;

    public void addFile(long fileSize) {
        count++;
        bytes += fileSize;
    }

    public long getCount() {
        return count;
    }

    public long getBytes() {
        return bytes;
    }
}