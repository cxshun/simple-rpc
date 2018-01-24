package rpc.client.example;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import rpc.client.RpcServiceFactory;
import rpc.server.example.HelloRpc;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/28
 * <br/>==========================
 */
public class HelloRpcTest {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
        rpc.server.example.HelloRpc helloRpc = RpcServiceFactory.getClass(HelloRpc.class);
        helloRpc.sayHello("shun");
    }

}
