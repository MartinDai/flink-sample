package com.doodl6.flink.common.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NoticeCountEvent {

    private String name;

    private int count;

    private Long startTime;

    private Long endTime;

    @Override
    public String toString() {
        return "NoticeCountEvent{" +
                "name='" + name + '\'' +
                ", count=" + count +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
