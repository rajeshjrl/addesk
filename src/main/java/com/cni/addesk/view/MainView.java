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
		setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
	    //rootVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
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
		
		AbstractOrderedLayout contactHorizontalLayout = new HorizontalLayout(contactLabel, contactLink);
		contactHorizontalLayout.setSpacing(true);
		contactHorizontalLayout.setSizeUndefined();
		
		contactRootHorizontalLayout.addComponent(contactHorizontalLayout);
		contactRootHorizontalLayout.setComponentAlignment(contactHorizontalLayout, Alignment.TOP_RIGHT);		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
