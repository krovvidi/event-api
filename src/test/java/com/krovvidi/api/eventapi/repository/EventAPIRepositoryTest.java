package com.krovvidi.api.eventapi.repository;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.krovvidi.api.eventapi.model.Event;

@RunWith(SpringRunner.class)
@SpringBootTest
@Configuration
@EnableWebMvc
public class EventAPIRepositoryTest {

	@Autowired
	EventAPIRepository eventAPIRepository;
	
	@Test
	public void test_getEvents() throws Exception {
		Map<String, String> requestParams = new HashMap<>();
		requestParams.put("firstName", "Suresh");
		List<Event> events = eventAPIRepository.getEvents(requestParams);
		assertNotNull(events);
		assertFalse(events.isEmpty());
	}
	
}
