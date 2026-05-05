package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.record.DbRecord;
import org.jatinvashisht1.core.record.RecordDataType;
import org.jatinvashisht1.core.storageengine.StorageEngine;

import java.util.ArrayList;
import java.util.List;

public class RPushCommandExecutor implements ICommandExecutor {
    private final static RecordDataType LIST_DATA_TYPE = RecordDataType.LIST;
    private final String key;
    private final String value;
    private final StorageEngine storageEngine;

    public RPushCommandExecutor(StorageEngine storageEngine, String key, String value) {
        this.storageEngine = storageEngine;
        this.key = key;
        this.value = value;
    }

    @Override
    public String executeCommand() {
        DbRecord record = storageEngine.getRecord(key);
        if (record == null) {
            ArrayList<String> newList = new ArrayList<>();
            newList.add(value);
            storageEngine.putRecord(key, new DbRecord(LIST_DATA_TYPE, newList));
            return "1";
        }

        if (record.getDataType() != LIST_DATA_TYPE) {
            throw new IllegalArgumentException("WRONGTYPE Operation against a key holding wrong kind of value");
        }

        List<String> existingList = (List<String>) record.getValue();
        existingList.add(value);
        storageEngine.putRecord(key, new DbRecord(LIST_DATA_TYPE, existingList));
        return String.valueOf(existingList.size());
    }
}
