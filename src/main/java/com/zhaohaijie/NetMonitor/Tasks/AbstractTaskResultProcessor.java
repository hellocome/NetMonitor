package com.zhaohaijie.NetMonitor.Tasks;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Created by ZHJ on 2/17/2017.
 */
public abstract class AbstractTaskResultProcessor implements TaskResultProcessor {
    protected static final Log logger = LogFactory.getLog();
    // might be accessed by thread,
    private final List<Filter> filters = new CopyOnWriteArrayList<>();

    @Override
    public void addFilter(Filter newFilter){
        if(!filters.contains(newFilter)){
            filters.add(newFilter);
        }
    }

    // bad performance, need to improve
    protected TaskResult applyFilter(TaskResult result){
        TaskResult tempResult = result;

        if(result != null && filters.size() > 0) {
            for (Filter filter : filters) {
                tempResult = filter.rebuildResult(tempResult);
            }
        }

        return tempResult;
    }

    // bad performance, need to improve
    protected boolean decide(TaskResult result){
        if(result != null && filters.size() > 0) {
            for (Filter filter : filters) {
                if(!filter.decide(result)){
                    return false;
                }
            }
        }

        return true;
    }
}
