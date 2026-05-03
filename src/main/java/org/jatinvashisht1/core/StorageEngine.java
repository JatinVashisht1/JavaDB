package org.jatinvashisht1.core;

public interface StorageEngine {
    Record putRecord(Record record);
    Record getRecord(String key);
    void deleteRecord(String key);
    boolean keyExists(String key);
}
