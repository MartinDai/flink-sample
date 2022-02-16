package com.doodl6.flink;

import com.doodl6.flink.common.KafkaTopicConstant;
import com.doodl6.flink.common.model.NoticeCountEvent;
import com.doodl6.flink.common.model.NoticeEvent;
import com.doodl6.flink.function.CountNoticeAggregateWindowFunction;
import com.doodl6.flink.serializer.Json2ObjectDeserializer;
import com.doodl6.flink.serializer.Object2JsonSerializer;
import org.apache.flink.api.common.eventtime.TimestampAssignerSupplier;
import org.apache.flink.api.common.eventtime.WatermarkStrategy;
import org.apache.flink.connector.kafka.sink.KafkaSink;
import org.apache.flink.connector.kafka.source.KafkaSource;
import org.apache.flink.connector.kafka.source.enumerator.initializer.OffsetsInitializer;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.datastream.WindowedStream;
import org.apache.flink.streaming.api.environment.CheckpointConfig;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import org.apache.flink.streaming.api.windowing.triggers.EventTimeTrigger;
import org.apache.flink.streaming.api.windowing.triggers.ProcessingTimeoutTrigger;
import org.apache.flink.streaming.api.windowing.windows.TimeWindow;

import java.time.Duration;

public class NoticeCountJob {

    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment executionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment();
        //5秒钟触发一次checkpoint
        executionEnvironment.enableCheckpointing(5000);
        CheckpointConfig checkpointConfig = executionEnvironment.getCheckpointConfig();
        //同时只允许1个checkpoint并发
        checkpointConfig.setMaxConcurrentCheckpoints(1);
        //两次checkpoint之间至少5秒钟
        checkpointConfig.setMinPauseBetweenCheckpoints(5000);

        String bootstrapServers = "172.16.2.231:9092";
        //接收kafka消息
        String sourceTopic = KafkaTopicConstant.NOTICE;
        //创建kafka数据来源
        KafkaSource<NoticeEvent> kafkaSource = KafkaSource.<NoticeEvent>builder()
                .setBootstrapServers(bootstrapServers)
                .setGroupId("flink-group")
                .setTopics(sourceTopic)
                .setValueOnlyDeserializer(new Json2ObjectDeserializer<>(NoticeEvent.class))
                .setStartingOffsets(OffsetsInitializer.latest())//指定从最新的偏移量开始消费
                .build();

        DataStream<NoticeEvent> dataStream = executionEnvironment.fromSource(kafkaSource,
                WatermarkStrategy.<NoticeEvent>forBoundedOutOfOrderness(Duration.ZERO)//超过时间窗口的数据不处理
                        //使用NoticeEvent对象的timestamp作为EventTime
                        .withTimestampAssigner((TimestampAssignerSupplier<NoticeEvent>) context -> (element, recordTimestamp) -> element.getTimestamp()),
                sourceTopic + "_kafka_source").name("noticeKafkaSource").uid("noticeKafkaSource");

        //备份数据到kafka数据源
        KafkaSink<NoticeEvent> kafkaBackupSink = KafkaSink.<NoticeEvent>builder()
                .setBootstrapServers(bootstrapServers)
                .setRecordSerializer(new Object2JsonSerializer<>(KafkaTopicConstant.NOTICE_BACKUP))
                .build();

        dataStream.sinkTo(kafkaBackupSink).name("noticeBackup").uid("noticeBackup");

        WindowedStream<NoticeEvent, String, TimeWindow> windowedStream = dataStream
                //根据name分组
                .keyBy(NoticeEvent::getName)
                //一分钟一个窗口
                .window(TumblingEventTimeWindows.of(Time.minutes(1)))
                //一分钟自动关闭窗口
                .trigger(ProcessingTimeoutTrigger.of(EventTimeTrigger.create(), Duration.ofSeconds(60), false, false));

        //对每个窗口的数据执行计算统计数量
        SingleOutputStreamOperator<NoticeCountEvent> noticeCountEventStream = windowedStream.apply(new CountNoticeAggregateWindowFunction()).name("countNotice").uid("countNotice");

        //创建kafka作为输出源
        KafkaSink<NoticeCountEvent> kafkaSink = KafkaSink.<NoticeCountEvent>builder()
                .setBootstrapServers(bootstrapServers)
                .setRecordSerializer(new Object2JsonSerializer<>(KafkaTopicConstant.NOTICE_COUNT))
                .build();

        //打印数据流，本地debug的时候可以使用
//        noticeCountEventStream.print();

        //发送数据到kafka
        noticeCountEventStream.sinkTo(kafkaSink).name("sendNoticeCount").uid("sendNoticeCount");

        executionEnvironment.execute(NoticeCountJob.class.getSimpleName());
    }
}
