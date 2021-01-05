package com.arlenchen.pojo;

import javax.persistence.*;

/**
 * @author arlenchen
 */
@Table(name = "PDMAN_DB_VERSION")
public class PdmanDbVersion {
    @Column(name = "DB_VERSION")
    private String dbVersion;

    @Column(name = "VERSION_DESC")
    private String versionDesc;

    @Column(name = "CREATED_TIME")
    private String createdTime;

    /**
     * @return DB_VERSION
     */
    public String getDbVersion() {
        return dbVersion;
    }

    /**
     * @param dbVersion
     */
    public void setDbVersion(String dbVersion) {
        this.dbVersion = dbVersion;
    }

    /**
     * @return VERSION_DESC
     */
    public String getVersionDesc() {
        return versionDesc;
    }

    /**
     * @param versionDesc
     */
    public void setVersionDesc(String versionDesc) {
        this.versionDesc = versionDesc;
    }

    /**
     * @return CREATED_TIME
     */
    public String getCreatedTime() {
        return createdTime;
    }

    /**
     * @param createdTime
     */
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }
}