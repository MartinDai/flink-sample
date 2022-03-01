package com.doodl6.flink.trigger;

import org.apache.flink.streaming.api.windowing.triggers.Trigger;
import org.apache.flink.streaming.api.windowing.triggers.TriggerResult;
import org.apache.flink.streaming.api.windowing.windows.Window;

/**
 * 超时自动触发并清除窗口元素的触发器
 */
public class TimeoutPurgeTrigger<T, W extends Window> extends Trigger<T, W> {

    private static final long serialVersionUID = 1L;

    private final Trigger<T, W> nestedTrigger;

    private TimeoutPurgeTrigger(Trigger<T, W> nestedTrigger) {
        this.nestedTrigger = nestedTrigger;
    }

    @Override
    public TriggerResult onElement(T element, long timestamp, W window, TriggerContext ctx) throws Exception {
        TriggerResult triggerResult = nestedTrigger.onElement(element, timestamp, window, ctx);
        return triggerResult.isFire() ? TriggerResult.FIRE_AND_PURGE : triggerResult;
    }

    @Override
    public TriggerResult onEventTime(long time, W window, TriggerContext ctx) throws Exception {
        TriggerResult triggerResult = this.nestedTrigger.onEventTime(time, window, ctx);
        return triggerResult.isFire() ? TriggerResult.FIRE_AND_PURGE : triggerResult;
    }

    @Override
    public TriggerResult onProcessingTime(long time, W window, TriggerContext ctx) throws Exception {
        nestedTrigger.onProcessingTime(time, window, ctx);
        return TriggerResult.FIRE_AND_PURGE;
    }

    @Override
    public void clear(W window, TriggerContext ctx) throws Exception {
        nestedTrigger.clear(window, ctx);
    }

    @Override
    public boolean canMerge() {
        return nestedTrigger.canMerge();
    }

    @Override
    public void onMerge(W window, OnMergeContext ctx) throws Exception {
        nestedTrigger.onMerge(window, ctx);
    }

    @Override
    public String toString() {
        return "TimeoutPurgeTrigger(" + nestedTrigger.toString() + ")";
    }

    public static <T, W extends Window> TimeoutPurgeTrigger<T, W> of(Trigger<T, W> nestedTrigger) {
        return new TimeoutPurgeTrigger<>(nestedTrigger);
    }

}
