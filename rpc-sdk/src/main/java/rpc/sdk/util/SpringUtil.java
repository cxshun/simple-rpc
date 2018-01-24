package rpc.sdk.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * <br/>==========================
 * UC国际业务部-> ucucion
 *
 * @author xiaoshun.cxs（xiaoshun.cxs@alibaba-inc.com）
 * @date 2017/11/27
 * <br/>==========================
 */
@Component
public class SpringUtil implements ApplicationContextAware{

    private static ApplicationContext ctx;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ctx = applicationContext;
    }

    /**
     * 获取指定类的Bean
     * @param clazz 指定需要获取bean的类全限定名
     * @return
     */
    public static Object getBean(Class<?> clazz) {
        return ctx.getBean(clazz);
    }

}
