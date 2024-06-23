package it.polimi.ingsw.gc31.utility;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtility {

    public static String getDesktopPath(String userHome) {
        String osName = System.getProperty("os.name").toLowerCase();
        String desktopPath = "";

        if (osName.contains("win")) {
            desktopPath = Paths.get(userHome, "Desktop").toString();
        } else if (osName.contains("mac")) {
            desktopPath = Paths.get(userHome, "Desktop").toString();
        } else if (osName.contains("nix") || osName.contains("nux")) {
            desktopPath = Paths.get(userHome, "Desktop").toString();
        }

        return desktopPath;
    }

    public static boolean exixts(Path path) {
        boolean exists = Files.exists(path);
        return exists;
    }

}
