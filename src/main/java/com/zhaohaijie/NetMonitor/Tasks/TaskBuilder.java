package com.zhaohaijie.NetMonitor.Tasks;

/**
 * Created by ZHJ on 2/15/2017.
 */
public interface TaskBuilder {
    /**
     * Set task manager for using in the task builder
     * @param taskManager task manager for using in the task builder
     */
    void setTaskManager(TaskManager taskManager);

    /**
     * Set the task name which will be used in the task builder to lookup the related settings
     *
     * @param taskName set the task name in the task builder.
     */
    void setTaskName(String taskName);

    /**
     * Start task builder
     */
    void startTaskBuilder();

    /**
     * Stop task builder
     */
    void stopTaskBuilder();
}
