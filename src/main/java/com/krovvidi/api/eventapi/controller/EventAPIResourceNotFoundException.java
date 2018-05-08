package com.krovvidi.api.eventapi.controller;

public class EventAPIResourceNotFoundException extends RuntimeException {

	private Long resourceId;

	public EventAPIResourceNotFoundException(Long resourceId, String message) {
		super(message);
		this.resourceId = resourceId;
	}
}