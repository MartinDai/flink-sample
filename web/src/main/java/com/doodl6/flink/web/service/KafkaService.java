package com.doodl6.flink.web.service;

import com.doodl6.flink.common.KafkaTopicConstant;
import com.doodl6.flink.common.model.NoticeEvent;
import com.doodl6.flink.common.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class KafkaService {

    @Autowired
    private KafkaTemplate<String, NoticeEvent> template;

    public void sendEvent(NoticeEvent noticeEvent) {
        template.send(KafkaTopicConstant.NOTICE, noticeEvent);
        log.info("sendEvent:" + JsonUtil.objToJson(noticeEvent));
    }
}
