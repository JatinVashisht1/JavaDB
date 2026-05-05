package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.record.DbRecord;
import org.jatinvashisht1.core.storageengine.StorageEngine;

public class DelCommandExecutor implements ICommandExecutor {
    private final String key;
    private final StorageEngine storageEngine;

    public DelCommandExecutor(StorageEngine storageEngine, String key) {
        this.storageEngine = storageEngine;
        this.key = key;
    }

    @Override
    public String executeCommand() {
        DbRecord record = storageEngine.deleteRecord(key);
        return record == null ? "0" : "1";
    }
}
