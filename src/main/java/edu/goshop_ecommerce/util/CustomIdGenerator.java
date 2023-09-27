package edu.goshop_ecommerce.util;

import java.util.Date;
import java.util.Random;
import java.util.UUID;

import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@SuppressWarnings("serial")
@Component
public class CustomIdGenerator implements IdentifierGenerator {

	@Override
	public Object generate(SharedSessionContractImplementor session, Object object) {
		String baseId = UUID.randomUUID().toString();
		return getRandomTen(baseId);
	}

	private Object getRandomTen(String baseId) {
		baseId += new Date(System.currentTimeMillis()).toString();
		StringBuilder customId = new StringBuilder();
		customId.append("URT ");
		
		Random random = new Random();
		for(int i=0; i<10; i++) {
			char c = baseId.charAt(random.nextInt(baseId.length()));
			if(c != ' ') {
				customId.append(c);
			}else
				i--;
		}
		return customId.toString();
	}

}
