package com.zhaohaijie.NetMonitor.Tasks;

/**
 * Created by ZHJ on 16/02/2017.
 */
public interface TaskResult<T> {
    /**
     * Get result object
     * @return get result object which is in T format.
     */
    T getResultObject();

    /**
     * get task info object, which can be any type, depends on the usage. it's just for data transfer.
     * @return task info object, which can be any type, depends on the usage, it's just for data transfer.
     */
    Object getTaskObject();
}
