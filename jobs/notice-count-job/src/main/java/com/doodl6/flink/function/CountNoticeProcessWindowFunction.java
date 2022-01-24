package com.doodl6.flink.function;

import com.doodl6.flink.common.model.NoticeCountEvent;
import com.doodl6.flink.common.model.NoticeEvent;
import org.apache.flink.streaming.api.functions.windowing.ProcessWindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * 全量统计窗口内通知数量
 */
public class CountNoticeProcessWindowFunction extends ProcessWindowFunction<NoticeEvent, NoticeCountEvent, String, TimeWindow> {

    @Override
    public void process(String key, ProcessWindowFunction<NoticeEvent, NoticeCountEvent, String, TimeWindow>.Context context, Iterable<NoticeEvent> elements, Collector<NoticeCountEvent> out) throws Exception {
        NoticeCountEvent noticeCountEvent = new NoticeCountEvent();
        noticeCountEvent.setStartTime(context.window().getStart());
        noticeCountEvent.setEndTime(context.window().getEnd());
        noticeCountEvent.setName(key);
        Iterator<NoticeEvent> iterator = elements.iterator();
        int count = 0;
        while (iterator.hasNext()) {
            iterator.next();
            count++;
        }
        noticeCountEvent.setCount(count);
        out.collect(noticeCountEvent);
    }
}
