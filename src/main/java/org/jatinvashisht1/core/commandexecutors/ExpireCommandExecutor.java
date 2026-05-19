package org.jatinvashisht1.core.commandexecutors;

import org.jatinvashisht1.core.storageengine.StorageEngine;

public class ExpireCommandExecutor implements ICommandExecutor{
    private final String key;
    private final StorageEngine storageEngine;
    private final long expireAfterMillis;

    public ExpireCommandExecutor(String key, StorageEngine storageEngine, long expireAfterMillis) {
        this.key = key;
        this.storageEngine = storageEngine;
        this.expireAfterMillis = expireAfterMillis;
    }

    @Override
    public String executeCommand() {
        long expireAtMillis = System.currentTimeMillis() + expireAfterMillis;
        boolean recordUpdated = storageEngine.expireRecord(key, expireAtMillis);
        return recordUpdated ? "1" : "0";
    }
}
