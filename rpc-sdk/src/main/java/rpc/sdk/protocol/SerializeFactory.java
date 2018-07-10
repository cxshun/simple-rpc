package rpc.sdk.protocol;

/**
 * factory class used to get serialize protocol
 * <br/>==========================
 * @author cxshun(cxshun@gmail.com)
 * @date 2017-12-25
 * <br/>==========================
 */
public class SerializeFactory {

    public static Serialize getSerialize(String protocol) {
        switch(protocol) {
            case SerializeProtocol.HESSIAN:
                return new HessianSerialization();
            case SerializeProtocol.KRYO:
                return new KryoSerialization();
            default:
                break;
        }
        return new HessianSerialization();
    }

}
