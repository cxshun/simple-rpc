package rpc.sdk.protocol;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
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
