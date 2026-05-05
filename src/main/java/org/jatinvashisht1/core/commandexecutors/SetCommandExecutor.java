package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.record.DbRecord;
import org.jatinvashisht1.core.record.RecordDataType;
import org.jatinvashisht1.core.storageengine.StorageEngine;

public class SetCommandExecutor implements ICommandExecutor {
    private final StorageEngine storageEngine;
    private final static RecordDataType STRING_DATA_TYPE = RecordDataType.STRING;
    private final String key;
    private final String value;

    public SetCommandExecutor(StorageEngine storageEngine, String key, String value) {
        this.storageEngine = storageEngine;
        this.key = key;
        this.value = value;
    }

    @Override
    public String executeCommand() {
        DbRecord record = new DbRecord(STRING_DATA_TYPE, value);
        storageEngine.putRecord(key, record);
        return "OK";
    }
}
