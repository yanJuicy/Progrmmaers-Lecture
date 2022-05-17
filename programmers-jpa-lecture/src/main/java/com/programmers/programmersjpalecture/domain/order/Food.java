package com.programmers.programmersjpalecture.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FOOD")
public class Food extends Item {
    private String chef;

}
