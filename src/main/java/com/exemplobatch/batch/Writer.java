package com.exemplobatch.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.exemplobatch.repository.UserRepository;

@ Component 
public class Writer implements ItemWriter {

	@Autowired
	private UserRepository userRepository;

	@Override
	public void write(List items) throws Exception {
		userRepository.saveAll(items);
		
	}
	


}
