package org.example.util;

import java.util.Locale;

public class FileUtils {

    public static final String NO_EXTENSION = "[no_extension]";

    public static String getExtension(String fileName) {
        int lastDot = fileName.lastIndexOf('.');

        if (lastDot <= 0 || lastDot == fileName.length() - 1) {
            return NO_EXTENSION;
        }

        return fileName.substring(lastDot + 1).toLowerCase(Locale.ROOT);
    }

    public static String repeat(String s, int count) {
        return s.repeat(Math.max(0, count));
    }
}