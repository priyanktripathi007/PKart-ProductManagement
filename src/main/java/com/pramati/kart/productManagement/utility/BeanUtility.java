package com.pramati.kart.productManagement.utility;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class BeanUtility {
    public String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        List<String> attributes = new ArrayList<>(Arrays.asList("createdTime", "lastModifiedTime", "createdBy"));
        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        emptyNames.addAll(attributes);
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
