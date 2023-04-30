package com.test.systemdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class PermissionGroup {

	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	@Column(name = "group_name", unique = true)
	private String name;
}
