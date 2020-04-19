package com.touchspring.smartforecasting.utils;

import java.lang.reflect.Field;
import java.util.*;


/**
 * 对象工具
 */
public class ObjectUtils {

    /**
     * 获取对象之间的不同值
     *
     * @param prevObject  .
     * @param afterObject .
     * @param keys
     * @return .
     * @throws IllegalArgumentException .
     * @throws IllegalAccessException   .
     */
    public static Map<String, ObjectDifferent> getDifferent(Object prevObject, Object afterObject, String[] keys)
            throws IllegalArgumentException, IllegalAccessException {
        List<String> keyList = Arrays.asList(keys);
        HashSet<String> keySet = new HashSet<>(keyList);
        Map<String, ObjectDifferent> map = new HashMap<>();
        Field[] prevFields = prevObject.getClass().getDeclaredFields();
        Field[] afterFields = afterObject.getClass().getDeclaredFields();
        for (int i = 0; i < prevFields.length; i++) {
            if (keySet.contains(prevFields[i].getName())) {
                ObjectDifferent objectDifferent = new ObjectDifferent();
                prevFields[i].setAccessible(true);
                afterFields[i].setAccessible(true);
                Object prevFieldValue = prevFields[i].get(prevObject);
                Object afterFieldValue = afterFields[i].get(afterObject);
                if (prevFieldValue != null && afterFieldValue != null) {
                    if (!prevFieldValue.equals(afterFieldValue)) {
                        objectDifferent.setCurrentValue(afterFieldValue.toString());
                        objectDifferent.setOldValue(prevFieldValue.toString());
                        map.put(prevFields[i].getName(), objectDifferent);
                    }
                } else if (prevFieldValue == null && afterFieldValue != null) {
                    objectDifferent.setCurrentValue(afterFieldValue.toString());
                    objectDifferent.setOldValue(null);
                    map.put(prevFields[i].getName(), objectDifferent);

                } else if (prevFieldValue != null) {
                    objectDifferent.setCurrentValue(null);
                    objectDifferent.setOldValue(prevFieldValue.toString());
                    map.put(prevFields[i].getName(), objectDifferent);
                }
            }
        }
        return map;

    }
    /**
     * 删除对象
     *
     * @param prevObject  .
     * @param keys
     * @return .
     * @throws IllegalArgumentException .
     * @throws IllegalAccessException   .
     */
    public static Map<String, ObjectDifferent> getRemoveValues(Object prevObject, String[] keys)
            throws IllegalArgumentException, IllegalAccessException {
        List<String> keyList = Arrays.asList(keys);
        HashSet<String> keySet = new HashSet<>(keyList);
        Map<String, ObjectDifferent> map = new HashMap<>();
        Field[] prevFields = prevObject.getClass().getDeclaredFields();
        for (int i = 0; i < prevFields.length; i++) {
            if (keySet.contains(prevFields[i].getName())) {
                ObjectDifferent objectDifferent = new ObjectDifferent();
                prevFields[i].setAccessible(true);
                Object prevFieldValue = prevFields[i].get(prevObject);
                if (prevFieldValue != null) {
                    objectDifferent.setCurrentValue(null);
                    objectDifferent.setOldValue(prevFieldValue.toString());
                    map.put(prevFields[i].getName(), objectDifferent);
                }
            }
        }
        return map;

    }

    /**
     * 新增对象
     *
     * @param afterObject .
     * @param keys
     * @return .
     * @throws IllegalArgumentException .
     * @throws IllegalAccessException   .
     */
    public static Map<String, ObjectDifferent> getValues(Object afterObject, String[] keys)
            throws IllegalArgumentException, IllegalAccessException {
        List<String> keyList = Arrays.asList(keys);
        HashSet<String> keySet = new HashSet<>(keyList);
        Map<String, ObjectDifferent> map = new HashMap<>();
        Field[] afterFields = afterObject.getClass().getDeclaredFields();
        for (int i = 0; i < afterFields.length; i++) {
            if (keySet.contains(afterFields[i].getName())) {
                ObjectDifferent objectDifferent = new ObjectDifferent();
                afterFields[i].setAccessible(true);
                Object prevFieldValue = afterFields[i].get(afterObject);
                if (prevFieldValue != null) {
                    objectDifferent.setOldValue(null);
                    objectDifferent.setCurrentValue(prevFieldValue.toString());
                    map.put(afterFields[i].getName(), objectDifferent);
                }
            }
        }
        return map;

    }

}
