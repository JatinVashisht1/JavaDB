package org.jatinvashisht1.core.storageengine;

import org.jatinvashisht1.core.record.Record;

public interface StorageEngine {
    org.jatinvashisht1.core.record.Record putRecord(String key, org.jatinvashisht1.core.record.Record record);
    Record getRecord(String key);
    void deleteRecord(String key);
    boolean keyExists(String key);
}
