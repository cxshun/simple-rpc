package rpc.sdk.protocol;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * <br/>==========================
 * @author cxshun(cxshun@gmail.com)
 * @date 2017-12-26
 * <br/>==========================
 */
public class KryoSerialization implements Serialize{
    @Override
    public void serialize(Object obj, ByteBuf bytebuf) {
        Output output = new Output(new ByteArrayOutputStream());

        Kryo kryo = new Kryo();
        kryo.writeClassAndObject(output, obj);
        output.close();
        bytebuf.writeBytes(output.getBuffer());
    }

    @Override
    public Object deserialize(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        Input input = new Input(new ByteArrayInputStream(bytes));
        Kryo kryo = new Kryo();
        Object obj = kryo.readClassAndObject(input);
        input.close();
        return obj;
    }
}
