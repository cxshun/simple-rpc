package rpc.sdk.dto;

import java.io.Serializable;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 * RPC请求对象
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/27
 * <br/>==========================
 */
public class RpcRequest implements Serializable{
    private static final long serialVersionUID = -3432433811350669741L;

    private String clsName;
    private String methodName;
    private Object[] parameters;

    public RpcRequest() {}

    public RpcRequest(String clsName, String methodName, Object[] parameters) {
        this.clsName = clsName;
        this.methodName = methodName;
        this.parameters = parameters;
    }

    public String getClsName() {
        return clsName;
    }

    public void setClsName(String clsName) {
        this.clsName = clsName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getParameters() {
        return parameters;
    }

    public void setParameters(Object[] parameters) {
        this.parameters = parameters;
    }
}
