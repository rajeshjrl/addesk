package com.cni.addesk.view;


import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.vaadin.activelink.ActiveLink;

import com.cni.addesk.custom.ClickableLabel;
import com.cni.addesk.form.CNILoginForm;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

public class HomeView extends CustomComponent implements View, Button.ClickListener {
	
	public static final String NAME = "home";
	private final TextField username;
    private final PasswordField password;
    private final CheckBox rememberMeCheckBox;
    private final Button loginButton;
	private final Button cancelButton;
	private final AbstractOrderedLayout rootVerticalLayout;
	private final Subject currentUser = SecurityUtils.getSubject();
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	/*if("Login".equals(selectedItem.getText())){	    		
	    		AbstractOrderedLayout loginVerticalLayout = new VerticalLayout();
	    		loginVerticalLayout.setSpacing(true);
	    		loginVerticalLayout.setWidth("250px");
	    		loginVerticalLayout.setHeight("250px");
	    		loginVerticalLayout.addComponent(new LoginForm());
	    		if (rootVerticalLayout.getComponentCount() > 1) {
	    			rootVerticalLayout.replaceComponent(rootVerticalLayout.getComponent(1), loginVerticalLayout);
    	        } else {
    	        	rootVerticalLayout.addComponent(loginVerticalLayout);
    	        }	    		
	    		rootVerticalLayout.setComponentAlignment(loginVerticalLayout, Alignment.TOP_RIGHT);
	    	}*/
	    }  
	};
	
	public HomeView() {
		if(currentUser.isAuthenticated()){
			currentUser.logout();
		}
	    setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout("home_header");
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem("", new ThemeResource("layouts/images/menu_mobile.png"), null);
		MenuItem homeMenuItem = menuItem.addItem("Home", null, menuCommand);
		homeMenuItem.setEnabled(false);
		MenuItem reportsMenuItem = menuItem.addItem("Reports", null, menuCommand);
		reportsMenuItem.setEnabled(false);
		MenuItem historyMenuItem = menuItem.addItem("History", null, menuCommand);
		historyMenuItem.setEnabled(false);
		MenuItem helpInfoMenuItem = menuItem.addItem("Help & Information", null, menuCommand);
		helpInfoMenuItem.setEnabled(false);
		MenuItem signOutMenuItem = menuItem.addItem("Sign Out", null, menuCommand);
		signOutMenuItem.setEnabled(false);
				
		MenuBar rightMenu = new MenuBar();
		MenuItem helpMenuItem = rightMenu.addItem("Help & Information", null, menuCommand);
		MenuItem registerItem = rightMenu.addItem("Register", null, menuCommand);
		
		headerTemplate.addComponent(menubar, "menubar");
		headerTemplate.addComponent(rightMenu, "rightMenu");
		
		rootVerticalLayout.addComponent(headerTemplate);
		
		AbstractOrderedLayout homeHorizontalLayout = new HorizontalLayout();
		homeHorizontalLayout.setSpacing(true);
		homeHorizontalLayout.setSizeUndefined();
		homeHorizontalLayout.setWidth("75%");
		homeHorizontalLayout.setMargin(true);
		
		Embedded companyLogoImage = new Embedded(null, new ThemeResource("layouts/images/company-logo_original.jpg"));
        homeHorizontalLayout.addComponent(companyLogoImage);
        homeHorizontalLayout.setComponentAlignment(companyLogoImage, Alignment.MIDDLE_LEFT);
        
        Label portalHomeLabel = new Label("<b><font size='7'>Advertiser Portal</font></b>", ContentMode.HTML);
        homeHorizontalLayout.addComponent(portalHomeLabel);
        homeHorizontalLayout.setComponentAlignment(portalHomeLabel, Alignment.MIDDLE_LEFT);
        
        rootVerticalLayout.addComponent(homeHorizontalLayout);
        
        Label  homeTitleLineLabel  = new Label("<hr/>", ContentMode.HTML);
        
        rootVerticalLayout.addComponent(homeTitleLineLabel);
        
        // Create the user input field
        username = new TextField("Login Name");
        username.setWidth("350px");
        //user.setInputPrompt("Your Login Name");

        // Create the password input field
        password = new PasswordField("Password");
        password.setWidth("350px");

        // Create login button
        loginButton = new Button("Login", this);
        
        Label signInLabel = new Label("<b><font size='5'>Sign In</font></b>", ContentMode.HTML);        
        cancelButton = new Button("Cancel");
		// Add both to a panel
        VerticalLayout fields = new VerticalLayout(signInLabel, username, password);
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();
        
        AbstractOrderedLayout rememberMeHorizontalLayout = new HorizontalLayout();
        rememberMeHorizontalLayout.setSizeFull();
        rememberMeCheckBox = new CheckBox("Remember Me");
        rememberMeCheckBox.setImmediate(true);
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
        
        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        
        rootVerticalLayout.addComponent(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void buttonClick(ClickEvent event) {
		UsernamePasswordToken token = new UsernamePasswordToken(username.getValue(), password.getValue());        

		try {
			if(rememberMeCheckBox.getValue()){
				token.setRememberMe(true);
			}
			currentUser.login(token);
            // Store the current user in the service session
            getSession().setAttribute("user", username.getValue());
            // Navigate to main view
            getUI().getNavigator().navigateTo(MainView.NAME);

		} catch (Exception e) {
			Logger.getAnonymousLogger().log(Level.INFO, e.getMessage());
			username.setValue("");
			password.setValue("");
		}
    }

}
