package rpc.client;

import com.google.gson.Gson;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.sdk.dto.RpcResponse;
import rpc.sdk.util.Logger;

/**
 * <br/>==========================
 * RPC客户端处理
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/27
 * <br/>==========================
 */
public class RpcClientHandler extends ChannelInboundHandlerAdapter {

    /**
     * 响应对象
     */
    private RpcResponse response;
    public RpcResponse getResponse() {
        return response;
    }

    private static final Gson gson = new Gson();

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Logger.info("Read message from server...");
        response = (RpcResponse) msg;
        Logger.info("server says:" + gson.toJson(response));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.info("Connection is established...");
        super.channelActive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Exception e = (Exception)cause;
        Logger.error(e.getMessage(), e);
        ctx.close();
    }
}
