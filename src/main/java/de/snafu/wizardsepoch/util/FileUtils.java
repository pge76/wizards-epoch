package de.snafu.wizardsepoch.util;

import java.nio.file.Files;
import java.nio.file.Path;

public class FileUtils {
    public static String loadResource(String filePath) throws Exception {
        return Files.readString(Path.of(ClassLoader.getSystemResource(filePath).toURI()));
    }
}
