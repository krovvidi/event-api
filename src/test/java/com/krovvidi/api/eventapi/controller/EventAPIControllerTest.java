package com.krovvidi.api.eventapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class EventAPIControllerTest {

	@Autowired
	MockMvc mockMvc;

	@Test
	public void test_getEvents_firstName_bad() throws Exception {
		mockMvc.perform(get("/events?firstName=S")).andDo(print());
	}

	@Test
	public void test_getEvents_lastName_bad() throws Exception {
		mockMvc.perform(get("/events?lastName=K")).andDo(print());
	}

	@Test
	public void test_getEvents_firstName_good() throws Exception {
		mockMvc.perform(get("/events?firstName=Suresh")).andDo(print());
	}

	@Test
	public void test_getEvents_lastName_good() throws Exception {
		mockMvc.perform(get("/events?lastName=Krovvidi")).andDo(print());
	}
	
	@Test
	public void test_getEvents_lastName_invalid() throws Exception {
		mockMvc.perform(get("/events?lastNme=Krovvidi")).andDo(print());
	}
	
	@Test
	public void test_getEvents_zipCode_bad() throws Exception {
		mockMvc.perform(get("/events?zipCode=KKKK")).andDo(print());
	}
	
	@Test
	public void test_getEvents_zipCode_good() throws Exception {
		mockMvc.perform(get("/events?zipCode=45040")).andDo(print());
	}
}
