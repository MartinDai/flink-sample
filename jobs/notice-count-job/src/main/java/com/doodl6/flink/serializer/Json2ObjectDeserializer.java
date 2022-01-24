package com.doodl6.flink.serializer;

import com.doodl6.flink.common.utils.JsonUtil;
import org.apache.flink.api.common.serialization.DeserializationSchema;
import org.apache.flink.api.common.typeinfo.TypeInformation;
import org.apache.flink.api.java.typeutils.PojoTypeInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.apache.flink.util.Preconditions.checkNotNull;

public class Json2ObjectDeserializer<T> implements DeserializationSchema<T> {

    private static final long serialVersionUID = 1L;

    private final Class<T> typeClass;

    private transient Charset charset;

    public Json2ObjectDeserializer(Class<T> typeClass) {
        this(typeClass, StandardCharsets.UTF_8);
    }

    public Json2ObjectDeserializer(Class<T> typeClass, Charset charset) {
        this.typeClass = checkNotNull(typeClass);
        this.charset = checkNotNull(charset);
    }

    @Override
    public T deserialize(byte[] message) {
        String json = new String(message, charset);
        return JsonUtil.jsonToObj(json, typeClass);
    }

    @Override
    public boolean isEndOfStream(T nextElement) {
        return false;
    }

    @Override
    public TypeInformation<T> getProducedType() {
        return PojoTypeInfo.of(typeClass);
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
