package com.zhaohaijie.NetMonitor.Tasks;

/**
 * Created by ZHJ on 16/02/2017.
 */
public interface Filter {

    /**
     *
     * @param result result from task
     * @return <code>true</code>  means processor want this result and <code>true</code>  means don't want
     *
     */
    public boolean decide(TaskResult result);

    /**
     *
     * @param result original result
     * @return TaskResult rebuild the result meet the requirement.
     */
    public TaskResult rebuildResult(TaskResult result);
}
