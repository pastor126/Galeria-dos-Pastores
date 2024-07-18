package com.pastor126.galeriap.util;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileUtil {

    private static final String FILE_PATH = "authDto.txt";

    public static String readFromFile() throws IOException {
        return new String(Files.readAllBytes(Paths.get(FILE_PATH)));
    }

    public static void saveToFile(String data) throws IOException {
        Files.write(Paths.get(FILE_PATH), data.getBytes());
    }
}
