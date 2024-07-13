package it.polimi.ingsw.gc31.utility;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import it.polimi.ingsw.gc31.exceptions.NoFileCreatedException;

/**
 * Utility class to manage files and directories
 * 
 */
public class FileUtility {

    /**
     * Name of the folder that will be created on the desktop
     */
    public static final String folderName = "CodexNaturalis";
    /**
     * Name of the file that will be created in the {@link #folderName} folder
     */
    public static final String fileName = "Token.txt";

    /**
     * @return the path of the desktop
     */
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

        Path path = Paths.get(desktopPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return desktopPath;
    }

    /**
     * @return the path of the folder {@link #folderName} on the desktop
     */
    public static Path getCodexNaturalisPath() {
        // Crea il percorso completo della cartella e del file
        return Paths.get(getDesktopPath(), folderName);
    }

    /**
     * @return the path of the file {@link #fileName} in the folder
     *         {@link #folderName}
     */
    public static Path getCodexTokenFilePath() {
        // Crea il percorso completo della cartella e del file
        return Paths.get(getDesktopPath(), folderName, fileName);
    }

    /**
     * @param path the path to check
     * @return true if the path exists, false otherwise
     */
    public static boolean exists(Path path) {
        boolean exists = Files.exists(path);
        return exists;
    }

    /**
     * 
     * @param path        the path to create
     * @param folderName  the name of the folder to create
     * @param desktopPath if true the folder will be created on the desktop
     * @throws NoFileCreatedException
     */
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
