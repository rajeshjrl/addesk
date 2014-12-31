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


public class InformationView extends CustomComponent implements View{
	
	public static final String NAME = Messages.getString("InformationView.informationViewName");   
	private final AbstractOrderedLayout rootVerticalLayout;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if(7 == selectedItem.getId()){
	    		
	    		// "Logout" the user
	            getSession().setAttribute(Messages.getString("InformationView.sessionUser"), null);   

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);
	    		
	    	}
	    }  
	};
	
	public InformationView(){
		//setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
	    rootVerticalLayout.setSizeFull();
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout(Messages.getString("InformationView.headerTemplate"));   
		
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem(Messages.getString("InformationView.menuItemCaption"), new ThemeResource(Messages.getString("InformationView.menuItem")), null);   
		MenuItem homeSubMenuItem = menuItem.addItem(Messages.getString("InformationView.homeSubMenuItem"), null, menuCommand);   
		MenuItem reportsMenuItem = menuItem.addItem(Messages.getString("InformationView.reportsMenuItem"), null, menuCommand);   
		MenuItem historyMenuItem = menuItem.addItem(Messages.getString("InformationView.historyMenuItem"), null, menuCommand);   
		MenuItem helpInfoMenuItem = menuItem.addItem(Messages.getString("InformationView.helpInfoMenuItem"), null, menuCommand);   
		MenuItem signOutMenuItem = menuItem.addItem(Messages.getString("InformationView.signOutMenuItem"), null, menuCommand);  
		
		Label logoLabel = new Label(Messages.getString("InformationView.logoLabel"), ContentMode.HTML); 
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem(Messages.getString("InformationView.homeMenuItem"), null, menuCommand);           
		MenuItem helpMenuItem = rightMenu.addItem(Messages.getString("InformationView.helpMenuItem"), null, menuCommand);   
		MenuItem myaccountMenuItem = rightMenu.addItem(Messages.getString("InformationView.myaccountMenuItem"), null, null);   
		MenuItem changePasswordMenuItem = myaccountMenuItem.addItem(Messages.getString("InformationView.changePasswordMenuItem"), null, menuCommand);   
		
		headerTemplate.addComponent(menubar, Messages.getString("InformationView.menubar"));  
		headerTemplate.addComponent(logoLabel, Messages.getString("InformationView.logo"));  
		headerTemplate.addComponent(rightMenu, Messages.getString("InformationView.rightMenu"));   
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout informationHomeHorizontalLayout = new HorizontalLayout();
		informationHomeHorizontalLayout.setSpacing(true);
		informationHomeHorizontalLayout.setWidth(Messages.getString("InformationView.informationHomeHorizontalLayoutWidth"));   
		informationHomeHorizontalLayout.setHeight(Messages.getString("InformationView.informationHomeHorizontalLayoutHeight"));   
		informationHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label informationHomeLabel = new Label(Messages.getString("InformationView.informationHomeLabel"), ContentMode.HTML);   
		informationHomeHorizontalLayout.addComponent(informationHomeLabel);
		informationHomeHorizontalLayout.setComponentAlignment(informationHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label(Messages.getString("InformationView.welcomeLabel"), ContentMode.HTML);   
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label(Messages.getString("InformationView.usernameLabel"));   
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource(Messages.getString("InformationView.companyLogoImage")));   
        companyLogoImage.setHeight(Messages.getString("InformationView.companyLogoImageHeight"));   
        companyLogoImage.setWidth(Messages.getString("InformationView.companyLogoImageWidth"));  
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		informationHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		informationHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(informationHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label(Messages.getString("InformationView.pageTitleLineLabel"), ContentMode.HTML);   
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		AbstractOrderedLayout contactRootHorizontalLayout = new HorizontalLayout();
		contactRootHorizontalLayout.setSpacing(true);
		contactRootHorizontalLayout.setWidth(Messages.getString("InformationView.contactRootHorizontalLayoutWidth"));   
		contactRootHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		rootVerticalLayout.addComponent(contactRootHorizontalLayout);		
		
		Label  contactLabel  = new Label(Messages.getString("InformationView.contactLabel"), ContentMode.HTML);   
		
		ActiveLink contactLink = new ActiveLink(Messages.getString("InformationView.contactLinkText"), new ExternalResource(Messages.getString("InformationView.contactLink")));   
		contactLink.addListener(new LinkActivatedListener() {           
            public void linkActivated(LinkActivatedEvent event) {                
                        //event.isLinkOpened()
            }
        });
		
		AbstractOrderedLayout rootInformationHorizontalLayout = new HorizontalLayout();
		rootInformationHorizontalLayout.setSpacing(true);
		rootInformationHorizontalLayout.setWidth(Messages.getString("InformationView.rootInformationHorizontalLayoutWidth"));  
		rootInformationHorizontalLayout.setHeight(Messages.getString("InformationView.rootInformationHorizontalLayoutHeight"));  
		
		AbstractOrderedLayout contactHorizontalLayout = new HorizontalLayout(contactLabel, contactLink);
		contactHorizontalLayout.setSpacing(true);
		contactHorizontalLayout.setSizeUndefined();
		
		AbstractOrderedLayout informationHorizontalLayout = new HorizontalLayout();
		informationHorizontalLayout.setSpacing(true);
		informationHorizontalLayout.setSizeUndefined();
		
		rootInformationHorizontalLayout.addComponent(informationHorizontalLayout);
		rootInformationHorizontalLayout.addComponent(contactHorizontalLayout);
		rootInformationHorizontalLayout.setComponentAlignment(contactHorizontalLayout, Alignment.TOP_RIGHT);		
		
		contactRootHorizontalLayout.addComponent(rootInformationHorizontalLayout);
		
		/*AbstractOrderedLayout rootPortalSelectionsHorizontalLayout = new HorizontalLayout();
		rootPortalSelectionsHorizontalLayout.setSpacing(true);
		rootPortalSelectionsHorizontalLayout.setWidth(Messages.getString("InformationView.rootPortalSelectionsHorizontalLayoutWidth"));  
		rootPortalSelectionsHorizontalLayout.setHeight(Messages.getString("InformationView.rootPortalSelectionsHorizontalLayoutHeight"));  
		
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
		viewComboBox.setCaption(Messages.getString("InformationView.viewComboBoxCaption"));  
		viewComboBox.addItem(Messages.getString("InformationView.viewComboBox1"));  
		viewComboBox.addItem(Messages.getString("InformationView.viewComboBox2"));  
		viewComboBox.addItem(Messages.getString("InformationView.viewComboBox3"));  
		viewComboBox.addItem(Messages.getString("InformationView.viewComboBox4"));  
		
		ComboBox sortComboBox = new ComboBox();
		sortComboBox.setNullSelectionAllowed(false);
		sortComboBox.setCaption(Messages.getString("InformationView.sortComboBoxCaption"));  
		sortComboBox.addItem(Messages.getString("InformationView.sortComboBox1"));  
		sortComboBox.addItem(Messages.getString("InformationView.sortComboBox2"));  
		
		Button filterButton = new Button(Messages.getString("InformationView.filterButtonText"));  
		
		final Label filtersAppliedLabel = new Label(Messages.getString("InformationView.filtersAppliedLabel"), ContentMode.HTML);  
		filtersAppliedLabel.setVisible(false);
		
		filterButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	
            	// Create a sub-window and add it to the main window
            	final Window subWindow = new Window();
            	subWindow.setWidth(Messages.getString("InformationView.subWindowWidth"));  
            	subWindow.setHeight(Messages.getString("InformationView.subWindowHeight"));  
            	subWindow.setImmediate(true);
            	subWindow.setModal(true);
            	
            	final AbstractOrderedLayout subWindowVerticalLayout = new VerticalLayout();
            	subWindowVerticalLayout.setSizeUndefined();
            	subWindowVerticalLayout.setMargin(true);
            	
            	Label campaignListFiltersLabel = new Label(Messages.getString("InformationView.campaignListFiltersLabel"), ContentMode.HTML);  
            	subWindowVerticalLayout.addComponent(campaignListFiltersLabel);
            	
            	final ComboBox advertiserComboBox = new ComboBox();
            	advertiserComboBox.setNullSelectionAllowed(false);
            	advertiserComboBox.setCaption(Messages.getString("InformationView.advertiserComboBoxCaption"));  
            	advertiserComboBox.addItem(Messages.getString("InformationView.advertiserComboBox1"));  
            	subWindowVerticalLayout.addComponent(advertiserComboBox);
            	
            	Label dateRangeLabel = new Label(Messages.getString("InformationView.dateRangeLabel"));  
            	subWindowVerticalLayout.addComponent(dateRangeLabel);
            	
            	final PopupDateField fromDate = new PopupDateField();
            	// Set the prompt
            	fromDate.setValue(new Date());            	        
            	// Set width explicitly to accommodate the prompt
            	fromDate.setWidth(Messages.getString("InformationView.fromDateWidth"));  
            	
            	final PopupDateField toDate = new PopupDateField();
            	// Set the prompt
            	toDate.setValue(new Date());            	        
            	// Set width explicitly to accommodate the prompt
            	toDate.setWidth(Messages.getString("InformationView.toDateWidth"));  
            	
            	AbstractOrderedLayout dateRangeHorizontalLayout = new HorizontalLayout(fromDate, toDate);
            	dateRangeHorizontalLayout.setSizeFull();
            	dateRangeHorizontalLayout.setSpacing(true);
            	
            	subWindowVerticalLayout.addComponent(dateRangeHorizontalLayout);
            	
            	final ComboBox campaignTypeComboBox = new ComboBox();
            	campaignTypeComboBox.setNullSelectionAllowed(false);
            	campaignTypeComboBox.setCaption(Messages.getString("InformationView.campaignTypeComboBoxCaption"));  
            	campaignTypeComboBox.addItem(Messages.getString("InformationView.campaignTypeComboBox1"));  
            	campaignTypeComboBox.addItem(Messages.getString("InformationView.campaignTypeComboBox2"));  
            	campaignTypeComboBox.addItem(Messages.getString("InformationView.campaignTypeComboBox3"));  
            	
            	subWindowVerticalLayout.addComponent(campaignTypeComboBox);
            	
            	// Have a bean container to put the beans in
                final BeanItemContainer<ProductsName> productsContainer =  new BeanItemContainer<ProductsName>(ProductsName.class);
                
                // Put data in it
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer1")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer2")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer3")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer4")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer5")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer6")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer7")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer8")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer9")));  
                productsContainer.addItem(new ProductsName(Messages.getString("InformationView.productsContainer10")));  
            	
            	final ListSelect productsSelect = new ListSelect(Messages.getString("InformationView.productsSelectCaption"), productsContainer);  
            	//productsSelect.setMultiSelect(true);
            	// Enable null selection
            	productsSelect.setNullSelectionAllowed(false);
            	// Set the caption mode to read the caption directly
                // from the 'name' property of the bean
            	productsSelect.setItemCaptionMode(ListSelect.ItemCaptionMode.PROPERTY );
            	productsSelect.setItemCaptionPropertyId(Messages.getString("InformationView.itemCaptionPropertyId"));  
            	
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
            	
            	Button subWindowApplyButton = new Button(Messages.getString("InformationView.subWindowApplyButtonText"));  
            	Button subWindowCancelButton = new Button(Messages.getString("InformationView.subWindowCancelButtonText"));  
            	Button subWindowClearButton = new Button(Messages.getString("InformationView.subWindowClearButtonText"));  
            	
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
		
		Embedded uploadImage = new Embedded(null, new ThemeResource(Messages.getString("InformationView.uploadImage")));  
		
		ClickableLabel uploadCreativeLabel = new ClickableLabel(Messages.getString("InformationView.uploadCreativeLabel"));  
		
		uploadCreativeHorizontalLayout.addComponent(uploadImage);
		uploadCreativeHorizontalLayout.addComponent(uploadCreativeLabel);
		uploadCreativeHorizontalLayout.setComponentAlignment(uploadCreativeLabel, Alignment.MIDDLE_LEFT);		
		
		rootVerticalLayout.addComponent(uploadCreativeHorizontalLayout);
		
		final ComboBox iWantToComboBox = new ComboBox(Messages.getString("InformationView.iWantToComboBoxCaption"));  
        iWantToComboBox.setInvalidAllowed(false);
        iWantToComboBox.setNullSelectionAllowed(false);
        iWantToComboBox.setInputPrompt(Messages.getString("InformationView.iWantToComboBoxPrompt"));  
        iWantToComboBox.addItem(Messages.getString("InformationView.iWantToComboBox1"));  
        iWantToComboBox.addItem(Messages.getString("InformationView.iWantToComboBox2"));  
        iWantToComboBox.addItem(Messages.getString("InformationView.iWantToComboBox3"));  
        iWantToComboBox.addItem(Messages.getString("InformationView.iWantToComboBox4"));  
        
        Label appearsLabel = new Label(Messages.getString("InformationView.appearsLabel"), ContentMode.HTML);  
        Label detailsLabel = new Label(Messages.getString("InformationView.detailsLabel"), ContentMode.HTML);  
        Label noticesLabel = new Label(Messages.getString("InformationView.noticesLabel"), ContentMode.HTML);  
		
		final Table activityTable = new Table();
		activityTable.setHeight(Messages.getString("InformationView.activityTableHeight"));  
		activityTable.addContainerProperty(Messages.getString("InformationView.activityTableColumn1"), Label.class, null);  
		activityTable.addContainerProperty(Messages.getString("InformationView.activityTableColumn2"), Label.class, null);  
		activityTable.addContainerProperty(Messages.getString("InformationView.activityTableColumn3"), Label.class, null);  
		activityTable.addContainerProperty(Messages.getString("InformationView.activityTableColumn4"), ComboBox.class, null);  
        
        List<Object> iWantToList = new CopyOnWriteArrayList<Object>();
        iWantToList.add(appearsLabel);
        iWantToList.add(detailsLabel);
        iWantToList.add(noticesLabel);
        iWantToList.add(iWantToComboBox);
		
		Object[] iWantToArray = iWantToList.toArray(new Object[iWantToList.size()]);
		activityTable.addItem(iWantToArray, null);
		
		Label numberOfRowsLabel = new Label(Messages.getString("InformationView.numberOfRowsLabel1") + activityTable.size() + Messages.getString("InformationView.numberOfRowsLabel2"), ContentMode.HTML);  
		
		AbstractOrderedLayout tableVerticalLayout = new VerticalLayout();
		tableVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		tableVerticalLayout.setSizeUndefined();
		tableVerticalLayout.addComponent(numberOfRowsLabel);
		tableVerticalLayout.addComponent(activityTable);
		rootVerticalLayout.addComponent(tableVerticalLayout);*/
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
