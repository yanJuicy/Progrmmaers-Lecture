package com.programmers.programmersjpalecture.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("CAR")
public class Car extends Item {
    private long power;
}
