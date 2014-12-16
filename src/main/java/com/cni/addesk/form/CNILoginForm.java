package com.cni.addesk.form;

import org.vaadin.activelink.ActiveLink;

import com.cni.addesk.custom.ClickableLabel;
import com.cni.addesk.view.MainView;
import com.ejt.vaadin.loginform.LoginForm;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class CNILoginForm extends LoginForm {	

    private Button cancelButton;
	
	/*@Override
    protected Component createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {
        HorizontalLayout layout = new HorizontalLayout();
        layout.setSpacing(true);
        layout.setMargin(true);

        layout.addComponent(userNameField);
        layout.addComponent(passwordField);
        layout.addComponent(loginButton);
        layout.setComponentAlignment(loginButton, Alignment.BOTTOM_LEFT);
        return layout;
    }*/
	
	public Component createContent(TextField userNameField, PasswordField passwordField, Button loginButton) {

        
        Label signInLabel = new Label("<b><font size='5'>Sign In</font></b>", ContentMode.HTML);        
        cancelButton = new Button("Cancel");
		// Add both to a panel
        VerticalLayout fields = new VerticalLayout(signInLabel, userNameField, passwordField);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();
        
        AbstractOrderedLayout rememberMeHorizontalLayout = new HorizontalLayout();
        rememberMeHorizontalLayout.setSizeFull();
        CheckBox rememberMeCheckBox = new CheckBox("Remember Me");
        rememberMeHorizontalLayout.addComponent(rememberMeCheckBox);
        ActiveLink forgotPasswordLink = new ActiveLink("Forgot your password?", null);
        rememberMeHorizontalLayout.addComponent(forgotPasswordLink);
        rememberMeHorizontalLayout.setComponentAlignment(forgotPasswordLink, Alignment.TOP_LEFT);
        fields.addComponent(rememberMeHorizontalLayout);
        fields.setComponentAlignment(rememberMeHorizontalLayout, Alignment.TOP_RIGHT);
        
        AbstractOrderedLayout submitHorizontalLayout = new HorizontalLayout();
        submitHorizontalLayout.setSizeFull();
        submitHorizontalLayout.setMargin(new MarginInfo(true, false, true, false));
        submitHorizontalLayout.addComponent(loginButton);
        submitHorizontalLayout.addComponent(cancelButton);
        
        fields.addComponent(submitHorizontalLayout);
        
        ClickableLabel infrequentUsersLabel = new ClickableLabel("<a href='#'><b><font size='4'>OR, submit creative without registering...</font></b><br/>For infrequent users</a>");
        
        infrequentUsersLabel.addLayoutClickListener(new LayoutClickListener() {            
            @Override
             public void layoutClick(LayoutClickEvent event) {
                      
                      //Notification.show("Hi!");        
              }
        });   
        
        fields.addComponent(infrequentUsersLabel);
        
        return fields;
    }

    @Override
    public void login(String userName, String password) {
    	
    }

}
