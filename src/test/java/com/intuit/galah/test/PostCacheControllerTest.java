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
import com.intuit.galah.model.Post;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = GalahEventConsumerApplication.class,webEnvironment = WebEnvironment.RANDOM_PORT)
@TestPropertySource(locations="classpath:application.properties")
public class PostCacheControllerTest {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void createPostTest() {
		Post request = new Post("","1234","1234 post",new Date());
		Post response = this.restTemplate.postForObject("/post", request, Post.class);
		Assert.assertNotNull(response.getPostId());	
	}
	
	@Test
	public void getAllPostsTest() {
		LinkedHashSet<?> response = this.restTemplate.getForObject("/post", LinkedHashSet.class);
		Assert.assertTrue(response.size()>1);
	}
	
	@Test
	public void getPostTest() {
		Post request = new Post("","12345","12345 post",new Date());
		Post createResponse = this.restTemplate.postForObject("/post", request, Post.class);
		Post response = this.restTemplate.getForObject("/post/{postId}", Post.class,createResponse.getPostId());
		Assert.assertNotNull(response);
	}
	
	@Test
	public void getPostFailureTest() {
		Fault response = this.restTemplate.getForObject("/post/{postId}", Fault.class,"abcd");
		Assert.assertNotNull(response);
		Assert.assertTrue(response.getErrorCode()==404);
	}

}
