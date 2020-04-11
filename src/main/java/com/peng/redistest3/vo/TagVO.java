package com.peng.redistest3.vo;

import java.io.Serializable;

public class TagVO implements Serializable {
    private static final long serialVersionUID=1;

    private long id;
    private String topic;
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
