package com.test.systemdesign.entity.item;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import org.springframework.context.annotation.Lazy;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.test.systemdesign.entity.Item;

import lombok.Data;

@Entity(name = "Item-File")
@DiscriminatorValue("File")
@Data
public class File extends Item {
	@OneToOne
	@JoinColumn(name = "parent_id", referencedColumnName = "id")
	private Item parent;

	@Lazy
	@OneToOne(mappedBy = "file",cascade = CascadeType.ALL)
	@JoinColumn(name = "id", referencedColumnName = "item_id")
	@JsonManagedReference
	private com.test.systemdesign.entity.File file;
}
