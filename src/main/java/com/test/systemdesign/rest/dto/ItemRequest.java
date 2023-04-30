package com.test.systemdesign.rest.dto;

import com.test.systemdesign.entity.ItemType;
import lombok.Data;

@Data
public class ItemRequest {
	private String name;
	private String permissionGroupName;
	private ItemType parentType;
	private String parentName;
}
