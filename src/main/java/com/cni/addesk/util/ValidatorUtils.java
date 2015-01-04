package com.cni.addesk.util;

import java.util.Collection;

import com.cni.addesk.bean.ContactInformation;
import com.vaadin.data.Validator;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.ui.Field;

public class ValidatorUtils {
	 
    private ValidatorUtils() {}
     
    public static void installSingleValidator(Field<?> field, String attribute) {
         
        Collection<Validator> validators = field.getValidators();
 
        if (validators == null || validators.isEmpty()) {
 
            field.addValidator(new BeanValidator(ContactInformation.class, attribute));
        }
    }
}
