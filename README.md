## 简单的RPC服务（包含服务端和客户端）
底层使用到了`zookeeper`,`netty`，`zookeeper`主要用于`server`的发现，而`netty`主要是用于进行客户端和服务端之间的`RPC`交互

### `rpc-client`为客户端模块
### `rpc-server`为服务端模块
### `rpc-sdk`为SDK模块，`server`和`client`都需要依赖它

### 运行
+ 修改`server`的`config.properties`文件，修改`zookeeper`配置
+ 修改`client`的`config.properties`文件，修改`zookeeper`配置
+ 运行`server`的`RpcServer`，注意，需要两个运行参数用于指定netty的启动IP和端口，一般使用`localhost`和`随便端口`即可
+ 运行`client`的`HelloRpcTest`，看到
> server says XXX

就是已经成功访问了RPC接口了

### TODO
+ 增加`protobuf`的支持
+ 增加`thrift`的支持
