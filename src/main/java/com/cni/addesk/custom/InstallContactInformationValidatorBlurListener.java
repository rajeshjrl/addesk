package com.cni.addesk.custom;

import com.cni.addesk.util.ValidatorUtils;
import com.vaadin.event.FieldEvents.BlurEvent;
import com.vaadin.event.FieldEvents.BlurListener;
import com.vaadin.ui.Field;

public class InstallContactInformationValidatorBlurListener implements BlurListener {
	 
    private Field<?> field;
    private String attribute;
 
    public InstallContactInformationValidatorBlurListener(Field<?> field, String attribute) {
 
        this.field = field;
        this.attribute = attribute;
    }
 
    @Override
    public void blur(BlurEvent event) {
 
        ValidatorUtils.installSingleValidator(field, attribute);
    }
}
