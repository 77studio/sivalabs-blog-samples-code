package com.sivalabs.springapp.web.controllers;

import java.io.UnsupportedEncodingException;
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
			@RequestParam(value = "sys") String sysName,
			@RequestParam(value = "host") String host,
			@RequestParam(value = "ip") String ip,
			@RequestParam(value = "type") String type,
			@RequestParam(value = "value") String value,
			@RequestParam(value = "recvs", required = false, defaultValue = "") String recvs,
			@RequestParam(value = "grps", required = false, defaultValue = "") String grps,
			@RequestParam(value = "recvType", required = false, defaultValue = "ALL") String recvType) {

		sysName = encoding(sysName, "GBK");

		Alarm alarm = new Alarm();
		alarm.setAlarmType(type);
		alarm.setAlarmValue(value);
		alarm.setCreateTime(new Date());
		alarm.setDelayMin(0);
		alarm.setGroups(grps);
		alarm.setHostName(host);
		alarm.setId(0L);
		alarm.setIpAddr(ip);
		alarm.setReceivers(recvs);
		alarm.setRecvType(recvType);
		alarm.setSysName(sysName);
		timeoutQueue.push(alarm);
	}

	private static String encoding(String before, String encode) {
		String after;
		try {
			after = new String(before.getBytes("iso-8859-1"), encode);
			return after;
		} catch (UnsupportedEncodingException e) {
			return before;
		}
	}

}
