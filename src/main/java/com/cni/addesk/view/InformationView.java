package com.cni.addesk.view;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vaadin.activelink.ActiveLink;
import org.vaadin.activelink.ActiveLink.LinkActivatedEvent;
import org.vaadin.activelink.ActiveLink.LinkActivatedListener;

import com.cni.addesk.bean.ContactInformation;
import com.cni.addesk.bean.ProductsName;
import com.cni.addesk.custom.ClickableLabel;
import com.cni.addesk.custom.InstallContactInformationValidatorBlurListener;
import com.cni.addesk.util.ErrorUtils;
import com.cni.addesk.util.Messages;
import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.validator.BeanValidator;
import com.vaadin.event.LayoutEvents.LayoutClickEvent;
import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.ExternalResource;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.AbstractTextField;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.Field;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import com.vaadin.ui.Link;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.TextField;
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
		
		Label informationNeededLabel = new Label("<span class='information_needed'>This information is needed in case<br/>we have questions about the creative.</span>", ContentMode.HTML);
		informationHorizontalLayout.addComponent(informationNeededLabel);
		
		rootInformationHorizontalLayout.addComponent(informationHorizontalLayout);
		rootInformationHorizontalLayout.addComponent(contactHorizontalLayout);
		rootInformationHorizontalLayout.setComponentAlignment(contactHorizontalLayout, Alignment.TOP_RIGHT);	
		
		Label contactLineLabel = new Label(Messages.getString("InformationView.pageTitleLineLabel"), ContentMode.HTML); 
		
		rootVerticalLayout.addComponent(contactLineLabel);
		
		contactRootHorizontalLayout.addComponent(rootInformationHorizontalLayout);
		
		AbstractOrderedLayout contactInformationVerticalLayout = new VerticalLayout();
		informationHorizontalLayout.setSpacing(true);
		informationHorizontalLayout.setSizeUndefined();
		contactInformationVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label contactInformationLabel = new Label("<span class='contact_information'>Contact Information</span><br/>This information is necessary to contact you if we have<br/>questions related to your submission.", ContentMode.HTML);
		contactInformationVerticalLayout.addComponent(contactInformationLabel);
		
		AbstractOrderedLayout contactInformationHorizontalLayout = new HorizontalLayout();
		contactInformationHorizontalLayout.setSpacing(true);
		contactInformationHorizontalLayout.setSizeUndefined();
		
		final BeanItem<ContactInformation> item = new BeanItem<ContactInformation>(new ContactInformation());
		final FieldGroup fieldGroup = new FieldGroup(item);
		
		final AbstractTextField firstName = (AbstractTextField)fieldGroup.buildAndBind("First name", "firstName");
		firstName.setNullRepresentation("");
		firstName.setInputPrompt("Required");
		firstName.setWidth("350px");  
		final AbstractTextField lastName = (AbstractTextField)fieldGroup.buildAndBind("Last name", "lastName");
		lastName.setNullRepresentation("");
		lastName.setInputPrompt("Required");
		lastName.setWidth("350px");
		final AbstractTextField emailAddress = (AbstractTextField)fieldGroup.buildAndBind("email Address", "emailAddress");
		emailAddress.setNullRepresentation("");
		emailAddress.setInputPrompt("Required");
		emailAddress.setWidth("350px");
		final AbstractTextField phoneNumber = (AbstractTextField)fieldGroup.buildAndBind("Phone Number", "phoneNumber");
		phoneNumber.setNullRepresentation("");
		phoneNumber.setInputPrompt("Required");
		phoneNumber.setWidth("350px");
		
		final AbstractTextField companyName = (AbstractTextField)fieldGroup.buildAndBind("Your Company Name", "companyName");
		companyName.setNullRepresentation("");
		companyName.setWidth("350px");  
		final AbstractTextField advertiserName = (AbstractTextField)fieldGroup.buildAndBind("Last name", "advertiserName");
		advertiserName.setNullRepresentation("");
		advertiserName.setWidth("350px");
		
		/*firstName.addBlurListener(new InstallContactInformationValidatorBlurListener(firstName, "firstName"));
		lastName.addBlurListener(new InstallContactInformationValidatorBlurListener(lastName, "lastName"));
		emailAddress.addBlurListener(new InstallContactInformationValidatorBlurListener(emailAddress, "emailAddress"));
		phoneNumber.addBlurListener(new InstallContactInformationValidatorBlurListener(phoneNumber, "phoneNumber"));
		companyName.addBlurListener(new InstallContactInformationValidatorBlurListener(companyName, "companyName"));
		advertiserName.addBlurListener(new InstallContactInformationValidatorBlurListener(advertiserName, "advertiserName"));*/
		
		AbstractOrderedLayout lhsFieldsVerticalLayout = new VerticalLayout(firstName, lastName, emailAddress, phoneNumber);
		lhsFieldsVerticalLayout.setSpacing(true);
		lhsFieldsVerticalLayout.setSizeUndefined();
		lhsFieldsVerticalLayout.setMargin(new MarginInfo(true, true, true, false));
		
		contactInformationHorizontalLayout.addComponent(lhsFieldsVerticalLayout);
		
		AbstractOrderedLayout rhsFieldsVerticalLayout = new VerticalLayout(companyName, advertiserName);
		rhsFieldsVerticalLayout.setSpacing(true);
		rhsFieldsVerticalLayout.setSizeUndefined();
		rhsFieldsVerticalLayout.setMargin(new MarginInfo(true, false, true, false));
		
		contactInformationHorizontalLayout.addComponent(rhsFieldsVerticalLayout);
		
		contactInformationVerticalLayout.addComponent(contactInformationHorizontalLayout);
		
		Button continueButton = new Button("CONTINUE");	
		continueButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	try {
            		
            		Collection<String> fieldGroupErrors = ErrorUtils.getComponentError(fieldGroup.getFields());
            		
            		if(fieldGroupErrors.isEmpty()){
            			firstName.addValidator(new BeanValidator(ContactInformation.class, "firstName"));
            			lastName.addValidator(new BeanValidator(ContactInformation.class, "lastName"));
            			emailAddress.addValidator(new BeanValidator(ContactInformation.class, "emailAddress"));
            			phoneNumber.addValidator(new BeanValidator(ContactInformation.class, "phoneNumber"));
            			companyName.addValidator(new BeanValidator(ContactInformation.class, "companyName"));
            			advertiserName.addValidator(new BeanValidator(ContactInformation.class, "advertiserName"));
            		}
            		
                    fieldGroup.commit();
                } catch (CommitException e) {
                    // Show all the validate errors:
                    ErrorUtils.showComponentErrors(fieldGroup.getFields());

                    return;
                }
            }
        });
		Button cancelButton = new Button("CANCEL");
		cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	// "Logout" the user
	            getSession().setAttribute(Messages.getString("InformationView.sessionUser"), null);   

	            // Refresh this view, should redirect to login view
	            getUI().getNavigator().navigateTo(NAME);
            }
        });
		
		AbstractOrderedLayout buttonsHorizontalLayout = new HorizontalLayout(continueButton, cancelButton);
		buttonsHorizontalLayout.setSizeUndefined();
		buttonsHorizontalLayout.setSpacing(true);
		buttonsHorizontalLayout.setMargin(new MarginInfo(true, false, true, false));
        
        contactInformationVerticalLayout.addComponent(buttonsHorizontalLayout);
		
		rootVerticalLayout.addComponent(contactInformationVerticalLayout);
		
		
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
