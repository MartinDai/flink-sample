package com.doodl6.flink.serializer;

import com.doodl6.flink.common.utils.JsonUtil;
import org.apache.flink.connector.kafka.sink.KafkaRecordSerializationSchema;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class Object2JsonSerializer<T> implements KafkaRecordSerializationSchema<T> {

    private static final long serialVersionUID = 1L;

    private final String topic;

    private transient Charset charset;

    public Object2JsonSerializer(String topic) {
        this(topic, StandardCharsets.UTF_8);
    }

    public Object2JsonSerializer(String topic, Charset charset) {
        this.topic = checkNotNull(topic);
        this.charset = checkNotNull(charset);
    }

    @Override
    public ProducerRecord<byte[], byte[]> serialize(T element, KafkaSinkContext context, Long timestamp) {
        String json = JsonUtil.objToJson(element);
        return new ProducerRecord<>(topic, json.getBytes(charset));
    }

    // ------------------------------------------------------------------------
    //  Java Serialization
    //  charset不支持序列化，所以需要单独处理
    // ------------------------------------------------------------------------
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
        out.writeUTF(charset.name());
    }

    private void readObject(java.io.ObjectInputStream in)
            throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        String charsetName = in.readUTF();
        this.charset = Charset.forName(charsetName);
    }
}
