package com.test.systemdesign.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.systemdesign.entity.Item;
import com.test.systemdesign.entity.ItemType;
import com.test.systemdesign.entity.PermissionGroup;
import com.test.systemdesign.entity.item.File;
import com.test.systemdesign.entity.item.Folder;
import com.test.systemdesign.entity.item.Space;
import com.test.systemdesign.repository.ItemRepository;
import com.test.systemdesign.service.exception.ItemNotFoundException;
import com.test.systemdesign.service.exception.PermissionGroupNotFoundException;

@Service
public class ItemsService {

	@Autowired
	private ItemRepository itemRepository;

	@Autowired
	private PermissionsService permissionsService;

	public Space createSpace(String name, String permissionGroupName) throws PermissionGroupNotFoundException {
		PermissionGroup permissionGroup = permissionsService.getPermissionGroupByName(permissionGroupName);
		Space newSpace = new Space();
		newSpace.setName(name);
		newSpace.setPermissionGroup(permissionGroup);
		return itemRepository.save(newSpace);
	}

	public Folder createFolder(String name, String permissionGroupName, String parentName, ItemType parentType) throws PermissionGroupNotFoundException, ItemNotFoundException {
		PermissionGroup permissionGroup = permissionsService.getPermissionGroupByName(permissionGroupName);
		Item parentItem = getItemByNameAndType(parentName, parentType);
		Folder newFolder = new Folder();
		newFolder.setName(name);
		newFolder.setPermissionGroup(permissionGroup);
		newFolder.setParent(parentItem);
		return itemRepository.save(newFolder);
	}

	public File createFile(String name, String permissionGroupName, String parentName, ItemType parentType, byte[] binaryData) throws PermissionGroupNotFoundException, ItemNotFoundException {
		PermissionGroup permissionGroup = permissionsService.getPermissionGroupByName(permissionGroupName);
		Item parentItem = getItemByNameAndType(parentName, parentType);
		File newFile = new File();
		newFile.setName(name);
		newFile.setPermissionGroup(permissionGroup);
		newFile.setParent(parentItem);
		com.test.systemdesign.entity.File fileData = new com.test.systemdesign.entity.File();
		fileData.setBinaryData(binaryData);
		fileData.setFile(newFile);
		newFile.setFile(fileData);
		return itemRepository.save(newFile);
	}

	public Space getSpaceByName(String name) throws ItemNotFoundException {
		Space space = itemRepository.findSpaceByName(name);
		if(space == null) {
			throw new ItemNotFoundException();
		}
		return space;
	}

	public Folder getFolderByName(String name) throws ItemNotFoundException {
		Folder folder =  itemRepository.findFolderByName(name);
		if(folder == null) {
			throw new ItemNotFoundException();
		}
		return folder;
	}

	public File getFileByName(String name) throws ItemNotFoundException {
		File file = itemRepository.findFileByName(name);
		if(file == null) {
			throw new ItemNotFoundException();
		}
		return file;
	}

	public Item getItemByNameAndType(String name, ItemType type) throws ItemNotFoundException {
		Item item = itemRepository.findItemByNameAndType(name, type);
		if(item == null) {
			throw new ItemNotFoundException();
		}
		return item;
	}
}
