package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.storageengine.StorageEngine;

public class CommandRouter {
    private static final String WHITESPACE_REGEX = "\\s+";

    public static CommandExecutor parse(String command, StorageEngine storageEngine) {
        String[] tokens = command.split(WHITESPACE_REGEX);

        if (tokens.length == 0 || tokens[0].isEmpty()) {
            throw new IllegalArgumentException("Empty command");
        }

        String commandName = tokens[0].toUpperCase();

        switch (commandName) {
            case "SET": {
                if (tokens.length != 3) {
                    throw new IllegalArgumentException("SET command requires exactly 2 arguments");
                }
                String key = tokens[1];
                String value = tokens[2];
                return new SetCommandExecutor(storageEngine, key, value);
            }
            case "GET": {
                if (tokens.length != 2) {
                    throw new IllegalArgumentException("GET command requires exactly 1 argument");
                }

                String key = tokens[1];
                return new GetCommandExecutor(storageEngine, key);
            }
            default: {
                throw new IllegalArgumentException("Unsupported command: " + commandName);
            }
        }
    }
}
