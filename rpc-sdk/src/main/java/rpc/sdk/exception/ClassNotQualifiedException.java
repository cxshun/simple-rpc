package rpc.sdk.exception;

/**
 * class is not qualified to be execute as rpc class
 * @author chenxs(chenxs @ xiaopeng.com)——车联网基础平台
 * 2018-07-10 15:41
 **/
public class ClassNotQualifiedException extends Exception{

    private String msg;

    public ClassNotQualifiedException(String msg) {
        this.msg = msg;
    }

    @Override
    public String getMessage() {
        return this.msg;
    }
}
