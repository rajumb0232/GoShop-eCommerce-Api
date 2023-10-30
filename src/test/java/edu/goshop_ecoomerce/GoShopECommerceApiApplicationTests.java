package edu.goshop_ecoomerce;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import edu.goshop_ecommerce.repo.UserRepo;

@SpringBootTest(classes = SpringExtension.class)
//@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = TestConfig.class)
//@WebAppConfiguration
class GoShopECommerceApiApplicationTests {

	@Mock
	UserRepo repo;

	MockMvc mockMvc;
	@Autowired
	WebApplicationContext wac;

	@BeforeEach
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(wac).apply(SecurityMockMvcConfigurers.springSecurity()).build();
	}

	@Test
	void contextLoads() {
		System.out.println("working...");
	}

}
