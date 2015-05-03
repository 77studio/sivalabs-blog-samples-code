package com.sivalabs.springapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sivalabs.springapp.entities.Group;

public interface GroupRepository extends JpaRepository<Group, Integer> {
	public Group findByName(String name);
}
