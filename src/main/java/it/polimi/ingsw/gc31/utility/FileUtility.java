package it.polimi.ingsw.gc31.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.polimi.ingsw.gc31.exceptions.NoFileCreatedException;

public class FileUtility {

    public static final String folderName = "CodexNaturalis";
    public static final String fileName = "Token.txt";

    public static String getDesktopPath() {
        String userHome = System.getProperty("user.home");
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

    public static Path getCodexNaturalisPath() {
        // Crea il percorso completo della cartella e del file
        return Paths.get(getDesktopPath(), folderName);
    }

    public static Path getCodexTokenFilePath() {
        // Crea il percorso completo della cartella e del file
        return Paths.get(getDesktopPath(), folderName, fileName);
    }

    public static boolean exists(Path path) {
        boolean exists = Files.exists(path);
        return exists;
    }

    public static void createDirectory(Path path, String folderName, boolean desktopPath)
            throws NoFileCreatedException {
        try {
            if (desktopPath)
                Files.createDirectory(Paths.get(getDesktopPath(), folderName));
            else {
                Files.createDirectory(path);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw new NoFileCreatedException();
        }

    }

}
