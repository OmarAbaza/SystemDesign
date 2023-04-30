package com.test.systemdesign.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.test.systemdesign.entity.Item;

import lombok.Data;

@Entity
@DiscriminatorValue("Folder")
@Data
public class Folder extends Item{
	@OneToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Item parent;
}
