package com.cni.addesk.view;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vaadin.activelink.ActiveLink;
import org.vaadin.activelink.ActiveLink.LinkActivatedEvent;
import org.vaadin.activelink.ActiveLink.LinkActivatedListener;

import com.cni.addesk.custom.ClickableLabel;
import com.cni.addesk.util.Messages;
import com.cni.addesk.util.ProductsName;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.BaseTheme;


public class MainView extends CustomComponent implements View{
	
	public static final String NAME = Messages.getString("MainView.mainViewName");  
	private final AbstractOrderedLayout rootVerticalLayout;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if(7 == selectedItem.getId()){
	    		
	    		// "Logout" the user
	            getSession().setAttribute(Messages.getString("MainView.sessionUser"), null);  

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);
	    		
	    	}
	    }  
	};
	
	public MainView(){
		//setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
	    rootVerticalLayout.setSizeFull();
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout(Messages.getString("MainView.headerTemplate"));  
		
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem(Messages.getString("MainView.menuItemCaption"), new ThemeResource(Messages.getString("MainView.menuItem")), null);  
		MenuItem homeSubMenuItem = menuItem.addItem(Messages.getString("MainView.homeSubMenuItem"), null, menuCommand);  
		MenuItem reportsMenuItem = menuItem.addItem(Messages.getString("MainView.reportsMenuItem"), null, menuCommand);  
		MenuItem historyMenuItem = menuItem.addItem(Messages.getString("MainView.historyMenuItem"), null, menuCommand);  
		MenuItem helpInfoMenuItem = menuItem.addItem(Messages.getString("MainView.helpInfoMenuItem"), null, menuCommand);  
		MenuItem signOutMenuItem = menuItem.addItem(Messages.getString("MainView.signOutMenuItem"), null, menuCommand);  
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem(Messages.getString("MainView.homeMenuItem"), null, menuCommand);          
		MenuItem helpMenuItem = rightMenu.addItem(Messages.getString("MainView.helpMenuItem"), null, menuCommand);  
		MenuItem myaccountMenuItem = rightMenu.addItem(Messages.getString("MainView.myaccountMenuItem"), null, null);  
		MenuItem changePasswordMenuItem = myaccountMenuItem.addItem(Messages.getString("MainView.changePasswordMenuItem"), null, menuCommand);  
		
		headerTemplate.addComponent(menubar, Messages.getString("MainView.menubar"));  
		headerTemplate.addComponent(rightMenu, Messages.getString("MainView.rightMenu"));  
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout campaignHomeHorizontalLayout = new HorizontalLayout();
		campaignHomeHorizontalLayout.setSpacing(true);
		campaignHomeHorizontalLayout.setWidth(Messages.getString("MainView.campaignHomeHorizontalLayoutWidth"));  
		campaignHomeHorizontalLayout.setHeight(Messages.getString("MainView.campaignHomeHorizontalLayoutHeight"));  
		campaignHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label campaignHomeLabel = new Label(Messages.getString("MainView.campaignHomeLabel"), ContentMode.HTML);  
		campaignHomeHorizontalLayout.addComponent(campaignHomeLabel);
		campaignHomeHorizontalLayout.setComponentAlignment(campaignHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label(Messages.getString("MainView.welcomeLabel"), ContentMode.HTML);  
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label(Messages.getString("MainView.usernameLabel"));  
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource(Messages.getString("MainView.companyLogoImage")));  
        companyLogoImage.setHeight(Messages.getString("MainView.companyLogoImageHeight"));  
        companyLogoImage.setWidth(Messages.getString("MainView.companyLogoImageWidth"));  
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		campaignHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		campaignHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(campaignHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label(Messages.getString("MainView.pageTitleLineLabel"), ContentMode.HTML);  
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		AbstractOrderedLayout contactRootHorizontalLayout = new HorizontalLayout();
		contactRootHorizontalLayout.setSpacing(true);
		contactRootHorizontalLayout.setWidth(Messages.getString("MainView.contactRootHorizontalLayoutWidth"));  
		contactRootHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		rootVerticalLayout.addComponent(contactRootHorizontalLayout);		
		
		Label  contactLabel  = new Label(Messages.getString("MainView.contactLabel"), ContentMode.HTML);  
		
		ActiveLink contactLink = new ActiveLink(Messages.getString("MainView.contactLinkText"), new ExternalResource(Messages.getString("MainView.contactLink")));  
		contactLink.addListener(new LinkActivatedListener() {           
            public void linkActivated(LinkActivatedEvent event) {                
                        //event.isLinkOpened()
            }
        });
		
		AbstractOrderedLayout rootPortalSelectionsHorizontalLayout = new HorizontalLayout();
		rootPortalSelectionsHorizontalLayout.setSpacing(true);
		rootPortalSelectionsHorizontalLayout.setWidth(Messages.getString("MainView.rootPortalSelectionsHorizontalLayoutWidth")); 
		rootPortalSelectionsHorizontalLayout.setHeight(Messages.getString("MainView.rootPortalSelectionsHorizontalLayoutHeight")); 
		
		AbstractOrderedLayout contactHorizontalLayout = new HorizontalLayout(contactLabel, contactLink);
		contactHorizontalLayout.setSpacing(true);
		contactHorizontalLayout.setSizeUndefined();
		
		AbstractOrderedLayout portalSelectionsHorizontalLayout = new HorizontalLayout();
		portalSelectionsHorizontalLayout.setSpacing(true);
		portalSelectionsHorizontalLayout.setSizeUndefined();
		
		rootPortalSelectionsHorizontalLayout.addComponent(portalSelectionsHorizontalLayout);
		rootPortalSelectionsHorizontalLayout.addComponent(contactHorizontalLayout);
		rootPortalSelectionsHorizontalLayout.setComponentAlignment(contactHorizontalLayout, Alignment.TOP_RIGHT);		
		
		contactRootHorizontalLayout.addComponent(rootPortalSelectionsHorizontalLayout);
		
		ComboBox viewComboBox = new ComboBox();
		viewComboBox.setNullSelectionAllowed(false);
		viewComboBox.setCaption(Messages.getString("MainView.viewComboBoxCaption")); 
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox1")); 
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox2")); 
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox3")); 
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox4")); 
		
		ComboBox sortComboBox = new ComboBox();
		sortComboBox.setNullSelectionAllowed(false);
		sortComboBox.setCaption(Messages.getString("MainView.sortComboBoxCaption")); 
		sortComboBox.addItem(Messages.getString("MainView.sortComboBox1")); 
		sortComboBox.addItem(Messages.getString("MainView.sortComboBox2")); 
		
		Button filterButton = new Button(Messages.getString("MainView.filterButtonText")); 
		
		final Label filtersAppliedLabel = new Label(Messages.getString("MainView.filtersAppliedLabel"), ContentMode.HTML); 
		filtersAppliedLabel.setVisible(false);
		
		filterButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	
            	// Create a sub-window and add it to the main window
            	final Window subWindow = new Window();
            	subWindow.setWidth(Messages.getString("MainView.subWindowWidth")); 
            	subWindow.setHeight(Messages.getString("MainView.subWindowHeight")); 
            	subWindow.setImmediate(true);
            	subWindow.setModal(true);
            	
            	final AbstractOrderedLayout subWindowVerticalLayout = new VerticalLayout();
            	subWindowVerticalLayout.setSizeUndefined();
            	subWindowVerticalLayout.setMargin(true);
            	
            	Label campaignListFiltersLabel = new Label(Messages.getString("MainView.campaignListFiltersLabel"), ContentMode.HTML); 
            	subWindowVerticalLayout.addComponent(campaignListFiltersLabel);
            	
            	final ComboBox advertiserComboBox = new ComboBox();
            	advertiserComboBox.setNullSelectionAllowed(false);
            	advertiserComboBox.setCaption(Messages.getString("MainView.advertiserComboBoxCaption")); 
            	advertiserComboBox.addItem(Messages.getString("MainView.advertiserComboBox1")); 
            	subWindowVerticalLayout.addComponent(advertiserComboBox);
            	
            	Label dateRangeLabel = new Label(Messages.getString("MainView.dateRangeLabel")); 
            	subWindowVerticalLayout.addComponent(dateRangeLabel);
            	
            	final PopupDateField fromDate = new PopupDateField();
            	// Set the prompt
            	fromDate.setValue(new Date());            	        
            	// Set width explicitly to accommodate the prompt
            	fromDate.setWidth(Messages.getString("MainView.fromDateWidth")); 
            	
            	final PopupDateField toDate = new PopupDateField();
            	// Set the prompt
            	toDate.setValue(new Date());            	        
            	// Set width explicitly to accommodate the prompt
            	toDate.setWidth(Messages.getString("MainView.toDateWidth")); 
            	
            	AbstractOrderedLayout dateRangeHorizontalLayout = new HorizontalLayout(fromDate, toDate);
            	dateRangeHorizontalLayout.setSizeFull();
            	dateRangeHorizontalLayout.setSpacing(true);
            	
            	subWindowVerticalLayout.addComponent(dateRangeHorizontalLayout);
            	
            	final ComboBox campaignTypeComboBox = new ComboBox();
            	campaignTypeComboBox.setNullSelectionAllowed(false);
            	campaignTypeComboBox.setCaption(Messages.getString("MainView.campaignTypeComboBoxCaption")); 
            	campaignTypeComboBox.addItem(Messages.getString("MainView.campaignTypeComboBox1")); 
            	campaignTypeComboBox.addItem(Messages.getString("MainView.campaignTypeComboBox2")); 
            	campaignTypeComboBox.addItem(Messages.getString("MainView.campaignTypeComboBox3")); 
            	
            	subWindowVerticalLayout.addComponent(campaignTypeComboBox);
            	
            	// Have a bean container to put the beans in
                final BeanItemContainer<ProductsName> productsContainer =  new BeanItemContainer<ProductsName>(ProductsName.class);
                
                // Put data in it
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer1"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer2"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer3"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer4"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer5"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer6"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer7"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer8"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer9"))); 
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer10"))); 
            	
            	final ListSelect productsSelect = new ListSelect(Messages.getString("MainView.productsSelectCaption"), productsContainer); 
            	//productsSelect.setMultiSelect(true);
            	// Enable null selection
            	productsSelect.setNullSelectionAllowed(false);
            	// Set the caption mode to read the caption directly
                // from the 'name' property of the bean
            	productsSelect.setItemCaptionMode(ListSelect.ItemCaptionMode.PROPERTY );
            	productsSelect.setItemCaptionPropertyId(Messages.getString("MainView.itemCaptionPropertyId")); 
            	
            	productsSelect.addValueChangeListener(new Property.ValueChangeListener() {
    		        public void valueChange(ValueChangeEvent event) {
    		            if (event.getProperty().getValue() != null){
    		                // Some feedback
    		            	ProductsName productsName = (ProductsName)event.getProperty().getValue();
    		            	productsContainer.removeItem(productsName);
    		            	productsContainer.addItemAt(0, new ProductsName(productsName.getName()));
    		            }
    		        }
    		    });
            	
            	subWindowVerticalLayout.addComponent(productsSelect);
            	
            	Button subWindowApplyButton = new Button(Messages.getString("MainView.subWindowApplyButtonText")); 
            	Button subWindowCancelButton = new Button(Messages.getString("MainView.subWindowCancelButtonText")); 
            	Button subWindowClearButton = new Button(Messages.getString("MainView.subWindowClearButtonText")); 
            	
            	subWindowApplyButton.addClickListener(new ClickListener() {
                    public void buttonClick(ClickEvent event) {
                    	filtersAppliedLabel.setVisible(true);
                    	subWindow.close(); // Close the sub-window
                    }
                });
            	
            	subWindowCancelButton.addClickListener(new ClickListener() {
                    public void buttonClick(ClickEvent event) {
                    	subWindow.close(); // Close the sub-window
                    }
                });
            	
            	subWindowClearButton.addClickListener(new ClickListener() {
                    public void buttonClick(ClickEvent event) {
                    	advertiserComboBox.setValue(null);
                    	fromDate.setValue(new Date());
                    	toDate.setValue(new Date());
                    	campaignTypeComboBox.setValue(null);
                    }
                });
            	
            	AbstractOrderedLayout subWindowButtonsHorizontalLayout = new HorizontalLayout(subWindowApplyButton, subWindowCancelButton, subWindowClearButton);
            	subWindowButtonsHorizontalLayout.setSizeFull();
            	subWindowButtonsHorizontalLayout.setSpacing(true);
            	subWindowButtonsHorizontalLayout.setMargin(new MarginInfo(true, false, false, false));
            	
            	subWindowVerticalLayout.addComponent(subWindowButtonsHorizontalLayout);
            	
            	subWindow.setContent(subWindowVerticalLayout);
            	UI.getCurrent().addWindow(subWindow);
            	
            }
            
		});
		
		portalSelectionsHorizontalLayout.addComponent(viewComboBox);
		portalSelectionsHorizontalLayout.addComponent(sortComboBox);
		portalSelectionsHorizontalLayout.addComponent(filterButton);
		portalSelectionsHorizontalLayout.setComponentAlignment(filterButton, Alignment.BOTTOM_LEFT);
    	portalSelectionsHorizontalLayout.addComponent(filtersAppliedLabel);
    	portalSelectionsHorizontalLayout.setComponentAlignment(filtersAppliedLabel, Alignment.BOTTOM_LEFT);
		
		AbstractOrderedLayout uploadCreativeHorizontalLayout = new HorizontalLayout();
		uploadCreativeHorizontalLayout.setSizeUndefined();
		uploadCreativeHorizontalLayout.setMargin(true);
		
		Embedded uploadImage = new Embedded(null, new ThemeResource(Messages.getString("MainView.uploadImage"))); 
		
		ClickableLabel uploadCreativeLabel = new ClickableLabel(Messages.getString("MainView.uploadCreativeLabel")); 
		
		uploadCreativeHorizontalLayout.addComponent(uploadImage);
		uploadCreativeHorizontalLayout.addComponent(uploadCreativeLabel);
		uploadCreativeHorizontalLayout.setComponentAlignment(uploadCreativeLabel, Alignment.MIDDLE_LEFT);		
		
		rootVerticalLayout.addComponent(uploadCreativeHorizontalLayout);
		
		final ComboBox iWantToComboBox = new ComboBox(Messages.getString("MainView.iWantToComboBoxCaption")); 
        iWantToComboBox.setInvalidAllowed(false);
        iWantToComboBox.setNullSelectionAllowed(false);
        iWantToComboBox.setInputPrompt(Messages.getString("MainView.iWantToComboBoxPrompt")); 
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox1")); 
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox2")); 
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox3")); 
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox4")); 
        
        Label appearsLabel = new Label(Messages.getString("MainView.appearsLabel"), ContentMode.HTML); 
        Label detailsLabel = new Label(Messages.getString("MainView.detailsLabel"), ContentMode.HTML); 
        Label noticesLabel = new Label(Messages.getString("MainView.noticesLabel"), ContentMode.HTML); 
		
		final Table activityTable = new Table();
		activityTable.setHeight(Messages.getString("MainView.activityTableHeight")); 
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn1"), Label.class, null); 
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn2"), Label.class, null); 
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn3"), Label.class, null); 
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn4"), ComboBox.class, null); 
        
        List<Object> iWantToList = new CopyOnWriteArrayList<Object>();
        iWantToList.add(appearsLabel);
        iWantToList.add(detailsLabel);
        iWantToList.add(noticesLabel);
        iWantToList.add(iWantToComboBox);
		
		Object[] iWantToArray = iWantToList.toArray(new Object[iWantToList.size()]);
		activityTable.addItem(iWantToArray, null);
		
		Label numberOfRowsLabel = new Label(Messages.getString("MainView.numberOfRowsLabel1") + activityTable.size() + Messages.getString("MainView.numberOfRowsLabel2"), ContentMode.HTML); 
		
		AbstractOrderedLayout tableVerticalLayout = new VerticalLayout();
		tableVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		tableVerticalLayout.setSizeUndefined();
		tableVerticalLayout.addComponent(numberOfRowsLabel);
		tableVerticalLayout.addComponent(activityTable);
		rootVerticalLayout.addComponent(tableVerticalLayout);
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
