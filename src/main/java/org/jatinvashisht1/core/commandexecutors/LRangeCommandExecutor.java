package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.record.DbRecord;
import org.jatinvashisht1.core.record.RecordDataType;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.util.List;

public class LRangeCommandExecutor implements CommandExecutor{
    private static final RecordDataType LIST_DATA_TYPE = RecordDataType.LIST;
    private final String key;
    private final StorageEngine storageEngine;

    public LRangeCommandExecutor(StorageEngine storageEngine, String key) {
        this.storageEngine = storageEngine;
        this.key = key;
    }

    @Override
    public String executeCommand() {
        DbRecord record = storageEngine.getRecord(key);
        if (record == null) {
            return "(empty list)";
        }

        if (record.getDataType() != LIST_DATA_TYPE) {
            throw new IllegalArgumentException("WRONGTYPE Operation against a key holding wrong kind of value");
        }

        List<String> existingList = (List<String>) record.getValue();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < existingList.size(); i++) {
            stringBuilder.append(i + 1).append(") \"").append(existingList.get(i)).append("\"\n");
        }
        return stringBuilder.toString().trim();
    }
}
