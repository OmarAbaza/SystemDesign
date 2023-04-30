package com.test.systemdesign.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.systemdesign.dao.IItemPermissionsDao;
import com.test.systemdesign.entity.ItemType;
import com.test.systemdesign.entity.PermissionGroup;
import com.test.systemdesign.entity.PermissionLevel;
import com.test.systemdesign.repository.PermissionGroupRepository;
import com.test.systemdesign.repository.PermissionRepository;
import com.test.systemdesign.service.exception.PermissionGroupNotFoundException;

@Service
public class PermissionsService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Autowired
	private PermissionGroupRepository permissionGroupRepository;

	@Autowired
	private IItemPermissionsDao itemPermissionsDao;

	public PermissionGroup getPermissionGroupByName(String permissionGroupName)
			throws PermissionGroupNotFoundException {
		PermissionGroup permissionGroup = permissionGroupRepository.findByName(permissionGroupName);
		if (permissionGroup == null) {
			throw new PermissionGroupNotFoundException();
		}
		return permissionGroup;
	}

	public boolean permissionLevelGrantedForUserOnItem(String itemName, ItemType itemType, String userId,
			PermissionLevel permissionLevel) {
		List<PermissionLevel> grantedPermissionLevels = itemPermissionsDao.getPermissionLevelsForUserOnItem(itemName,
				itemType, userId);
		if (permissionLevel != null && !grantedPermissionLevels.isEmpty()
				&& grantedPermissionLevels.contains(permissionLevel)) {
			return true;
		} else {
			return false;
		}
	}
}
