package com.cni.addesk.view;


import com.cni.addesk.form.CNILoginForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
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
	private final TextField user;
    private final PasswordField password;
    private final Button loginButton;
	private final AbstractOrderedLayout rootVerticalLayout;
	private final CNILoginForm cniLoginForm;
	
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
	    setSizeFull();
	    cniLoginForm = new CNILoginForm();
	    rootVerticalLayout = new VerticalLayout();
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout("home_header");
		MenuBar menubar = new MenuBar();
		menubar.setEnabled(false);
		MenuItem menuItem = menubar.addItem("", new ThemeResource("layouts/images/menu_mobile.png"), null);
		menuItem.addItem("Home", null, menuCommand);
		menuItem.addItem("Reports", null, menuCommand);
		menuItem.addItem("History", null, menuCommand);
		menuItem.addItem("Help & Information", null, menuCommand);
		menuItem.addItem("Sign Out", null, menuCommand);
				
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem("Home", null, menuCommand);
		homeMenuItem.setEnabled(false);
		MenuItem helpMenuItem = rightMenu.addItem("Help & Information", null, menuCommand);
		MenuItem registerItem = rightMenu.addItem("Register", null, menuCommand);
		
		headerTemplate.addComponent(menubar, "menubar");
		headerTemplate.addComponent(rightMenu, "rightMenu");
		
		rootVerticalLayout.addComponent(headerTemplate);
		
		AbstractOrderedLayout homeHorizontalLayout = new HorizontalLayout();
		homeHorizontalLayout.setSpacing(true);
		homeHorizontalLayout.setSizeUndefined();
		homeHorizontalLayout.setWidth("75%");
		//homeHorizontalLayout.setHeight("20%");
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
        user = new TextField("Login Name");
        user.setWidth("350px");
        //user.setInputPrompt("Your Login Name");

        // Create the password input field
        password = new PasswordField("Password");
        password.setWidth("350px");

        // Create login button
        loginButton = new Button("Login", this);
        
        Component fieldsVerticalLayout = cniLoginForm.createContent(user, password, loginButton);
        
        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fieldsVerticalLayout);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fieldsVerticalLayout, Alignment.MIDDLE_CENTER);
        
        rootVerticalLayout.addComponent(viewLayout);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
    public void buttonClick(ClickEvent event) {
		
        String username = user.getValue();
        String password = this.password.getValue();        
        //
        // Validate username and password with database here. For examples sake
        // I use a dummy username and password.
        //
        boolean isValid = username.equals("test@test.com") && password.equals("passw0rd");

        if (isValid) {

            // Store the current user in the service session
            getSession().setAttribute("user", username);

            // Navigate to main view
            getUI().getNavigator().navigateTo(MainView.NAME);

        } else {

            // Wrong password clear the password field and refocuses it
            this.password.setValue(null);
            this.password.focus();

        }
    }

}
