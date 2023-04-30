package com.test.systemdesign.entity;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "type", "name" }) })
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
@Data

public class Item {
	@Id
	@GeneratedValue
	@JsonIgnore
	private Long id;
	@Enumerated(EnumType.STRING)
	@Column (name="type", insertable = false, updatable = false)
	private ItemType type;
	@NotNull
	private String name;
	@OneToOne
	@JoinColumn(name = "permission_group_id", referencedColumnName = "id")
	private PermissionGroup permissionGroup;
}
