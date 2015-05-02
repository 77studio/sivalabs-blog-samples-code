package com.sivalabs.springapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sivalabs.springapp.entities.Receiver;
import com.sivalabs.springapp.repositories.ReceiverRepository;

@Service
@Transactional
public class ReceiverService {

	@Autowired
	private ReceiverRepository receiverRepository;

	public Receiver createReceiver(Receiver receiver) {
		return receiverRepository.save(receiver);
	}

	public void deleteReceiver(int id) {
		receiverRepository.delete(id);
	}

	public Receiver findReceiverById(int id) {
		return receiverRepository.findOne(id);
	}

	public Receiver findReceiverByName(String name) {
		return receiverRepository.findByName(name);
	}

	public Receiver update(Receiver receiver) {
		return receiverRepository.save(receiver);
	}

}
