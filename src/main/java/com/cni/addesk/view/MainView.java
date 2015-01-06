package com.cni.addesk.view;

import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.apache.shiro.authc.UsernamePasswordToken;
import org.vaadin.activelink.ActiveLink;
import org.vaadin.activelink.ActiveLink.LinkActivatedEvent;
import org.vaadin.activelink.ActiveLink.LinkActivatedListener;

import com.cni.addesk.bean.ProductsName;
import com.cni.addesk.custom.ClickableLabel;
import com.cni.addesk.util.Messages;
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
	
	public static final String NAME = Messages.getString("MainView.mainViewName");   //$NON-NLS-1$
	private final AbstractOrderedLayout rootVerticalLayout;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if(7 == selectedItem.getId()){
	    		
	    		// "Logout" the user
	            getSession().setAttribute(Messages.getString("MainView.sessionUser"), null);   //$NON-NLS-1$

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
		CustomLayout headerTemplate = new CustomLayout(Messages.getString("MainView.headerTemplate"));   //$NON-NLS-1$
		
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem(Messages.getString("MainView.menuItemCaption"), new ThemeResource(Messages.getString("MainView.menuItem")), null);   //$NON-NLS-1$ //$NON-NLS-2$
		MenuItem homeSubMenuItem = menuItem.addItem(Messages.getString("MainView.homeSubMenuItem"), null, menuCommand);   //$NON-NLS-1$
		MenuItem reportsMenuItem = menuItem.addItem(Messages.getString("MainView.reportsMenuItem"), null, menuCommand);   //$NON-NLS-1$
		MenuItem historyMenuItem = menuItem.addItem(Messages.getString("MainView.historyMenuItem"), null, menuCommand);   //$NON-NLS-1$
		MenuItem helpInfoMenuItem = menuItem.addItem(Messages.getString("MainView.helpInfoMenuItem"), null, menuCommand);   //$NON-NLS-1$
		MenuItem signOutMenuItem = menuItem.addItem(Messages.getString("MainView.signOutMenuItem"), null, menuCommand);  //$NON-NLS-1$
		
		Label logoLabel = new Label(Messages.getString("MainView.logoLabel"), ContentMode.HTML); //$NON-NLS-1$
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem(Messages.getString("MainView.homeMenuItem"), null, menuCommand);           //$NON-NLS-1$
		MenuItem helpMenuItem = rightMenu.addItem(Messages.getString("MainView.helpMenuItem"), null, menuCommand);   //$NON-NLS-1$
		MenuItem myaccountMenuItem = rightMenu.addItem(Messages.getString("MainView.myaccountMenuItem"), null, null);   //$NON-NLS-1$
		MenuItem changePasswordMenuItem = myaccountMenuItem.addItem(Messages.getString("MainView.changePasswordMenuItem"), null, menuCommand);   //$NON-NLS-1$
		
		headerTemplate.addComponent(menubar, Messages.getString("MainView.menubar"));  //$NON-NLS-1$
		headerTemplate.addComponent(logoLabel, Messages.getString("MainView.logo"));  //$NON-NLS-1$
		headerTemplate.addComponent(rightMenu, Messages.getString("MainView.rightMenu"));   //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout campaignHomeHorizontalLayout = new HorizontalLayout();
		campaignHomeHorizontalLayout.setSpacing(true);
		campaignHomeHorizontalLayout.setWidth(Messages.getString("MainView.campaignHomeHorizontalLayoutWidth"));   //$NON-NLS-1$
		campaignHomeHorizontalLayout.setHeight(Messages.getString("MainView.campaignHomeHorizontalLayoutHeight"));   //$NON-NLS-1$
		campaignHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label campaignHomeLabel = new Label(Messages.getString("MainView.campaignHomeLabel"), ContentMode.HTML);   //$NON-NLS-1$
		campaignHomeHorizontalLayout.addComponent(campaignHomeLabel);
		campaignHomeHorizontalLayout.setComponentAlignment(campaignHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label(Messages.getString("MainView.welcomeLabel"), ContentMode.HTML);   //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label(Messages.getString("MainView.usernameLabel"));   //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource(Messages.getString("MainView.companyLogoImage")));   //$NON-NLS-1$
        companyLogoImage.setHeight(Messages.getString("MainView.companyLogoImageHeight"));   //$NON-NLS-1$
        companyLogoImage.setWidth(Messages.getString("MainView.companyLogoImageWidth"));   //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		campaignHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		campaignHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(campaignHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label(Messages.getString("MainView.pageTitleLineLabel"), ContentMode.HTML);   //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		AbstractOrderedLayout contactRootHorizontalLayout = new HorizontalLayout();
		contactRootHorizontalLayout.setSpacing(true);
		contactRootHorizontalLayout.setWidth(Messages.getString("MainView.contactRootHorizontalLayoutWidth"));   //$NON-NLS-1$
		contactRootHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		rootVerticalLayout.addComponent(contactRootHorizontalLayout);		
		
		Label  contactLabel  = new Label(Messages.getString("MainView.contactLabel"), ContentMode.HTML);   //$NON-NLS-1$
		
		ActiveLink contactLink = new ActiveLink(Messages.getString("MainView.contactLinkText"), new ExternalResource(Messages.getString("MainView.contactLink")));   //$NON-NLS-1$ //$NON-NLS-2$
		contactLink.addListener(new LinkActivatedListener() {           
            public void linkActivated(LinkActivatedEvent event) {                
                        //event.isLinkOpened()
            }
        });
		
		AbstractOrderedLayout rootPortalSelectionsHorizontalLayout = new HorizontalLayout();
		rootPortalSelectionsHorizontalLayout.setSpacing(true);
		rootPortalSelectionsHorizontalLayout.setWidth(Messages.getString("MainView.rootPortalSelectionsHorizontalLayoutWidth"));  //$NON-NLS-1$
		rootPortalSelectionsHorizontalLayout.setHeight(Messages.getString("MainView.rootPortalSelectionsHorizontalLayoutHeight"));  //$NON-NLS-1$
		
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
		viewComboBox.setCaption(Messages.getString("MainView.viewComboBoxCaption"));  //$NON-NLS-1$
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox1"));  //$NON-NLS-1$
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox2"));  //$NON-NLS-1$
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox3"));  //$NON-NLS-1$
		viewComboBox.addItem(Messages.getString("MainView.viewComboBox4"));  //$NON-NLS-1$
		
		ComboBox sortComboBox = new ComboBox();
		sortComboBox.setNullSelectionAllowed(false);
		sortComboBox.setCaption(Messages.getString("MainView.sortComboBoxCaption"));  //$NON-NLS-1$
		sortComboBox.addItem(Messages.getString("MainView.sortComboBox1"));  //$NON-NLS-1$
		sortComboBox.addItem(Messages.getString("MainView.sortComboBox2"));  //$NON-NLS-1$
		
		Button filterButton = new Button(Messages.getString("MainView.filterButtonText"));  //$NON-NLS-1$
		
		final Label filtersAppliedLabel = new Label(Messages.getString("MainView.filtersAppliedLabel"), ContentMode.HTML);  //$NON-NLS-1$
		filtersAppliedLabel.setVisible(false);
		
		filterButton.addClickListener(new Button.ClickListener() {

            @Override
            public void buttonClick(ClickEvent event) {
            	
            	// Create a sub-window and add it to the main window
            	final Window subWindow = new Window();
            	subWindow.setWidth(Messages.getString("MainView.subWindowWidth"));  //$NON-NLS-1$
            	subWindow.setHeight(Messages.getString("MainView.subWindowHeight"));  //$NON-NLS-1$
            	subWindow.setImmediate(true);
            	subWindow.setModal(true);
            	
            	final AbstractOrderedLayout subWindowVerticalLayout = new VerticalLayout();
            	subWindowVerticalLayout.setSizeUndefined();
            	subWindowVerticalLayout.setMargin(true);
            	
            	Label campaignListFiltersLabel = new Label(Messages.getString("MainView.campaignListFiltersLabel"), ContentMode.HTML);  //$NON-NLS-1$
            	subWindowVerticalLayout.addComponent(campaignListFiltersLabel);
            	
            	final ComboBox advertiserComboBox = new ComboBox();
            	advertiserComboBox.setNullSelectionAllowed(false);
            	advertiserComboBox.setCaption(Messages.getString("MainView.advertiserComboBoxCaption"));  //$NON-NLS-1$
            	advertiserComboBox.addItem(Messages.getString("MainView.advertiserComboBox1"));  //$NON-NLS-1$
            	subWindowVerticalLayout.addComponent(advertiserComboBox);
            	
            	Label dateRangeLabel = new Label(Messages.getString("MainView.dateRangeLabel"));  //$NON-NLS-1$
            	subWindowVerticalLayout.addComponent(dateRangeLabel);
            	
            	final PopupDateField fromDate = new PopupDateField();
            	// Set the prompt
            	fromDate.setValue(new Date());            	        
            	// Set width explicitly to accommodate the prompt
            	fromDate.setWidth(Messages.getString("MainView.fromDateWidth"));  //$NON-NLS-1$
            	
            	final PopupDateField toDate = new PopupDateField();
            	// Set the prompt
            	toDate.setValue(new Date());            	        
            	// Set width explicitly to accommodate the prompt
            	toDate.setWidth(Messages.getString("MainView.toDateWidth"));  //$NON-NLS-1$
            	
            	AbstractOrderedLayout dateRangeHorizontalLayout = new HorizontalLayout(fromDate, toDate);
            	dateRangeHorizontalLayout.setSizeFull();
            	dateRangeHorizontalLayout.setSpacing(true);
            	
            	subWindowVerticalLayout.addComponent(dateRangeHorizontalLayout);
            	
            	final ComboBox campaignTypeComboBox = new ComboBox();
            	campaignTypeComboBox.setNullSelectionAllowed(false);
            	campaignTypeComboBox.setCaption(Messages.getString("MainView.campaignTypeComboBoxCaption"));  //$NON-NLS-1$
            	campaignTypeComboBox.addItem(Messages.getString("MainView.campaignTypeComboBox1"));  //$NON-NLS-1$
            	campaignTypeComboBox.addItem(Messages.getString("MainView.campaignTypeComboBox2"));  //$NON-NLS-1$
            	campaignTypeComboBox.addItem(Messages.getString("MainView.campaignTypeComboBox3"));  //$NON-NLS-1$
            	
            	subWindowVerticalLayout.addComponent(campaignTypeComboBox);
            	
            	// Have a bean container to put the beans in
                final BeanItemContainer<ProductsName> productsContainer =  new BeanItemContainer<ProductsName>(ProductsName.class);
                
                // Put data in it
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer1")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer2")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer3")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer4")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer5")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer6")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer7")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer8")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer9")));  //$NON-NLS-1$
                productsContainer.addItem(new ProductsName(Messages.getString("MainView.productsContainer10")));  //$NON-NLS-1$
            	
            	final ListSelect productsSelect = new ListSelect(Messages.getString("MainView.productsSelectCaption"), productsContainer);  //$NON-NLS-1$
            	//productsSelect.setMultiSelect(true);
            	// Enable null selection
            	productsSelect.setNullSelectionAllowed(false);
            	// Set the caption mode to read the caption directly
                // from the 'name' property of the bean
            	productsSelect.setItemCaptionMode(ListSelect.ItemCaptionMode.PROPERTY );
            	productsSelect.setItemCaptionPropertyId(Messages.getString("MainView.itemCaptionPropertyId"));  //$NON-NLS-1$
            	
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
            	
            	Button subWindowApplyButton = new Button(Messages.getString("MainView.subWindowApplyButtonText"));  //$NON-NLS-1$
            	Button subWindowCancelButton = new Button(Messages.getString("MainView.subWindowCancelButtonText"));  //$NON-NLS-1$
            	Button subWindowClearButton = new Button(Messages.getString("MainView.subWindowClearButtonText"));  //$NON-NLS-1$
            	
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
		
		Embedded uploadImage = new Embedded(null, new ThemeResource(Messages.getString("MainView.uploadImage")));  //$NON-NLS-1$
		
		ClickableLabel uploadCreativeLabel = new ClickableLabel(Messages.getString("MainView.uploadCreativeLabel"));  //$NON-NLS-1$
		
		uploadCreativeLabel.addLayoutClickListener(new LayoutClickListener() {            
            @Override
             public void layoutClick(LayoutClickEvent event) {
                getUI().getNavigator().addView(UploadView.NAME, UploadView.class);
                // Navigate to upload creative view
                getUI().getNavigator().navigateTo(UploadView.NAME);
              }
        });  
		
		uploadCreativeHorizontalLayout.addComponent(uploadImage);
		uploadCreativeHorizontalLayout.addComponent(uploadCreativeLabel);
		uploadCreativeHorizontalLayout.setComponentAlignment(uploadCreativeLabel, Alignment.MIDDLE_LEFT);		
		
		rootVerticalLayout.addComponent(uploadCreativeHorizontalLayout);
		
		final ComboBox iWantToComboBox = new ComboBox(Messages.getString("MainView.iWantToComboBoxCaption"));  //$NON-NLS-1$
        iWantToComboBox.setInvalidAllowed(false);
        iWantToComboBox.setNullSelectionAllowed(false);
        iWantToComboBox.setInputPrompt(Messages.getString("MainView.iWantToComboBoxPrompt"));  //$NON-NLS-1$
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox1"));  //$NON-NLS-1$
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox2"));  //$NON-NLS-1$
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox3"));  //$NON-NLS-1$
        iWantToComboBox.addItem(Messages.getString("MainView.iWantToComboBox4"));  //$NON-NLS-1$
        
        Label appearsLabel = new Label(Messages.getString("MainView.appearsLabel"), ContentMode.HTML);  //$NON-NLS-1$
        Label detailsLabel = new Label(Messages.getString("MainView.detailsLabel"), ContentMode.HTML);  //$NON-NLS-1$
        Label noticesLabel = new Label(Messages.getString("MainView.noticesLabel"), ContentMode.HTML);  //$NON-NLS-1$
		
		final Table activityTable = new Table();
		activityTable.setHeight(Messages.getString("MainView.activityTableHeight"));  //$NON-NLS-1$
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn1"), Label.class, null);  //$NON-NLS-1$
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn2"), Label.class, null);  //$NON-NLS-1$
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn3"), Label.class, null);  //$NON-NLS-1$
		activityTable.addContainerProperty(Messages.getString("MainView.activityTableColumn4"), ComboBox.class, null);  //$NON-NLS-1$
        
        List<Object> iWantToList = new CopyOnWriteArrayList<Object>();
        iWantToList.add(appearsLabel);
        iWantToList.add(detailsLabel);
        iWantToList.add(noticesLabel);
        iWantToList.add(iWantToComboBox);
		
		Object[] iWantToArray = iWantToList.toArray(new Object[iWantToList.size()]);
		activityTable.addItem(iWantToArray, null);
		
		Label numberOfRowsLabel = new Label(Messages.getString("MainView.numberOfRowsLabel1") + activityTable.size() + Messages.getString("MainView.numberOfRowsLabel2"), ContentMode.HTML);  //$NON-NLS-1$ //$NON-NLS-2$
		
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
