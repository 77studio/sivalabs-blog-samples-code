package com.sivalabs.springapp.repositories;

import org.springframework.data.repository.CrudRepository;

import com.sivalabs.springapp.entities.Receiver;

public interface ReceiverRepository extends CrudRepository<Receiver, Integer>{
	public Receiver findByName(String name);
}
