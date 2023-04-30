package com.test.systemdesign.rest.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ResponseStatusMessage {
	SPACE_RETRIEVED_SUCCESSFULLY("Space is retrieved successfully"),
	FOLDER_RETRIEVED_SUCCESSFULLY("Folder is retrieved successfully"),
	FILE_RETRIEVED_SUCCESSFULLY("File is retrieved successfully"),
	SPACE_NOT_FOUND("No space found with this name"),
	FOLDER_NOT_FOUND("No folder found with this name"),
	FILE_NOT_FOUND("No file found with this name"),
	SPACE_CREATED_SUCCESSFULLY("Space is created successfully"),
	FOLDER_CREATED_SUCCESSFULLY("Folder is created successfully"),
	FILE_CREATED_SUCCESSFULLY("File is created successfully"),
	TECHNICAL_FAILURE("Technical failure occurred, Please contact support team."),
	PERMISSION_GROUP_NAME_INVALID("Permission group name is invalid"),
	PARENT_NOT_FOUND("Parent not found"),
	ACCESS_DENIED("User doesn't have the required permission level to perform this action");

	private String message;

	private ResponseStatusMessage(String message) {
		this.message = message;
	}

	@JsonValue
	public String getMessage() {
		return this.message;
	}
}
