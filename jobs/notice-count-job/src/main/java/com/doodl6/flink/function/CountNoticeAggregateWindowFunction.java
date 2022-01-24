package com.doodl6.flink.function;

import com.doodl6.flink.common.model.NoticeCountEvent;
import com.doodl6.flink.common.model.NoticeEvent;
import org.apache.flink.api.common.functions.AggregateFunction;
import org.apache.flink.streaming.api.functions.windowing.AggregateApplyWindowFunction;
import org.apache.flink.streaming.api.functions.windowing.WindowFunction;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;
import org.apache.flink.util.Collector;

import java.util.Iterator;

/**
 * 增量统计窗口内通知数量
 */
public class CountNoticeAggregateWindowFunction extends AggregateApplyWindowFunction<String, TimeWindow, NoticeEvent, Integer, Integer, NoticeCountEvent> {

    public CountNoticeAggregateWindowFunction() {
        super(new CountNoticeAggregateFunction(), new CountNoticeWindowFunction());
    }

    static class CountNoticeAggregateFunction implements AggregateFunction<NoticeEvent, Integer, Integer> {

        @Override
        public Integer createAccumulator() {
            return 0;
        }

        @Override
        public Integer add(NoticeEvent value, Integer accumulator) {
            return ++accumulator;
        }

        @Override
        public Integer getResult(Integer accumulator) {
            return accumulator;
        }

        @Override
        public Integer merge(Integer a, Integer b) {
            return a + b;
        }
    }

    static class CountNoticeWindowFunction implements WindowFunction<Integer, NoticeCountEvent, String, TimeWindow> {

        @Override
        public void apply(String key, TimeWindow window, Iterable<Integer> input, Collector<NoticeCountEvent> out) {
            NoticeCountEvent noticeCountEvent = new NoticeCountEvent();
            noticeCountEvent.setStartTime(window.getStart());
            noticeCountEvent.setEndTime(window.getEnd());
            noticeCountEvent.setName(key);
            Iterator<Integer> iterator = input.iterator();
            int count = 0;
            while (iterator.hasNext()) {
                count += iterator.next();
            }
            noticeCountEvent.setCount(count);
            out.collect(noticeCountEvent);
        }
    }

}
