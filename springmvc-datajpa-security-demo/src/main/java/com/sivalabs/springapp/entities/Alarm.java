package com.sivalabs.springapp.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.sivalabs.springapp.util.AlarmType;
import com.sivalabs.springapp.util.RecvType;

@Entity
@Table(name = "ALARMS_TAB")
public class Alarm {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Column(nullable = false)
	private String sysName;
	@Column(nullable = false)
	private String hostName;
	@Column(nullable = false)
	private String ipAddr;
	@Column(nullable = false)
	private String alarmType;
	@Column(nullable = false)
	private String alarmValue;
	@Column(nullable = false)
	private int delayMin;
	@Column(nullable = false)
	private String receivers;
	@Column(nullable = false)
	private String groups;
	@Column(nullable = false)
	private String recvType;
	@Column(nullable = false)
	private Date createTime;
	@Transient
	private List<Alarm> children;

	public Alarm() {
	}

	public String getAlarmType() {
		return alarmType;
	}

	public String getAlarmValue() {
		return alarmValue;
	}

	public List<Alarm> getChildren() {
		return children;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public int getDelayMin() {
		return this.delayMin;
	}

	public String getGroups() {
		return groups;
	}

	public String getHostName() {
		return hostName;
	}

	public long getId() {
		return id;
	}

	public String getIpAddr() {
		return ipAddr;
	}

	public String getKey() {
		return "{" + this.getSysName() + "," + this.getHostName() + ","
				+ this.getAlarmType() + "}";
	}

	public String getReceivers() {
		return receivers;
	}

	public String getRecvType() {
		return recvType;
	}

	public String getSysName() {
		return sysName;
	}

	public boolean isTimeout(Date now) {
		if (this.getDelayMin() <= 0)
			return true;
		Date creatTimestamp = this.getCreateTime();
		long delay = now.getTime() - creatTimestamp.getTime();
		int msPerMin = 60 * 1000;
		return delay > this.getDelayMin() * msPerMin;
	}

	public void setAlarmType(String alarmType) {
		this.alarmType = alarmType;
	}

	public void setAlarmValue(String alarmValue) {
		this.alarmValue = alarmValue;
	}

	public void setChildren(List<Alarm> children) {
		this.children = children;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDelayMin(int delayMin) {
		this.delayMin = delayMin;
	}

	public void setGroups(String groups) {
		this.groups = groups;
	}

	public void setHostName(String hostName) {
		this.hostName = hostName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIpAddr(String ipAddr) {
		this.ipAddr = ipAddr;
	}

	public void setReceivers(String receivers) {
		this.receivers = receivers;
	}

	public void setRecvType(String recvType) {
		this.recvType = recvType;
	}

	public void setSysName(String sysName) {
		this.sysName = sysName;
	}

	public int count() {
		int countSelf = 1;
		if(this.getChildren() == null)
			return countSelf;
		int countChildren = this.getChildren().size();
		return countSelf + countChildren;
	}
	
	public boolean validate() {
		if(isNullOrEmpty(this.getSysName()))
			return false;
		if(isNullOrEmpty(this.getAlarmType()))
			return false;
		if(isNullOrEmpty(this.getAlarmValue()))
			return false;
		if(isNullOrEmpty(this.getGroups()) && isNullOrEmpty(this.getReceivers()))
			return false;
		if(isNullOrEmpty(this.getHostName()))
			return false;
		if(isNullOrEmpty(this.getIpAddr()))
			return false;
		if(isNullOrEmpty(this.getRecvType()))
			return false;

		if(this.getCreateTime() == null)
			return false;
		if(this.getRecvList().size() <= 0 && this.getGrpList().size() <= 0)
			return false;
		
		if (this.resolveAlarmType() == null)
			return false;
		if (this.resolveRecvType() == null)
			return false;
		
		return true;
	}

	public List<String> getRecvList() {
		return resolvePlusList(false);
	}

	private List<String> resolvePlusList(boolean group) {
		String listStr = null;
		if (group)
			listStr = this.getGroups();
		else
			listStr = this.getReceivers();
			
		List<String> res = new ArrayList<String>();
		if (isNullOrEmpty(listStr))
			return res;
		for(String word : listStr.split("+")) {
			if (isNullOrEmpty(word))
				continue;
			res.add(word);
		}
		return res;
	}
	
	public List<String> getGrpList() {
		return resolvePlusList(true);
	}
	
	private static boolean isNullOrEmpty(String str) {
		return str == null || str.equals("");
	}
	
	public AlarmType resolveAlarmType() {
		try {
			return AlarmType.valueOf(this.getAlarmType());
		} catch (Exception e) {
			return null;
		}
	}
	
	public RecvType resolveRecvType() {
		try {
			return RecvType.valueOf(this.getRecvType());
		} catch (Exception e) {
			return null;
		}
	}
}
