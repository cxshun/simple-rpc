package rpc.server;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.stream.Stream;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import rpc.sdk.dto.RpcRequest;
import rpc.sdk.dto.RpcResponse;
import rpc.sdk.dto.RpcResponse.ResponseCode;
import rpc.sdk.util.Logger;
import rpc.sdk.util.SpringUtil;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/27
 * <br/>==========================
 */
public class RpcServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Logger.info("Received connect from client...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Logger.info("Received request from client...");

        if (!ctx.channel().isWritable()) {
            return;
        }

        //得到具体的内容
        RpcRequest request = (RpcRequest) msg;

        //找到客户端请求的指定类的bean
        Object obj = SpringUtil.getBean(Class.forName(request.getClsName()));
        if (obj == null) {
            ctx.channel().writeAndFlush(new RpcResponse(null, new ResponseCode(500, "找不到[%s]spring bean",
                request.getClsName())));
            return;
        }
        //找到客户端请求的方法
        final boolean[] hasMethod = new boolean[1];
        Method[] methods = obj.getClass().getMethods();
        Stream.of(methods).forEach(method -> {
            if (method.getName().equalsIgnoreCase(request.getMethodName())) {
                try {
                    Object result = method.invoke(obj, request.getParameters());
                    ctx.channel().writeAndFlush(new RpcResponse(result, ResponseCode.SUCCESS));
                    hasMethod[0] = true;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    Logger.error(e.getMessage(), e);
                }
            }
        });

        //如果找不到方法，返回错误给客户端
        if (!hasMethod[0]) {
            ctx.channel().writeAndFlush(new RpcResponse(null, new ResponseCode(500, "找不到[%s]方法",
                request.getMethodName())));
            Logger.error("找不到指定的[{}]方法", request.getMethodName());
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
        Exception exception = (Exception) cause;
        Logger.error(exception.getMessage(), exception);
        ctx.close();
    }
}
