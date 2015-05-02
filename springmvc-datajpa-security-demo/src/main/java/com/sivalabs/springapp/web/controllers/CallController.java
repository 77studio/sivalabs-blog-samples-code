package com.sivalabs.springapp.web.controllers;

import java.util.Date;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.sivalabs.springapp.entities.Alarm;
import com.sivalabs.springapp.services.TimeoutQueue;

@Controller
public class CallController {
	@Autowired
	private TimeoutQueue timeoutQueue;
	
	@RequestMapping(value="alarm", method=RequestMethod.GET)
	public void alarm(
			HttpServletResponse response,
			@RequestParam(value = "host") String host,
			@RequestParam(value = "ip") String ip,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "value") String value,
			@RequestParam(value = "recvId") String recvId,
			@RequestParam(value = "recvType", required = false, defaultValue = "3") String recvType) {
		Alarm alarm = new Alarm();
		alarm.setAlarmType("ÄÚ´æ");
		alarm.setAlarmValue("<=%5");
		alarm.setCreateTime(new Date());
		alarm.setDelayMin(0);
		alarm.setGroups("Group1");
		alarm.setHostName("cnhq-01");
		alarm.setId(0L);
		alarm.setIpAddr("192.168.187.199");
		alarm.setReceivers("Recv1");
		alarm.setRecvType("ALL");
		alarm.setSysName("Spark¼¯Èº");
		timeoutQueue.push(alarm);
	}

}
