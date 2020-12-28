package com.apress.cloud.task;

import lombok.extern.log4j.Log4j2;
import org.springframework.cloud.task.listener.annotation.AfterTask;
import org.springframework.cloud.task.listener.annotation.BeforeTask;
import org.springframework.cloud.task.listener.annotation.FailedTask;
import org.springframework.cloud.task.repository.TaskExecution;

@Log4j2
public class TaskDemoListener {

    @BeforeTask
    public void beforeTask(TaskExecution taskExecution) {
        log.debug("[@BeforeTask] - {}", taskExecution);
    }

    @AfterTask
    public void afterTask(TaskExecution taskExecution) {
        log.debug("[@AfterTask] - {}", taskExecution);
    }

    @FailedTask
    public void failedTask(TaskExecution taskExecution, Throwable throwable) {
        log.debug("[@FailedTask] - {}", taskExecution);
        log.error("[@FailedTask] - {}", throwable);
    }
}

/*
@Log4j2
public class TaskDemoListener implements TaskExecutionListener {

    @Override
    public void onTaskStartup(TaskExecution taskExecution) {
        log.debug("[onTaskStartup] - {}", taskExecution);
    }

    @Override
    public void onTaskEnd(TaskExecution taskExecution) {
        log.debug("[onTaskEnd] - {}", taskExecution);
    }

    @Override
    public void onTaskFailed(TaskExecution taskExecution, Throwable throwable) {
        log.debug("[onTaskFailed] - {}", taskExecution);
        log.error("[onTaskFailed] - {}", throwable);
    }
}
*/