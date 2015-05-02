package com.sivalabs.springapp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sivalabs.springapp.config.AppConfig;
import com.sivalabs.springapp.entities.Receiver;
import com.sivalabs.springapp.services.ReceiverService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = AppConfig.class)
public class ReceiverServiceTest {
	@Autowired
	private ReceiverService userService;

	// @Test
	// public void findAllUsers() {
	// List<Receiver> users = userService.findAll();
	// assertNotNull(users);
	// assertTrue(!users.isEmpty());
	// }

	@Test
	public void findUserById() {
		Receiver receiver = userService.findReceiverById(1);
		assertNotNull(receiver);
	}

	@Test
	public void createUser() {
		Receiver receiver = new Receiver(0, "Îå", "siva@gmail.com",
				"13814195872", null);
		Receiver savedReceiver = userService.createReceiver(receiver);
		Receiver newReceiver = userService.findReceiverById(savedReceiver
				.getId());
		assertEquals("Îå", newReceiver.getName());
		assertEquals("siva@gmail.com", newReceiver.getEmail());
		assertEquals("13814195872", newReceiver.getPhone());
	}

	@Test
	public void deleteUser() {
		Receiver receiver = userService.findReceiverByName("Îå");
		assertNotNull(receiver);
		userService.deleteReceiver(receiver.getId());
	}

}
