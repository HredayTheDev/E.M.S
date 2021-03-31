package com.example.emsapp.models;

public class PgNumber {
    private String pushKey;
    private String pgId;

    public PgNumber() {
    }

    public PgNumber(String pushKey, String pgId) {
        this.pushKey = pushKey;
        this.pgId = pgId;
    }

    public String getPushId() {
        return pushKey;
    }

    public void setPushId(String pushKey) {
        this.pushKey = pushKey;
    }

    public String getPgId() {
        return pgId;
    }

    public void setPgId(String pgId) {
        this.pgId = pgId;
    }

    @Override
    public String toString() {
        return "PgNumber{" +
                "pushKey='" + pushKey + '\'' +
                ", pgId='" + pgId + '\'' +
                '}';
    }
}
