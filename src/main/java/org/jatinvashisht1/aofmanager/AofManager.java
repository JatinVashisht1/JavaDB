package org.jatinvashisht1.aofmanager;

import org.jatinvashisht1.core.commandexecutors.CommandRouter;
import org.jatinvashisht1.core.commandexecutors.ICommandExecutor;
import org.jatinvashisht1.core.networking.ClientHandler;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.jatinvashisht1.constants.Constants.WHITESPACE_REGEX;

public class AofManager {
    private static final String FILE_NAME = "AOF.log";
    private StorageEngine storageEngine;
    private BufferedWriter bufferedWriter;

    public AofManager(StorageEngine storageEngine) {
        this.storageEngine = storageEngine;
        try {
            FileWriter fileWriter = new FileWriter(FILE_NAME, true);
            this.bufferedWriter = new BufferedWriter(fileWriter);
        } catch (IOException e) {
            System.err.println("Failed to initialize AOF file: " + e.getMessage());
        }
    }


    public synchronized void append(String rawCommand) throws IOException {
        String[] tokenList = rawCommand.split(WHITESPACE_REGEX);

        if (tokenList.length == 0) return;
        boolean isValidCommand = Arrays.stream(ValidCommands.values()).anyMatch(validCommand ->
                validCommand.name().equalsIgnoreCase(tokenList[0]));
        if (!isValidCommand) return;

        try {
            bufferedWriter.append(rawCommand);
            bufferedWriter.newLine();
            bufferedWriter.flush();
        } catch(IOException ex) {
            System.out.println("Unable to write to file: " + ex);
        }
    }

    public void replay() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            System.out.println("No AOF log found. Starting with an empty engine.");
            return;
        }

        try (
                final FileReader fileReader = new FileReader(file);
                BufferedReader bufferedReader = new BufferedReader(fileReader)
        ) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                ICommandExecutor commandExecutor = CommandRouter.parse(line, storageEngine);
                commandExecutor.executeCommand();
            }
        } catch (IOException e) {
            System.out.println("Unable to read log file " + e);
        }
    }

}
