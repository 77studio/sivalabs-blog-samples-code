package com.sivalabs.springapp.entities;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

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

}
