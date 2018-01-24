package rpc.sdk.exception;

/**
 * <br/>==========================
 * 找不到指定的远程方法异常
 * @author cxshun(cxshun@gmail.com)
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
