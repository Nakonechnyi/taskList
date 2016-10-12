package org.nakonechnyi.domain;

import java.util.Date;

/**
 * @autor A_Nakonechnyi
 * @date 12.10.2016.
 */
public class Task {

    int id;
    String name;
    Date date;
    int priority;
    byte statusDone;

    public Task( String name, Date date, int priority ) {
        this.name = name;
        this.date = date;
        this.priority = priority;
    }

    public Task(int id, String name, Date date, int priority, byte statusDone) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.priority = priority;
        this.statusDone = statusDone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public byte getStatusDone() {
        return statusDone;
    }

    public void setStatusDone(byte statusDone) {
        this.statusDone = statusDone;
    }
}
