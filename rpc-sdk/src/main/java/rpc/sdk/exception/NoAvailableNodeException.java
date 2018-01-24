package rpc.sdk.exception;

/**
 * <br/>==========================
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/29
 * <br/>==========================
 */
public class NoAvailableNodeException extends Exception{

    private String msg;

    public NoAvailableNodeException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }

}
