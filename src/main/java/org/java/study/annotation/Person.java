package org.java.study.annotation;

import java.lang.annotation.*;

@Repeatable(Persons.class)
@Target(ElementType.FIELD)
public @interface Person {
    String value();
}
