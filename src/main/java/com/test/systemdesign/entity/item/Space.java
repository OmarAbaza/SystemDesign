package com.test.systemdesign.entity.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import com.test.systemdesign.entity.Item;

import lombok.Data;

@Entity
@DiscriminatorValue("Space")
@Data
public class Space extends Item {

}
