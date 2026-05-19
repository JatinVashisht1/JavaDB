package org.jatinvashisht1.core.storageengine;

import org.jatinvashisht1.core.record.DbRecord;

public interface StorageEngine {
    DbRecord putRecord(String key, DbRecord dbRecord);
    DbRecord getRecord(String key);
    DbRecord deleteRecord(String key);
    boolean expireRecord(String key, long expiresAtMillis);
    boolean keyExists(String key);
}
