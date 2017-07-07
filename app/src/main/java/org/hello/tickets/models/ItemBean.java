package org.hello.tickets.models;

/**
 * Created by dhanushi_s on 7/5/2017.
 */

public class ItemBean {
    private String id;
    private String subject;
    private String type;
    private String priority;
    private String status;

    public ItemBean(String id, String subject, String type, String priority, String status) {
        this.id       = id;
        this.subject  = subject;
        this.type     = type;
        this.priority = priority;
        this.status   = status;
    }

    public String getId() {
        return id;
    }

    public String getSubject() {
        return subject;
    }

    public String getType() {
        return type;
    }

    public String getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }
}
