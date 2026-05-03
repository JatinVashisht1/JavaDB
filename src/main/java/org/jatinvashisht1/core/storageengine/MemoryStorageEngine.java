package org.jatinvashisht1.core.storageengine;

import org.jatinvashisht1.core.record.DbRecord;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorageEngine implements StorageEngine{
    private final ConcurrentHashMap<String, DbRecord> memoryStore = new ConcurrentHashMap<>();

    @Override
    public DbRecord putRecord(String key, DbRecord dbRecord) throws IllegalArgumentException{
        if (key == null || dbRecord == null) throw new IllegalArgumentException("Key and DbRecord cannot be null");
        memoryStore.put(key, dbRecord);
        return dbRecord;
    }

    @Override
    public DbRecord getRecord(String key) throws IllegalArgumentException {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        DbRecord dbRecord = memoryStore.get(key);
        if (dbRecord != null) dbRecord.markLastAccessedAt();
        return dbRecord;
    }

    @Override
    public void deleteRecord(String key) throws  IllegalArgumentException{
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        memoryStore.remove(key);
    }

    @Override
    public boolean keyExists(String key) throws IllegalArgumentException {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        return memoryStore.containsKey(key);
    }
}
