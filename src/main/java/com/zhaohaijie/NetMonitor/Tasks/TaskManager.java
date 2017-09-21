package com.zhaohaijie.NetMonitor.Tasks;

import java.util.List;

/**
 * Created by ZHJ on 16/02/2017.
 */
public interface TaskManager {

    /**
     * Start task manager
     * @return <code>true</code> start successful  <code>false</code> fail to start task manager
     */
    boolean startTaskManager();

    /**
     * Add task to the task manager, which will be queued and execute at right time
     * @param task Abstract task to be queued
     */
    void addTask(AbstractTask task);

    /**
     * Add tasks to the task manager, which will be queued and execute at right time, prefer to use concurrent List
     * @param tasks Abstract tasks to be queued
     */
    void addAllTasks(List<AbstractTask> tasks);
}
