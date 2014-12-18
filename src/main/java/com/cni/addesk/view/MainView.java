package com.cni.addesk.view;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.BaseTheme;


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
	    //rootVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
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
		
		Label portalHomeLabel = new Label("<b><font size='7'>Portal Home</font></b>", ContentMode.HTML);
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
		
		AbstractOrderedLayout contactRootHorizontalLayout = new HorizontalLayout();
		contactRootHorizontalLayout.setSpacing(true);
		contactRootHorizontalLayout.setWidth("100%");
		contactRootHorizontalLayout.setHeight("15%");
		contactRootHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Embedded uploadImage = new Embedded(null, new ThemeResource("layouts/images/upload.png"));
		contactRootHorizontalLayout.addComponent(uploadImage);
		contactRootHorizontalLayout.setComponentAlignment(uploadImage, Alignment.MIDDLE_LEFT);
		
		rootVerticalLayout.addComponent(contactRootHorizontalLayout);		
		
		Label  contactLabel  = new Label("<b>Questions?</b>", ContentMode.HTML);
		
		Link contactLink = new Link("Contact Mike Nichols", null);
		
		AbstractOrderedLayout contactHorizontalLayout = new HorizontalLayout(contactLabel, contactLink);
		contactHorizontalLayout.setSpacing(true);
		contactHorizontalLayout.setSizeUndefined();
		
		contactRootHorizontalLayout.addComponent(contactHorizontalLayout);
		contactRootHorizontalLayout.setComponentAlignment(contactHorizontalLayout, Alignment.TOP_RIGHT);	
		
		Label  contactLineLabel  = new Label("<hr/>", ContentMode.HTML);
		
		rootVerticalLayout.addComponent(contactLineLabel);
		
		Label portalActivityLabel = new Label("<b><font size='5'>Your Portal Activity</font></b>", ContentMode.HTML);
			
		AbstractOrderedLayout activityVerticalLayout = new VerticalLayout();
		activityVerticalLayout.setSizeFull();
		activityVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		activityVerticalLayout.addComponent(portalActivityLabel);
		
		rootVerticalLayout.addComponent(activityVerticalLayout);
		
        final ComboBox iWantToCombobox = new ComboBox("I Want To");
        iWantToCombobox.setInvalidAllowed(false);
        iWantToCombobox.setInputPrompt("I want to");
        iWantToCombobox.addItem("View Transaction Details");
        iWantToCombobox.addItem("View Creative");
        
        Label detailsLabel = new Label("<b>Pick-Up with changes</b><br>Confirmation 11223344", ContentMode.HTML);
        Label orderLabel = new Label("<b>Star Ledger</b><br>Half Page, Black and White", ContentMode.HTML);
		
		final Table activityTable = new Table();
		activityTable.setHeight("350px");
		activityTable.addContainerProperty("Date", Date.class, null);
		activityTable.addContainerProperty("Details", Label.class, null);
		activityTable.addContainerProperty("Order", Label.class, null);
		activityTable.addContainerProperty("Action", ComboBox.class, null);
        
        List<Object> iWantToList = new CopyOnWriteArrayList<Object>();
        iWantToList.add(new Date());
        iWantToList.add(detailsLabel);
        iWantToList.add(orderLabel);
        iWantToList.add(iWantToCombobox);
		
		Object[] iWantToArray = iWantToList.toArray(new Object[iWantToList.size()]);
		activityTable.addItem(iWantToArray, null);
		
		activityVerticalLayout.addComponent(activityTable);

		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
