package com.test.systemdesign.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import com.test.systemdesign.entity.ItemType;
import com.test.systemdesign.entity.PermissionLevel;

@Repository
public class ItemPermissionsDao implements IItemPermissionsDao {
	@PersistenceContext
	private EntityManager em;

	@Override
	public List<PermissionLevel> getPermissionLevelsForUserOnItem(String itemName, ItemType itemType, String userEmail) {
		List<PermissionLevel> permissionLevels = em
				.createQuery("SELECT p.permissionLevel from Permission p where p.userEmail = :userEmail and p.permissionGroup in (select i.permissionGroup from Item i where i.type = :itemType and i.name = :itemName)")
				.setParameter("userEmail", userEmail)
				.setParameter("itemType", itemType)
				.setParameter("itemName", itemName)
				.getResultList();
		return permissionLevels;
	}

}
