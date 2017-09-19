package com.zhaohaijie.NetMonitor.Utils;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;

/**
 * Created by ZHJ on 16/02/2017.
 */
public class ClassUtils {
    protected static Log logger = LogFactory.getLog();

    /**
     * Load instance of object from class name, the constructor must be no parameter
     * @param className
     * @param <T>
     * @return
     */
    public static <T> T getInstanceOf(String className, Class<T> type) {
        try {
            T object = type.cast(Class.forName(className).newInstance());
            return object;

        }catch(Exception ex){
            logger.error("Failed to load class: " + className, ex);
        }

        return null;
    }
}
