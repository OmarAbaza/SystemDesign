package com.test.systemdesign.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.test.systemdesign.entity.Item;
import com.test.systemdesign.entity.ItemType;
import com.test.systemdesign.entity.item.File;
import com.test.systemdesign.entity.item.Folder;
import com.test.systemdesign.entity.item.Space;

public interface ItemRepository extends JpaRepository<Item, Long> {
	public Space findSpaceByName(String name);
	public Folder findFolderByName(String name);
	public File findFileByName(String name);
	public Item findItemByName(String name);
	public Item findItemByNameAndType(String name, ItemType type);
}
