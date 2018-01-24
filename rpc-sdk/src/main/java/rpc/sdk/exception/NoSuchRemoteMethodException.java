package rpc.sdk.exception;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 * 找不到指定的远程方法异常
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/27
 * <br/>==========================
 */
public class NoSuchRemoteMethodException extends Exception {

    private String msg;

    public NoSuchRemoteMethodException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return msg;
    }

}
