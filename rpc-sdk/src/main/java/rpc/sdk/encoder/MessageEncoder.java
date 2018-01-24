package rpc.sdk.encoder;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import rpc.sdk.protocol.Serialize;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/27
 * <br/>==========================
 */
public class MessageEncoder extends MessageToByteEncoder<Object> {

    private Serialize serialize;
    public MessageEncoder(Serialize serialize) {
        this.serialize = serialize;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        serialize.serialize(msg, out);
    }
}
