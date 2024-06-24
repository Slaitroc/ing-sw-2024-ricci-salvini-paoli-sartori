package it.polimi.ingsw.gc31.client_server.interfaces;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoTokenException;
import it.polimi.ingsw.gc31.utility.FileUtility;

public class Token {

    private int token;

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
        if (!doesTokenExists()) {
            this.token = -1;
        } else {
            try {
                getTokenLine();
            } catch (NoTokenException e) {
                e.printStackTrace();
            }
        }
    }

    public String getTokenLine() throws NoTokenException {
        Path file = FileUtility.getCodexTokenFile();
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
        Path file = FileUtility.getCodexTokenFile();
        List<String> lines = new ArrayList<>();
        if (Files.exists(file)) {
            return true;
        } else {
            return false;
        }

    }
}
