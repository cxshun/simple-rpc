package rpc.sdk.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * <br/>==========================
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/27
 * <br/>==========================
 */
@Inherited
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
@Component
public @interface RpcService {
}
