package org.nakonechnyi.domain;

import java.util.Date;

/**
 * @autor A_Nakonechnyi
 * @date 16.10.2016.
 */
public class ArchivedTask extends Task{

    java.sql.Date archivedOn;
    int originalId;

    public ArchivedTask(int id, String name, Date date, int priority, byte statusDone, java.sql.Date archivedOn, int originalId) {
        super(id, name, date, priority, statusDone);
        this.setArchivedOn(archivedOn);
        this.setOriginalId(originalId);
    }

    public ArchivedTask(String name, Date date, int priority, byte statusDone, java.sql.Date archivedOn, int originalId) {
        super (name, date, priority);
        this.setStatusDone(statusDone);
        this.setArchivedOn(archivedOn);
        this.setOriginalId(originalId);
    }

    public java.sql.Date getArchivedOn() {
        return archivedOn;
    }

    public void setArchivedOn(java.sql.Date archivedOn) {
        this.archivedOn = archivedOn;
    }

    public int getOriginalId() {
        return originalId;
    }

    public void setOriginalId(int originalId) {
        this.originalId = originalId;
    }
}
