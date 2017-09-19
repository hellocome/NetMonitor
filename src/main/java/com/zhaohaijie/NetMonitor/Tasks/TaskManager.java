package com.zhaohaijie.NetMonitor.Tasks;

import java.util.List;

/**
 * Created by ZHJ on 16/02/2017.
 */
public interface TaskManager {
    boolean startTaskManager();
    void addTask(AbstractTask task);
    void addAllTasks(List<AbstractTask> tasks);
}
