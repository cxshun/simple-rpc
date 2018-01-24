package rpc.sdk.dto;

import java.io.Serializable;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 * RPC请求响应
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/27
 * <br/>==========================
 */
public class RpcResponse implements Serializable{
    private static final long serialVersionUID = -3522250621167698598L;

    private Object obj;
    private ResponseCode responseCode;

    public RpcResponse() {}

    public RpcResponse(Object obj, ResponseCode responseCode) {
        this.obj = obj;
        this.responseCode = responseCode;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(ResponseCode responseCode) {
        this.responseCode = responseCode;
    }

    /**
     * <br/>==========================
     * UC国际业务部-> ucucion
     *
     * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
     * @date 2017/11/27
     * <br/>==========================
     */
    public static class ResponseCode implements Serializable{
        private static final long serialVersionUID = 3694550884161815553L;
        public static final ResponseCode SUCCESS = new ResponseCode(200, "成功");
        public static final ResponseCode SERVER_ERROR = new ResponseCode(500, "服务器错误");

        private int val;
        private String desc;
        public ResponseCode() {}

        public ResponseCode(int val, String desc, Object... args) {
            this.val = val;
            this.desc = String.format(desc, args);
        }

        public int getVal() {
            return val;
        }

        public String getDesc() {
            return desc;
        }
    }
}
