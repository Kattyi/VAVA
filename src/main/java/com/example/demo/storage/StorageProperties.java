package com.example.demo.storage;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("storage")
public class StorageProperties {

    /**
     * Folder location for storing files
     */
    private String location = "upload-dir";
    private String convertedLocation = "converted-dir";

    public String getLocation() {
        return location;
    }
    public String getConvertedLocation() { return convertedLocation; }

    public void setLocation(String location) {
        this.location = location;
    }

}
