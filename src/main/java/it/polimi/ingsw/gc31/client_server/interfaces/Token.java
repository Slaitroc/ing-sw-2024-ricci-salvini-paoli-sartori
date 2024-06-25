package it.polimi.ingsw.gc31.client_server.interfaces;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoFileCreatedException;
import it.polimi.ingsw.gc31.exceptions.NoTokenException;
import it.polimi.ingsw.gc31.utility.FileUtility;

public class Token {

    private int token;
    private int tempToken;

    public int getTempToken() {
        return tempToken;
    }

    public void setTempToken(int tempToken) {
        this.tempToken = tempToken;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public Token(int token) {
        this.token = token;
    }

    public Token() {
        try {
            this.token = Integer.parseInt(getTokenLine());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (NoTokenException e) {
            this.token = -1;
        }
    }

    public String getTokenLine() throws NoTokenException {
        Path file = FileUtility.getCodexTokenFilePath();
        List<String> lines = new ArrayList<>();
        if (doesTokenExists()) {
            try {
                lines = Files.readAllLines(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // ui.showGenericClientResonse("TOKEN: " + lines.get(0));
            return lines.get(0);
        } else {
            throw new NoTokenException();
        }
    }

    public boolean doesTokenExists() {
        Path file = FileUtility.getCodexTokenFilePath();
        if (Files.exists(file)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean doesCodexFolderExists() {
        Path file = FileUtility.getCodexNaturalisPath();
        if (Files.exists(file)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean rewriteTokenFile() {
        boolean alreadyExists = false;
        // se non esiste la cartella la crea
        if (!doesCodexFolderExists())
            try {
                FileUtility.createDirectory(null, FileUtility.folderName, true);
            } catch (NoFileCreatedException e) {
                e.printStackTrace();
            }
        Path filePath = FileUtility.getCodexTokenFilePath();
        // se il file esiste lo cancella
        if (Files.exists(filePath)) {
            try {
                Files.delete(filePath);
            } catch (IOException e) {
                e.printStackTrace();
            }
            alreadyExists = true;
        }
        // crea e scrive il file
        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write("" + token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return alreadyExists;
    }
}
