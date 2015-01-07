package com.cni.addesk.view;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.cni.addesk.util.Messages;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
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
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.themes.BaseTheme;


public class UploadView extends CustomComponent implements View{
	
	public static final String NAME = "upload";
	private final AbstractOrderedLayout rootVerticalLayout;
	private View oldView;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if(7 == selectedItem.getId()){
	    		
	    		// "Logout" the user
	            getSession().setAttribute("user", null);

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);  
	    		
	    	}
	    }  
	};
	
	public UploadView(){
		//setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
	    rootVerticalLayout.setSizeFull();
	    //rootVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout("header");
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem("", new ThemeResource("layouts/images/menu.png"), null);
		menuItem.addItem("Home", null, menuCommand);
		menuItem.addItem("Reports", null, menuCommand);
		menuItem.addItem("History", null, menuCommand);
		menuItem.addItem("Help & Information", null, menuCommand);
		menuItem.addItem("Sign Out", null, menuCommand);
		
		Label logoLabel = new Label(Messages.getString("HomeView.logoLabel"), ContentMode.HTML);
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem("Home : Upload", null, menuCommand);        
		MenuItem helpMenuItem = rightMenu.addItem("Help & Information", null, menuCommand);
		MenuItem myaccountMenuItem = rightMenu.addItem("My Account", null, null);
		myaccountMenuItem.addItem("Change Password", null, menuCommand);
		
		headerTemplate.addComponent(menubar, "menubar");
		headerTemplate.addComponent(logoLabel, Messages.getString("HomeView.logo"));  
		headerTemplate.addComponent(rightMenu, "rightMenu");
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout uploadHomeHorizontalLayout = new HorizontalLayout();
		uploadHomeHorizontalLayout.setSpacing(true);
		uploadHomeHorizontalLayout.setWidth("100%");
		uploadHomeHorizontalLayout.setHeight("15%");
		uploadHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label uploadHomeLabel = new Label("<span class='home_label'>Upload Creative</span>", ContentMode.HTML);
		uploadHomeHorizontalLayout.addComponent(uploadHomeLabel);
		uploadHomeHorizontalLayout.setComponentAlignment(uploadHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label("<em>WELCOME</em>", ContentMode.HTML);
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label("Tim Bucktoo");
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource("layouts/images/user_company_logo.png"));
        companyLogoImage.setHeight("50%");
        companyLogoImage.setWidth("50%");
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		uploadHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		uploadHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(uploadHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label("<hr/>", ContentMode.HTML);
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		AbstractOrderedLayout creativeInfoHorizontalLayout = new HorizontalLayout();
		creativeInfoHorizontalLayout.setSpacing(true);
		creativeInfoHorizontalLayout.setWidth("100%");
		creativeInfoHorizontalLayout.setHeight("15%");
		creativeInfoHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		rootVerticalLayout.addComponent(creativeInfoHorizontalLayout);		
		
		Label  creativeInfoLabel  = new Label("<em>The New Orlenas Official Visitors Gude, Winter</em><br/>Appears: July 1, 2014<br/>Description: Come Splash With Us<br/>Half Page Vertical, Full Color", ContentMode.HTML);
		
		creativeInfoHorizontalLayout.addComponent(creativeInfoLabel);
		
		Label  contactLineLabel  = new Label("<hr/>", ContentMode.HTML);
		
		rootVerticalLayout.addComponent(contactLineLabel);
		
		Label adCreativeLabel = new Label("<span class='creative_information'>Ad Creative Information</span><br/>Tell us about the file(s) you are sending. This helps us process<br/>know what you are looking for.", ContentMode.HTML);
			
		AbstractOrderedLayout adCreativeVerticalLayout = new VerticalLayout();
		adCreativeVerticalLayout.setSizeFull();
		adCreativeVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		adCreativeVerticalLayout.addComponent(adCreativeLabel);
		
		rootVerticalLayout.addComponent(adCreativeVerticalLayout);
		
		AbstractOrderedLayout instructionsHorizontalLayout = new HorizontalLayout();
		instructionsHorizontalLayout.setSpacing(true);
		instructionsHorizontalLayout.setMargin(new MarginInfo(true, false, true, false));
		
		AbstractOrderedLayout instructionsVerticalLayout = new VerticalLayout();
		instructionsVerticalLayout.setSpacing(true);
		
		ComboBox typesOfFilesComboBox = new ComboBox("What type of file(s) are you submitting?");
		typesOfFilesComboBox.setWidth("350px");
		typesOfFilesComboBox.setInvalidAllowed(false);
		typesOfFilesComboBox.addItem("An ad which requires some modifications.");
		
		TextField descriptionTextField = new TextField("Description (To help identify the creative.)");
		descriptionTextField.setWidth("350px");
		descriptionTextField.setInputPrompt("Optional");
		
		instructionsVerticalLayout.addComponent(typesOfFilesComboBox);
		instructionsVerticalLayout.addComponent(descriptionTextField);
		
		instructionsHorizontalLayout.addComponent(instructionsVerticalLayout);
		
		TextArea instructionsTextArea = new TextArea("Instructions and Direction");
		instructionsTextArea.setWidth("350px");
		instructionsTextArea.setHeight("115px");
		instructionsTextArea.setInputPrompt("Tell us what needs to be done with the files");
		
		instructionsHorizontalLayout.addComponent(instructionsTextArea);
		
		adCreativeVerticalLayout.addComponent(instructionsHorizontalLayout);
		
		CheckBox readAgreeCheckbox = new CheckBox("I have read and agree to the Deadline Policies and Terms of use");
		
		adCreativeVerticalLayout.addComponent(readAgreeCheckbox);
		
		Button submitButton = new Button("SUBMIT");	 
		submitButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            }
        });
		Button cancelButton = new Button(Messages.getString("InformationView.cancel")); 
		cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	if(oldView instanceof MainView){
    	            getUI().getNavigator().navigateTo(MainView.NAME);
	            }else if(oldView instanceof InformationView){
	            	getUI().getNavigator().navigateTo(InformationView.NAME);
	            }
            }
        });
		
		AbstractOrderedLayout buttonsHorizontalLayout = new HorizontalLayout(submitButton, cancelButton);
		buttonsHorizontalLayout.setSizeUndefined();
		buttonsHorizontalLayout.setSpacing(true);
		buttonsHorizontalLayout.setMargin(new MarginInfo(true, false, true, false));
		
		adCreativeVerticalLayout.addComponent(buttonsHorizontalLayout);
		
        /*final ComboBox iWantToComboBox = new ComboBox("I Want To");
        iWantToComboBox.setInvalidAllowed(false);
        iWantToComboBox.setInputPrompt("I want to");
        iWantToComboBox.addItem("View Transaction Details");
        iWantToComboBox.addItem("View Creative");
        
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
        iWantToList.add(iWantToComboBox);
		
		Object[] iWantToArray = iWantToList.toArray(new Object[iWantToList.size()]);
		activityTable.addItem(iWantToArray, null);
		
		activityVerticalLayout.addComponent(activityTable);*/

		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		oldView = event.getOldView();		
	}
}
