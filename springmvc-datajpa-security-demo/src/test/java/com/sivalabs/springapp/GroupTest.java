package com.sivalabs.springapp;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.sivalabs.springapp.config.AppConfig;
import com.sivalabs.springapp.entities.Group;
import com.sivalabs.springapp.repositories.GroupRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes=AppConfig.class)
public class GroupTest {

	@Autowired
	private GroupRepository groupRepo;
	
	@Test
	public void testCreate() {
		Group g = new Group();
		g.setName("TestGroup");
		this.groupRepo.save(g);
	}
	
	@Test
	public void testUpdate() {
		Group g = new Group();
		g.setName("Test2Group");
		this.groupRepo.save(g);
		g = this.groupRepo.findByName("Test2Group");
		g.setName("UpdateGroup");
		this.groupRepo.save(g);
		List<Group> gs = this.groupRepo.findAll();
		for ( Group g2 : gs)
			this.groupRepo.delete(g2);
	}
	
	@Test
	public void testDelete() {
		Group g = new Group();
		g.setName("Test3Group");
		this.groupRepo.save(g);

		List<Group> gs = this.groupRepo.findAll();
		for ( Group g2 : gs)
			this.groupRepo.delete(g2);
	}
}
