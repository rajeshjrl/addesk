package com.cni.addesk.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;


public class MainView extends CustomComponent implements View{
	
	public static final String NAME = "";
	private final AbstractOrderedLayout rootVerticalLayout;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if("Sign Out".equals(selectedItem.getText())){
	    		
	    		// "Logout" the user
	            getSession().setAttribute("user", null);

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);
	    		
	    	}
	    }  
	};
	
	public MainView(){
		setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout("header");
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem("", new ThemeResource("layouts/images/menu_mobile.png"), null);
		menuItem.addItem("Home", null, menuCommand);
		menuItem.addItem("Reports", null, menuCommand);
		menuItem.addItem("History", null, menuCommand);
		menuItem.addItem("Help & Information", null, menuCommand);
		menuItem.addItem("Sign Out", null, menuCommand);
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem("Home", null, menuCommand);        
		MenuItem helpMenuItem = rightMenu.addItem("Help & Information", null, menuCommand);
		MenuItem myaccountMenuItem = rightMenu.addItem("My Account", null, null);
		myaccountMenuItem.addItem("Change Password", null, menuCommand);
		
		headerTemplate.addComponent(menubar, "menubar");
		headerTemplate.addComponent(rightMenu, "rightMenu");
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout portalHomeHorizontalLayout = new HorizontalLayout();
		portalHomeHorizontalLayout.setSpacing(true);
		portalHomeHorizontalLayout.setWidth("100%");
		portalHomeHorizontalLayout.setHeight("15%");
		portalHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label portalHomeLabel = new Label("<b><font size=\"7\">Portal Home</font></b>", ContentMode.HTML);
		portalHomeHorizontalLayout.addComponent(portalHomeLabel);
		portalHomeHorizontalLayout.setComponentAlignment(portalHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label("<b>WELCOME</b>", ContentMode.HTML);
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label("Tim Bucktoo");
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource("layouts/images/company_logo.jpg"));
        companyLogoImage.setHeight("50%");
        companyLogoImage.setWidth("50%");
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		portalHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		portalHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(portalHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label("<hr/>", ContentMode.HTML);
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
