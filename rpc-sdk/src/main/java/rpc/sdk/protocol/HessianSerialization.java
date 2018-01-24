package rpc.sdk.protocol;

import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import io.netty.buffer.ByteBuf;
import rpc.sdk.util.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017-12-25
 * <br/>==========================
 */
public class HessianSerialization implements Serialize{

    @Override
    public void serialize(Object obj, ByteBuf byteBuf) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(baos);
        try {
            output.startMessage();
            output.writeObject(obj);
            output.completeMessage();
            output.close();

            byteBuf.writeBytes(baos.toByteArray());
        } catch (Exception e) {
            Logger.error(e.getMessage(), e);
        } finally {
            try {
                baos.close();
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Object deserialize(ByteBuf byteBuf) {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);

        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        Hessian2Input input = new Hessian2Input(bais);
        try {
            input.startMessage();
            Object object = input.readObject();
            input.completeMessage();
            return object;
        } catch (IOException e) {
            Logger.error(e.getMessage(), e);
        } finally {
            try {
                input.close();
                bais.close();
            } catch (IOException e) {
                Logger.error(e.getMessage(), e);
            }
        }
        return null;
    }

}
