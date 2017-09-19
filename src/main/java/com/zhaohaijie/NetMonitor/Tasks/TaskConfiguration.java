package com.zhaohaijie.NetMonitor.Tasks;

import com.zhaohaijie.NetMonitor.Logging.Log;
import com.zhaohaijie.NetMonitor.Logging.LogFactory;
import com.zhaohaijie.NetMonitor.Utils.ClassUtils;
import org.apache.commons.configuration2.XMLConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by ZHJ on 2/15/2017.
 */
public class TaskConfiguration {
    protected Log logger = LogFactory.getLog();

    private Configurations configs = new Configurations();
    protected XMLConfiguration config;

    public TaskConfiguration() throws ConfigurationException {
        File configFile = new File("codetest.config.xml");
        if(!configFile.exists()){
            logger.error("can't find codetest.config.xml in : " + configFile.getAbsolutePath());
        }
        config = configs.xml(configFile.getAbsolutePath());
    }

    public XMLConfiguration getConfig() {
        return config;
    }

    public List<TaskBuilder> getTaskBuilders() {

        List<TaskBuilder> builders = new LinkedList<>();

        try {

            List<Object> classes = this.getConfig().getList("//task/taskbuilder");

            for (Object obj : classes) {
                if (obj != null && obj.toString().length() > 0) {
                    TaskBuilder builder = ClassUtils.getInstanceOf(obj.toString(), TaskBuilder.class);

                    if(builder != null){
                        logger.info("Load builder: " + obj.toString());
                        builders.add(builder);
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Failed to getGithubSearchKeyWord", ex);
        }

        logger.info("Search keyword: " + (builders != null ? builders.size() : "0"));

        return builders;
    }

    public List<TaskWorker> getTaskWorkers(String taskname) {

        List<TaskWorker> workers = new LinkedList<>();

        try {

            List<Object> classes = this.getConfig().getList("//task[name=" + taskname + "]/workers/worker[@name]");

            for (Object obj : classes) {
                if (obj != null && obj.toString().length() > 0) {
                    TaskWorker worker = ClassUtils.getInstanceOf(obj.toString(), TaskWorker.class);

                    if(worker != null){
                        logger.info("Load worker: " + obj.toString());

                        List<TaskResultProcessor> taskResultProcessors = getTaskResultProcessor(taskname, obj.toString());

                        for(TaskResultProcessor processor: taskResultProcessors){
                            worker.registerTaskResultProcessor(processor);
                        }

                        workers.add(worker);
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Failed to getGithubSearchKeyWord", ex);
        }

        logger.info("Search keyword: " + (workers != null ? workers.size() : "0"));

        return workers;
    }

    /**
     * Get Task Result processor, this is configuration from the xml file;
     * @return Get Task Result processor
     */
    private List<TaskResultProcessor> getTaskResultProcessor(String taskname, String workername){
        List<TaskResultProcessor> processors = new LinkedList<>();

        try {

            List<Object> classes = this.getConfig().getList("//task[name=" + taskname + "]/workers/worker[name=" + workername + "]/taskresultprocessors/taskresultprocessor");

            for (Object obj : classes) {
                if (obj != null && obj.toString().length() > 0) {
                    TaskResultProcessor processor = ClassUtils.getInstanceOf(obj.toString(), TaskResultProcessor.class);

                    if(processor != null){
                        processors.add(processor);
                        logger.info("Load processor: " + obj.toString());
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("Failed to getGithubSearchKeyWord", ex);
        }

        logger.info("Search keyword: " + (processors != null ? processors.size() : "0"));

        return processors;
    }
}
