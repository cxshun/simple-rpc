package rpc.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.util.CollectionUtils;
import rpc.sdk.decoder.MessageDecoder;
import rpc.sdk.dto.RpcRequest;
import rpc.sdk.encoder.MessageEncoder;
import rpc.sdk.exception.NoAvailableNodeException;
import rpc.sdk.loadbalance.ZkServerHandler;
import rpc.sdk.protocol.Serialize;
import rpc.sdk.protocol.SerializeFactory;
import rpc.sdk.protocol.SerializeProtocol;
import rpc.sdk.util.Logger;
import rpc.sdk.util.SpringUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import java.util.Random;

/**
 * <br/>==========================
 * RPC代理工厂类，使用java动态代理来实现
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/27
 * <br/>==========================
 */
public class RpcServiceFactory implements InvocationHandler{

    private ZkServerHandler zkServerHandler = (ZkServerHandler) SpringUtil.getBean(ZkServerHandler.class);

    private static ThreadLocal<Class<?>> clazzLocal = new ThreadLocal<>();

    @SuppressWarnings("unchecked")
    public static <T> T getClass(Class<?> clazz) {
        clazzLocal.set(clazz);

        RpcServiceFactory serviceFactory = new RpcServiceFactory();
        return (T)Proxy.newProxyInstance(serviceFactory.getClass().getClassLoader(), new Class[]{clazz}, serviceFactory);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //构造请求对象
        RpcRequest request = new RpcRequest(clazzLocal.get().getName(), method.getName(), args);
        final RpcClientHandler clientHandler = new RpcClientHandler();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup).channel(NioSocketChannel.class).option(ChannelOption.SO_KEEPALIVE, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        Serialize serialize = SerializeFactory.getSerialize(SerializeProtocol.HESSIAN);
                        ch.pipeline().addLast(new MessageEncoder(serialize), new MessageDecoder(serialize), clientHandler);
                    }
                });

            //得到可以连接的server
            String[] serverConfig = getServer().split(":");
            ChannelFuture f = bootstrap.connect(serverConfig[0], Integer.parseInt(serverConfig[1])).sync();

            f.channel().writeAndFlush(request).sync();
            f.channel().closeFuture().sync();
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
            throw e;
        } finally {
            workerGroup.shutdownGracefully();
        }
        return clientHandler.getResponse();
    }

    /**
     * 获取可以使用的服务器的配置
     * 暂时使用随机进行获取
     * @return
     */
    private String getServer() throws NoAvailableNodeException {
        List<String> availableNodes = zkServerHandler.availableNodes();

        if (CollectionUtils.isEmpty(availableNodes)) {
            throw new NoAvailableNodeException("找不到可用的服务器节点");
        }
        int serverIdx = new Random().nextInt(availableNodes.size());
        return availableNodes.get(serverIdx);
    }

}
