package com.techexplore.autosuggest.util;

import java.lang.reflect.Field;

/**
 * Created by chandrashekar.v on 9/12/2017.
 */
public final class ClassUtil {

    public static Field[] getAllFields(Class<?> clazz) {

        Field[] fields = clazz.getFields();
        return fields;
    }

    public static Field[] getFieldAnnotationMapper(Class<?> clazz) {

        Field[] fields = clazz.getFields();
       /* Arrays.stream(fields)
                .filter(field -> !Objects.isNull(field.getAnnotations()))
                .collect(Collectors.groupingBy(Field::getName), Collectors.mapping(Field::getAnnotations));*/
        return fields;
    }
}
