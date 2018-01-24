package rpc.sdk.protocol;

import io.netty.buffer.ByteBuf;

/**
 * <br/>==========================
 * @author cxshun(cxshun@gmail.com)
 * @date 2017-12-25
 * <br/>==========================
 */
public interface Serialize {

    /**
     * 序列化对象，并写出到byteBuf
     * @param obj
     * @param bytebuf
     */
    void serialize(Object obj, ByteBuf bytebuf);

    /**
     * 从Bytebuf中读取并反序列化对象
     * @param byteBuf
     * @return
     */
    Object deserialize(ByteBuf byteBuf);

}
