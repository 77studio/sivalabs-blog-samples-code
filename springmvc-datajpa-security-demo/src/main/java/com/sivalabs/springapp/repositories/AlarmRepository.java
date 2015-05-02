package com.sivalabs.springapp.repositories;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.springapp.entities.Alarm;

public interface AlarmRepository extends JpaRepository<Alarm, String> {

	public List<Alarm> findByCreateTimeBetween(Date begin, Date end);

	public List<Alarm> findBySysName(String sysName);

	public List<Alarm> findByAlarmType(String alarmType);

	public List<Alarm> findBySysNameAndAlarmType(String sysName,
			String alarmType);
}
