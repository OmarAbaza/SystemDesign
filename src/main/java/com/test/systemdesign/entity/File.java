package com.test.systemdesign.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class File {
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	@NotNull
	@OneToOne
	@JoinColumn(name = "item_id", referencedColumnName = "id")
	@JsonBackReference
	private com.test.systemdesign.entity.item.File file;
	private byte[] binaryData;
	
}
