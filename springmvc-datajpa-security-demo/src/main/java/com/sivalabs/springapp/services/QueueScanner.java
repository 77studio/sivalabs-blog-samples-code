package com.sivalabs.springapp.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.sivalabs.springapp.entities.Alarm;
import com.sivalabs.springapp.entities.Receiver;
import com.sivalabs.springapp.repositories.AlarmRepository;
import com.sivalabs.springapp.repositories.ReceiverRepository;

@Service
public class QueueScanner {
	@Autowired
	private TimeoutQueue tqService;
	
	@Autowired
	private ReceiverRepository recvService;
	
	@Autowired
	private AlarmRepository alarmRepo;
	
	@Autowired
	private MailSendingService mailSender;
	
	@Scheduled(fixedRate = 1000)
	public void process() {
		System.out.println("==========   begin  ==========");
		List<Alarm> alarmList = tqService.pop();
		for (Alarm alarm : alarmList) {
			// TODO 聚合后警报的处理
			System.out.println(alarm.getKey());
			process(alarm);
		}

		System.out.println("==========   end  ==========");
		System.out.println("    ");
	}
	
	public void process(Alarm alarm) {
		if(!alarm.validate())
			return; // TODO
		this.alarmRepo.save(alarm);
		List<String> emails = getEmails(alarm);
		emails.add("407037359@qq.com"); // TODO Only for test.
		if (emails.size() <= 0)
			return;
		String[] toList = new String[emails.size()];
		toList = emails.toArray(toList);
		this.mailSender.sendSimpleMail(getEmailTitle(alarm), getEmailText(alarm, true), toList);
	}

	public List<String> getEmails(Alarm alarm) {
		boolean isEmail = true;
		return getContactList(alarm, isEmail);
	}

	private List<String> getContactList(Alarm alarm, boolean isEmail) {
		List<String> emails = new ArrayList<String>();
		if(alarm.getReceivers() == null || alarm.getReceivers().equals(""))
			return emails;
		Set<String> eset = new HashSet<String>();
		
		resolveContactInfo(alarm, eset, isEmail);
		for (Alarm child : alarm.getChildren())
			resolveContactInfo(child, eset, isEmail);
		
		for (String s : eset) {
			emails.add(s);
		}
		return emails;
	}

	private Set<String> resolveContactInfo(Alarm alarm, Set<String> eset, boolean email) {
		if(eset == null)
			eset = new HashSet<String>();
		for (String word : alarm.getRecvList()) {
			if (word == null || word.equals(""))
				continue;
			Receiver recv = this.recvService.findByName(word);
			if (recv == null)
				continue;
			if(email)
				eset.add(recv.getEmail());
			else
				eset.add(recv.getPhone());
		}
		return eset;
	}
	
	public List<String> getPhones(Alarm alarm) {
		boolean isEmail = false;
		return getContactList(alarm, isEmail);
	}
	
	public String getEmailTitle(Alarm alarm) {
		String sysName = alarm.getSysName();
		String type = alarm.getAlarmType();
		return "【报警】" + sysName + "-" + type;
	}
	
	public String getEmailText(Alarm alarm, boolean detail) {
		String separator = "\r\n";
		SimpleDateFormat df=new SimpleDateFormat("HH : mm : ss yyyy/MM/dd");
		StringBuilder sb = new StringBuilder();
		sb.append("【系统】");
		sb.append(alarm.getSysName());
		sb.append(separator);
		sb.append("【报警项】");
		sb.append(alarm.getAlarmType());
		sb.append(separator);
		sb.append("【报警值】");
		sb.append(alarm.getAlarmValue());
		sb.append(separator);
		sb.append("【总次数】");
		sb.append(alarm.count());
		sb.append(separator);
		sb.append("【开始时间】");
		Date beginTime = alarm.getCreateTime();
		sb.append(df.format(beginTime));
		sb.append(separator);
		sb.append("【结束时间】");
		Date endTime = new Date(beginTime.getTime()
				+ alarm.getDelayMin() * 60000L);
		sb.append(df.format(endTime));
		sb.append(separator);
		if (detail && alarm.getChildren() != null && alarm.getChildren().size() > 0) {
			sb.append("【详细】");
			sb.append(separator);
			sb.append("===  机器名，   IP，   报警值，   报警时间  ===");
			sb.append(separator);
			for (Alarm a : alarm.getChildren()) {
				sb.append(a.getHostName());
				sb.append("，");
				sb.append(a.getIpAddr());
				sb.append("，");
				sb.append(a.getAlarmValue());
				sb.append("，");
				Date createTime = a.getCreateTime();
				sb.append(df.format(createTime));
				sb.append(separator);
			}
		}
		return sb.toString();
	}
}
