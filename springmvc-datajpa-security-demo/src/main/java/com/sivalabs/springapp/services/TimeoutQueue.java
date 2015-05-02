package com.sivalabs.springapp.services;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.springapp.entities.Alarm;

@Service
@Transactional
public class TimeoutQueue {
	// 警报索引，按alarm.getkey()值进行HASH存储
	private Map<String, List<Alarm>> index;
	// 超时队列组，按超时分钟数进行HASH存储
	private Map<Long, Queue<Alarm>> queues;

	public TimeoutQueue() {
		index = new Hashtable<String, List<Alarm>>();
		queues = new Hashtable<Long, Queue<Alarm>>();
	}

	/**
	 * 入队
	 * 
	 * @param alarm
	 */
	public void push(Alarm alarm) {
		String key = alarm.getKey();
		// 检索索引表
		if (index.keySet().contains(key)) {
			// 已存在，警报加入到索引
			index.get(key).add(alarm);
		} else {
			// 不存在，说明是首次请求，开始新聚合任务
			// 建立索引项
			index.put(key, new LinkedList<Alarm>());
			// 如果还没有对应聚合时间的超时队列，则创建一个
			long delayMin = alarm.getDelayMin();
			if (!queues.keySet().contains(delayMin))
				queues.put(delayMin, new LinkedList<Alarm>());
			// 警报加入相应的超时队列
			queues.get(delayMin).add(alarm);
		}
	}

	/**
	 * 出队已超时的所有警报
	 * 
	 * @return
	 */
	public List<Alarm> pop() {
		List<Alarm> res = new ArrayList<Alarm>();
		Timestamp now = new Timestamp(System.currentTimeMillis());

		// 遍历所有超时队列
		for (Long timeout : queues.keySet()) {
			Queue<Alarm> q = queues.get(timeout);
			while (!q.isEmpty())
			{
				// 该超时队列中没有已经超时的警报,退出去处理下一个超时队列
				if (!q.peek().isTimeout(now))
					break;// while
				// 取出超时的警报
				Alarm top = q.poll();

				// 取出被聚合的子警报
				String key = top.getKey();
				top.setChildren(index.get(key));
				// 清理索引，以便进行下次聚合
				index.remove(key);
				res.add(top);
			}
		}

		return res;
	}
}
