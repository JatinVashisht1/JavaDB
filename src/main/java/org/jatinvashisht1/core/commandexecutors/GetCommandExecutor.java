package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.record.DbRecord;
import org.jatinvashisht1.core.storageengine.StorageEngine;

public class GetCommandExecutor implements CommandExecutor{
    private final String key;
    private final StorageEngine storageEngine;

    public GetCommandExecutor(StorageEngine storageEngine, String key) {
        this.key = key;
        this.storageEngine = storageEngine;
    }

    @Override
    public String executeCommand() {
        DbRecord record = storageEngine.getRecord(key);

        if (record == null) {
            return "(nil)";
        }

        return record.getValue().toString();
    }
}
