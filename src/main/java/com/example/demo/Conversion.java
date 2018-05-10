package com.example.demo;

import java.sql.Timestamp;

public class Conversion {

    private Integer id;
    private Timestamp timestamp;
    private String filename;
    private Integer size;

    public Conversion() {
        super();
    }

    public Conversion(Integer id, Timestamp timestamp, String filename, Integer size) {
        super();
        this.id = id;
        this.timestamp = timestamp;
        this.filename = filename;
        this.size = size;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
