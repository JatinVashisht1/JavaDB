package org.jatinvashisht1.core.storageengine;

import org.jatinvashisht1.core.record.Record;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorageEngine implements StorageEngine{
    private final ConcurrentHashMap<String, org.jatinvashisht1.core.record.Record> memoryStore = new ConcurrentHashMap<>();

    @Override
    public org.jatinvashisht1.core.record.Record putRecord(String key, org.jatinvashisht1.core.record.Record record) throws IllegalArgumentException{
        if (key == null || record == null) throw new IllegalArgumentException("Key and Record cannot be null");
        memoryStore.put(key, record);
        return record;
    }

    @Override
    public org.jatinvashisht1.core.record.Record getRecord(String key) throws IllegalArgumentException {
        if (key == null) throw new IllegalArgumentException("Key cannot be null");
        Record record = memoryStore.get(key);
        if (record != null) record.markLastAccessedAt();
        return record;
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
