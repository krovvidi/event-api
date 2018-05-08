package com.krovvidi.api.eventapi.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.krovvidi.api.eventapi.model.Event;
import com.krovvidi.api.eventapi.repository.EventAPIRepository;

@Validated
@RestController
public class EventAPIController {

	private static final Logger logger = LoggerFactory.getLogger(EventAPIController.class);
	private static final String NAME_VALIDATION_REGEX = "([a-zA-Z]){2,100}";
	private static final String ZIP_CODE_REGEX = "^[0-9]{5}?$";
	@Autowired
	EventAPIRepository eventAPIRepository;

	@GetMapping("/events")
	@ResponseBody
	public ResponseEntity<List<Event>> findEvents(
			@Valid @Pattern(regexp = NAME_VALIDATION_REGEX) @RequestParam(required = false, value = "firstName") String firstName,
			@Valid @Size(min = 2, max = 100, message = "lastName length must be between 2 and 100") @RequestParam(required = false, value = "lastName") String lastName,
			@RequestParam(required = false, value = "maidenName") String maidenName,
			@Valid @Pattern(regexp = ZIP_CODE_REGEX) @RequestParam(required = false, value = "zipCode") String zipCode) {
		List<Event> events = null;
		Map<String, String> requestParams = new HashMap<>();
		requestParams.put("firstName", firstName);
		requestParams.put("lastName", lastName);
		requestParams.put("maidenName", maidenName);
		requestParams.put("zipCode", zipCode);
		try {
			logger.info("requestParams: " + requestParams);
			logger.debug("Before getEvents");
			events = eventAPIRepository.getEvents(requestParams);
			logger.debug("After getEvents: " + events);
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<List<Event>>(events, HttpStatus.OK);
	}
}
