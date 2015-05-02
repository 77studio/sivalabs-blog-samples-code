package com.sivalabs.springapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class MailSendingService {

	@Autowired
	private JavaMailSenderImpl sender;

	public void sendSimpleMail(String subject, String text, String to) {
		sendSimpleMail(subject, text, new String[] { to });
	}

	public void sendSimpleMail(String subject, String text, String[] to) {
		SimpleMailMessage smm = new SimpleMailMessage();
		// 设定邮件参数
		smm.setFrom(sender.getUsername());
		smm.setTo(to);
		smm.setSubject(subject);
		smm.setText(text);
		// 发送邮件
		sender.send(smm);
	}
}
