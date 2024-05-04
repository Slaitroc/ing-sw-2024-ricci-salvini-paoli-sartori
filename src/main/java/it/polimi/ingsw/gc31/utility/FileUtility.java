package it.polimi.ingsw.gc31.utility;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class FileUtility {

    public static String fileToString(String path) {
        String userDir = System.getProperty("user.dir");
        String relativePath = path;

        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(userDir + File.separator + relativePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                stringBuilder.append(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
}
