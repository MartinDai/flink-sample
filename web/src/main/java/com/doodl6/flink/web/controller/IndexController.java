package com.doodl6.flink.web.controller;

import com.doodl6.flink.common.model.NoticeEvent;
import com.doodl6.flink.web.service.KafkaService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class IndexController {

    @Resource
    private KafkaService kafkaService;

    @GetMapping("/sendNoticeEvent")
    public String sendNoticeEvent(String name) {
        Assert.isTrue(StringUtils.isNoneBlank(name), "name不能为空");
        NoticeEvent event = new NoticeEvent();
        event.setName(name);
        event.setTimestamp(System.currentTimeMillis());
        kafkaService.sendEvent(event);

        return "success";
    }
}
