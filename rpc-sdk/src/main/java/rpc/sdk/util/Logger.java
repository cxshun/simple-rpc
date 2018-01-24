package rpc.sdk.util;

import org.slf4j.LoggerFactory;

/**
 * <br/>==========================
 * 日志帮助类
 * @author cxshun(cxshun@gmail.com)
 * @date 2017/11/27
 * <br/>==========================
 */
public class Logger {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(Logger.class);

    /**
     * 根据logger名称获取日志配置
     * @param loggerName
     * @return
     */
    public static org.slf4j.Logger getLogger(String loggerName) {
        return LoggerFactory.getLogger(loggerName);
    }

    public static void info(String msg) {
        LOGGER.info(msg);
    }

    public static void debug(String msg) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug(msg);
        }
    }

    public static void error(String msg, Exception e) {
        LOGGER.error(msg, e);
    }

    public static void error(String msg, Object... args) {
        LOGGER.error(msg, args);
    }

}
