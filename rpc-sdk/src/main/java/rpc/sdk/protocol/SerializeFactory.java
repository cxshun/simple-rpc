package rpc.sdk.protocol;

/**
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
        }
        return new HessianSerialization();
    }

}
