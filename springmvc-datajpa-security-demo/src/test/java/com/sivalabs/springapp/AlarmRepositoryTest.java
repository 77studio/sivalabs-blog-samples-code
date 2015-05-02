package com.sivalabs.springapp;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sivalabs.springapp.config.AppConfig;
import com.sivalabs.springapp.entities.Alarm;
import com.sivalabs.springapp.repositories.AlarmRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class AlarmRepositoryTest {

	@Autowired
	private AlarmRepository alarmRepo;
	
	@Test
	public void create() {
		Alarm alarm = new Alarm();
		alarm.setAlarmType("ÄÚ´æ");
		alarm.setAlarmValue("<=%5");
		alarm.setCreateTime(new Date());
		alarm.setDelayMin(5);
		alarm.setGroups("Group1");
		alarm.setHostName("cnhq-01");
		alarm.setId(0L);
		alarm.setIpAddr("192.168.187.199");
		alarm.setReceivers("Recv1");
		alarm.setRecvType("ALL");
		alarm.setSysName("Spark¼¯Èº");
		alarmRepo.save(alarm);
	}
}
