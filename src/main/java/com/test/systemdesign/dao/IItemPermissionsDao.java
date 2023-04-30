package com.test.systemdesign.dao;

import java.util.List;

import com.test.systemdesign.entity.ItemType;
import com.test.systemdesign.entity.PermissionLevel;

public interface IItemPermissionsDao {
	public List<PermissionLevel> getPermissionLevelsForUserOnItem(String itemName, ItemType itemType, String userId);
}
