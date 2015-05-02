package com.sivalabs.springapp;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sivalabs.springapp.config.AppConfig;
import com.sivalabs.springapp.services.MailSendingService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class MailTest {
	@Autowired
	private MailSendingService mailService;

	@Test
	public void findAllUsers() {
		mailService.sendSimpleMail("Maven Test", "MAVEN≤‚ ‘” º˛°£" + new Date(),
				"407037359@qq.com");
	}

}
