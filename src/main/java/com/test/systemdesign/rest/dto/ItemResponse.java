package com.test.systemdesign.rest.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)
public class ItemResponse {
	private boolean success;
	private ResponseStatusMessage message;
	private Object data;
}
