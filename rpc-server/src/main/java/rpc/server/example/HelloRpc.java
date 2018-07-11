package rpc.server.example;

import rpc.sdk.annotation.RpcService;

/**
 * <br/>==========================
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/28
 * <br/>==========================
 */
@RpcService
public interface HelloRpc {

    void sayHello(String name);

}
