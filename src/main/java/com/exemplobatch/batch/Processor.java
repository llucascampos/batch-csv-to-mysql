package com.exemplobatch.batch;

import java.util.HashMap;
import java.util.Map;

import org.springframework.batch.item.ItemProcessor;

import com.exemplobatch.entity.User;

public class Processor implements ItemProcessor<User, User> {

	private static final Map<String, String> DEPT_NAMES = new HashMap<>();
			
	public Processor() {
		DEPT_NAMES.put("001", "Consultant");
		DEPT_NAMES.put("002", "BusinessAnalyst");
		DEPT_NAMES.put("003", "HR");
		}
	
	@Override
	public User process(User user) throws Exception {
	     String code = user.getDept();
	     String department = DEPT_NAMES.get(code);
	     user.setDept(department);
	     System.out.println(String.format("Converted from [%s] to [%s]", code, department));
	     return user;
	  }

}
