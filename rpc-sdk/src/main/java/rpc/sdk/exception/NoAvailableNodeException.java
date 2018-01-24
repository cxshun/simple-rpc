package rpc.sdk.exception;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
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
