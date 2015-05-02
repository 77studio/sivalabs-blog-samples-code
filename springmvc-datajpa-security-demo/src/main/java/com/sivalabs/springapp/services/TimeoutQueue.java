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
	// ������������alarm.getkey()ֵ����HASH�洢
	private Map<String, List<Alarm>> index;
	// ��ʱ�����飬����ʱ����������HASH�洢
	private Map<Long, Queue<Alarm>> queues;

	public TimeoutQueue() {
		index = new Hashtable<String, List<Alarm>>();
		queues = new Hashtable<Long, Queue<Alarm>>();
	}

	/**
	 * ���
	 * 
	 * @param alarm
	 */
	public void push(Alarm alarm) {
		String key = alarm.getKey();
		// ����������
		if (index.keySet().contains(key)) {
			// �Ѵ��ڣ��������뵽����
			index.get(key).add(alarm);
		} else {
			// �����ڣ�˵�����״����󣬿�ʼ�¾ۺ�����
			// ����������
			index.put(key, new LinkedList<Alarm>());
			// �����û�ж�Ӧ�ۺ�ʱ��ĳ�ʱ���У��򴴽�һ��
			long delayMin = alarm.getDelayMin();
			if (!queues.keySet().contains(delayMin))
				queues.put(delayMin, new LinkedList<Alarm>());
			// ����������Ӧ�ĳ�ʱ����
			queues.get(delayMin).add(alarm);
		}
	}

	/**
	 * �����ѳ�ʱ�����о���
	 * 
	 * @return
	 */
	public List<Alarm> pop() {
		List<Alarm> res = new ArrayList<Alarm>();
		Timestamp now = new Timestamp(System.currentTimeMillis());

		// �������г�ʱ����
		for (Long timeout : queues.keySet()) {
			Queue<Alarm> q = queues.get(timeout);
			while (!q.isEmpty())
			{
				// �ó�ʱ������û���Ѿ���ʱ�ľ���,�˳�ȥ������һ����ʱ����
				if (!q.peek().isTimeout(now))
					break;// while
				// ȡ����ʱ�ľ���
				Alarm top = q.poll();

				// ȡ�����ۺϵ��Ӿ���
				String key = top.getKey();
				top.setChildren(index.get(key));
				// �����������Ա�����´ξۺ�
				index.remove(key);
				res.add(top);
			}
		}

		return res;
	}
}
