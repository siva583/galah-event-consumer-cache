package com.intuit.galah.test;


import java.util.Date;
import java.util.LinkedHashSet;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import com.intuit.galah.GalahEventConsumerApplication;
import com.intuit.galah.model.Fault;
import com.intuit.galah.model.User;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = GalahEventConsumerApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class UserCacheControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void createUserTest() {	
		User request = new User("","test@test.com","test","test","test","test",new Date(),new Date());
		User response = this.restTemplate.postForObject("/user", request, User.class);
		Assert.assertEquals(request.getFirstName(), response.getFirstName());
		Assert.assertNotNull(response.getUserId());
	}
	
	@Test
	public void getAllUsersSuccessTest() {
		LinkedHashSet<?> response = this.restTemplate.getForObject("/user", LinkedHashSet.class);
		Assert.assertTrue(response.size()>1);
	}
	
	@Test
	public void updateUserTest() {
		User request = new User("","test@test.com","test","test","test","test",new Date(),new Date());
		User createResponse = this.restTemplate.postForObject("/user", request, User.class);
		createResponse.setFirstName("updated Name");
		this.restTemplate.put("/user/{userId}", createResponse, createResponse.getUserId());
		User response = this.restTemplate.getForObject("/user/{userId}", User.class,createResponse.getUserId());
		Assert.assertNotNull(response);
		Assert.assertEquals(createResponse.getFirstName(),response.getFirstName());
		
	}
	
	@Test 
	public void getUserSuccessTest() {
		User request = new User("","test@test.com","test","test","test","test",new Date(),new Date());
		User createResponse = this.restTemplate.postForObject("/user", request, User.class);
		User response = this.restTemplate.getForObject("/user/{userId}", User.class,createResponse.getUserId());
		Assert.assertNotNull(response);
		Assert.assertEquals(createResponse.getFirstName(),response.getFirstName());
	}
	
	@Test 
	public void getUserFailureTest() {
		Fault fault = this.restTemplate.getForObject("/user/{userId}", Fault.class,"abcd");
		Assert.assertNotNull(fault);
		Assert.assertTrue(fault.getErrorCode()==404);
	}

}
