package org.jatinvashisht1.core.record;

public class Record {
    private final String key;
    private Object value;
    private final long createdAt;
    private long updatedAt;
    private long lastAccessedAt;
    private final RecordDataType dataType;

    public Record(String key, RecordDataType dataType, Object value) {
        this.key = key;
        this.dataType = dataType;
        this.value = value;

        final long currentTimeMillis = System.currentTimeMillis();
        this.createdAt = currentTimeMillis;
        this.updatedAt = currentTimeMillis;
        this.lastAccessedAt = currentTimeMillis;
    }

    public Object getValue() {
        return value;
    }

    public long getLastAccessedAt() {
        return lastAccessedAt;
    }

    public String getKey() {
        return key;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public long getUpdatedAt() {
        return updatedAt;
    }

    public RecordDataType getDataType() {
        return dataType;
    }

    public void setValue(Object value) {
        validateTypeMatch(value);
        this.value = value;
        this.updatedAt = System.currentTimeMillis();
    }

    public void markLastAccessedAt() {
        this.lastAccessedAt = System.currentTimeMillis();
    }

    private void validateTypeMatch(Object value) throws IllegalArgumentException {
        if (value == null) throw new IllegalArgumentException("Database values cannot be null");

        switch (this.dataType) {
            case STRING:
                if (!(value instanceof String)) throw new IllegalArgumentException("WRONGTYPE: Operation against a key holding the wrong kind of value");
                break;

            case LIST:
                if (!(value instanceof java.util.List)) throw new IllegalArgumentException("WRONGTYPE: Expected a List");
                break;

            case SET:
                if (!(value instanceof java.util.Set)) throw new IllegalArgumentException("WRONGTYPE: Expected a Set");
                break;
        }
    }
}
