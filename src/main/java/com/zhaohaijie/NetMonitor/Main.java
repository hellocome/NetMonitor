package com.zhaohaijie.NetMonitor;

import com.zhaohaijie.NetMonitor.Tasks.ThreadPoolTaskManager;

public class Main {

    public static void main(String[] args) {
        ThreadPoolTaskManager.getThreadPoolTaskManager().startTaskManager();
    }
}
