package org.jatinvashisht1.core;

import java.util.concurrent.ConcurrentHashMap;

public class MemoryStorageEngine implements StorageEngine{
    private final ConcurrentHashMap<String, Record> memoryStore = new ConcurrentHashMap<>();

    @Override
    public Record putRecord(String key, Record record) throws IllegalArgumentException{
        if (key == null || record == null) throw new IllegalArgumentException("Key and Record cannot be null");
        memoryStore.put(key, record);
        return record;
    }

    @Override
    public Record getRecord(String key) throws IllegalArgumentException {
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
