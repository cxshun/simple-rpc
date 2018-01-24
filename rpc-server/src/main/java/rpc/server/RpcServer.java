package rpc.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.sdk.decoder.MessageDecoder;
import rpc.sdk.encoder.MessageEncoder;
import rpc.sdk.loadbalance.ZkServerHandler;
import rpc.sdk.protocol.Serialize;
import rpc.sdk.protocol.SerializeFactory;
import rpc.sdk.protocol.SerializeProtocol;
import rpc.sdk.util.Logger;
import rpc.sdk.util.SpringUtil;

/**
 * <br/>==========================
 * RPC服务
 *
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/27
 * <br/>==========================
 */
public class RpcServer {

    private String host;
    private int port;

    public RpcServer(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 启动RPC服务器
     */
    public void start() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
            .channel(NioServerSocketChannel.class)
            .childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    Serialize serialize = SerializeFactory.getSerialize(SerializeProtocol.HESSIAN);
                    ch.pipeline().addLast(new MessageEncoder(serialize), new MessageDecoder(serialize), new RpcServerHandler());
                }
            })
            .option(ChannelOption.SO_BACKLOG, 128)
            .childOption(ChannelOption.SO_KEEPALIVE, true);

        try {
            ChannelFuture f = bootstrap.bind(host, port).sync();

            //增加新的服务器结点到zookeeper
            ((ZkServerHandler)SpringUtil.getBean(ZkServerHandler.class)).addNode(host + ":" + port, host + ":" + port);
            f.channel().closeFuture().sync();
            Logger.info("Server is started...");
        } catch (InterruptedException e) {
            Logger.error(e.getMessage(), e);
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        new RpcServer(args[0], Integer.parseInt(args[1])).start();
        Logger.info("Server is started...");
    }

}
