package com.test.systemdesign.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Data
public class Permission {
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	@Column(name = "user_email")
	private String userEmail;
	@Enumerated(EnumType.STRING)
	@Column(name = "permission_level")
	private PermissionLevel permissionLevel;
	@OneToOne
	@JoinColumn(name = "group_id", referencedColumnName = "id")
	private PermissionGroup permissionGroup;
}
