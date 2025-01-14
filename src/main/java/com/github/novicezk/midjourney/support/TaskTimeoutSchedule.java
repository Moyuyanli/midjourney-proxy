package com.github.novicezk.midjourney.support;

import com.github.novicezk.midjourney.ProxyProperties;
import com.github.novicezk.midjourney.enums.TaskStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度器
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class TaskTimeoutSchedule {
	private final TaskQueueHelper taskQueueHelper;
	private final ProxyProperties properties;

	/**
	 * 检查超时
	 */
	@Scheduled(fixedRate = 30000L)
	public void checkTasks() {
		//系统时间
		long currentTime = System.currentTimeMillis();
		//超时时间
		long timeout = TimeUnit.MINUTES.toMillis(this.properties.getQueue().getTimeoutMinutes());
		//任务队列
		List<Task> tasks = this.taskQueueHelper.findRunningTask(new TaskCondition())
				.filter(t -> currentTime - t.getStartTime() > timeout)
				.toList();
		for (Task task : tasks) {
			//任务状态
			if (Set.of(TaskStatus.FAILURE, TaskStatus.SUCCESS).contains(task.getStatus())) {
				log.warn("task status is failure/success but is in the queue, end it. id: {}", task.getId());
			} else {
				log.debug("task timeout, id: {}", task.getId());
				task.fail("任务超时");
			}
			Future<?> future = this.taskQueueHelper.getRunningFuture(task.getId());
			if (future != null) {
				future.cancel(true);
			}
			this.taskQueueHelper.changeStatusAndNotify(task, task.getStatus());
		}
	}
}
