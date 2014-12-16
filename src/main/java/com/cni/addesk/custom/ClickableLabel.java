package com.cni.addesk.custom;

import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Label;

import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
public class ClickableLabel extends VerticalLayout{
	
	public ClickableLabel(String value) {

        Label label = new Label (value, ContentMode.HTML);
        addComponent(label);    
    }

}
