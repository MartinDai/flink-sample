package com.doodl6.flink.common.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class NoticeEvent implements Serializable {

    private String name;

    private Long timestamp;

    @Override
    public String toString() {
        return "NoticeEvent{" +
                "name='" + name + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
