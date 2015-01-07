package com.cni.addesk.view;

import java.util.Collection;

import org.vaadin.activelink.ActiveLink;
import org.vaadin.activelink.ActiveLink.LinkActivatedEvent;
import org.vaadin.activelink.ActiveLink.LinkActivatedListener;

import com.cni.addesk.bean.ContactInformation;
import com.cni.addesk.util.ErrorUtils;
import com.cni.addesk.util.Messages;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.validator.BeanValidator;
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
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.MenuBar.MenuItem;


public class InformationView extends CustomComponent implements View{
	
	public static final String NAME = Messages.getString("InformationView.informationViewName");    //$NON-NLS-1$
	private final AbstractOrderedLayout rootVerticalLayout;
	private View oldView;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if(7 == selectedItem.getId()){
	    		
	    		// "Logout" the user
	            getSession().setAttribute(Messages.getString("InformationView.sessionUser"), null);    //$NON-NLS-1$

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
		CustomLayout headerTemplate = new CustomLayout(Messages.getString("InformationView.headerTemplate"));    //$NON-NLS-1$
		
		MenuBar menubar = new MenuBar();
		MenuItem menuItem = menubar.addItem(Messages.getString("InformationView.menuItemCaption"), new ThemeResource(Messages.getString("InformationView.menuItem")), null);    //$NON-NLS-1$ //$NON-NLS-2$
		MenuItem homeSubMenuItem = menuItem.addItem(Messages.getString("InformationView.homeSubMenuItem"), null, menuCommand);    //$NON-NLS-1$
		MenuItem reportsMenuItem = menuItem.addItem(Messages.getString("InformationView.reportsMenuItem"), null, menuCommand);    //$NON-NLS-1$
		MenuItem historyMenuItem = menuItem.addItem(Messages.getString("InformationView.historyMenuItem"), null, menuCommand);    //$NON-NLS-1$
		MenuItem helpInfoMenuItem = menuItem.addItem(Messages.getString("InformationView.helpInfoMenuItem"), null, menuCommand);    //$NON-NLS-1$
		MenuItem signOutMenuItem = menuItem.addItem(Messages.getString("InformationView.signOutMenuItem"), null, menuCommand);   //$NON-NLS-1$
		
		Label logoLabel = new Label(Messages.getString("InformationView.logoLabel"), ContentMode.HTML);  //$NON-NLS-1$
		
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem(Messages.getString("InformationView.homeMenuItem"), null, menuCommand);            //$NON-NLS-1$
		MenuItem helpMenuItem = rightMenu.addItem(Messages.getString("InformationView.helpMenuItem"), null, menuCommand);    //$NON-NLS-1$
		MenuItem myaccountMenuItem = rightMenu.addItem(Messages.getString("InformationView.myaccountMenuItem"), null, null);    //$NON-NLS-1$
		MenuItem changePasswordMenuItem = myaccountMenuItem.addItem(Messages.getString("InformationView.changePasswordMenuItem"), null, menuCommand);    //$NON-NLS-1$
		
		headerTemplate.addComponent(menubar, Messages.getString("InformationView.menubar"));   //$NON-NLS-1$
		headerTemplate.addComponent(logoLabel, Messages.getString("InformationView.logo"));   //$NON-NLS-1$
		headerTemplate.addComponent(rightMenu, Messages.getString("InformationView.rightMenu"));    //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(headerTemplate);	
		
		AbstractOrderedLayout informationHomeHorizontalLayout = new HorizontalLayout();
		informationHomeHorizontalLayout.setSpacing(true);
		informationHomeHorizontalLayout.setWidth(Messages.getString("InformationView.informationHomeHorizontalLayoutWidth"));    //$NON-NLS-1$
		informationHomeHorizontalLayout.setHeight(Messages.getString("InformationView.informationHomeHorizontalLayoutHeight"));    //$NON-NLS-1$
		informationHomeHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label informationHomeLabel = new Label(Messages.getString("InformationView.informationHomeLabel"), ContentMode.HTML);    //$NON-NLS-1$
		informationHomeHorizontalLayout.addComponent(informationHomeLabel);
		informationHomeHorizontalLayout.setComponentAlignment(informationHomeLabel, Alignment.MIDDLE_LEFT);
		
		AbstractOrderedLayout welcomeVerticalLayout = new VerticalLayout();
		welcomeVerticalLayout.setSizeUndefined();
		
		Label welcomeLabel = new Label(Messages.getString("InformationView.welcomeLabel"), ContentMode.HTML);    //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(welcomeLabel);
		welcomeVerticalLayout.setComponentAlignment(welcomeLabel, Alignment.TOP_RIGHT);
		Label usernameLabel = new Label(Messages.getString("InformationView.usernameLabel"));    //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(usernameLabel);
		welcomeVerticalLayout.setComponentAlignment(usernameLabel, Alignment.TOP_RIGHT);        
        
        Embedded companyLogoImage = new Embedded(null, new ThemeResource(Messages.getString("InformationView.companyLogoImage")));    //$NON-NLS-1$
        companyLogoImage.setHeight(Messages.getString("InformationView.companyLogoImageHeight"));    //$NON-NLS-1$
        companyLogoImage.setWidth(Messages.getString("InformationView.companyLogoImageWidth"));   //$NON-NLS-1$
		welcomeVerticalLayout.addComponent(companyLogoImage);
        welcomeVerticalLayout.setComponentAlignment(companyLogoImage, Alignment.TOP_RIGHT);
		
		informationHomeHorizontalLayout.addComponent(welcomeVerticalLayout);	
		informationHomeHorizontalLayout.setComponentAlignment(welcomeVerticalLayout, Alignment.TOP_RIGHT);		
		
		rootVerticalLayout.addComponent(informationHomeHorizontalLayout);	
		
		Label  pageTitleLineLabel  = new Label(Messages.getString("InformationView.pageTitleLineLabel"), ContentMode.HTML);    //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(pageTitleLineLabel);
		
		AbstractOrderedLayout contactRootHorizontalLayout = new HorizontalLayout();
		contactRootHorizontalLayout.setSpacing(true);
		contactRootHorizontalLayout.setWidth(Messages.getString("InformationView.contactRootHorizontalLayoutWidth"));    //$NON-NLS-1$
		contactRootHorizontalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		rootVerticalLayout.addComponent(contactRootHorizontalLayout);		
		
		Label  contactLabel  = new Label(Messages.getString("InformationView.contactLabel"), ContentMode.HTML);    //$NON-NLS-1$
		
		ActiveLink contactLink = new ActiveLink(Messages.getString("InformationView.contactLinkText"), new ExternalResource(Messages.getString("InformationView.contactLink")));    //$NON-NLS-1$ //$NON-NLS-2$
		contactLink.addListener(new LinkActivatedListener() {           
            public void linkActivated(LinkActivatedEvent event) {                
                        //event.isLinkOpened()
            }
        });
		
		AbstractOrderedLayout rootInformationHorizontalLayout = new HorizontalLayout();
		rootInformationHorizontalLayout.setSpacing(true);
		rootInformationHorizontalLayout.setWidth(Messages.getString("InformationView.rootInformationHorizontalLayoutWidth"));   //$NON-NLS-1$
		rootInformationHorizontalLayout.setHeight(Messages.getString("InformationView.rootInformationHorizontalLayoutHeight"));   //$NON-NLS-1$
		
		AbstractOrderedLayout contactHorizontalLayout = new HorizontalLayout(contactLabel, contactLink);
		contactHorizontalLayout.setSpacing(true);
		contactHorizontalLayout.setSizeUndefined();
		
		AbstractOrderedLayout informationHorizontalLayout = new HorizontalLayout();
		informationHorizontalLayout.setSpacing(true);
		informationHorizontalLayout.setSizeUndefined();
		
		Label informationNeededLabel = new Label(Messages.getString("InformationView.informationNeededLabel"), ContentMode.HTML); //$NON-NLS-1$
		informationHorizontalLayout.addComponent(informationNeededLabel);
		
		rootInformationHorizontalLayout.addComponent(informationHorizontalLayout);
		rootInformationHorizontalLayout.addComponent(contactHorizontalLayout);
		rootInformationHorizontalLayout.setComponentAlignment(contactHorizontalLayout, Alignment.TOP_RIGHT);	
		
		Label contactLineLabel = new Label(Messages.getString(Messages.getString("InformationView.contactLineLabel")), ContentMode.HTML);  //$NON-NLS-1$
		
		rootVerticalLayout.addComponent(contactLineLabel);
		
		contactRootHorizontalLayout.addComponent(rootInformationHorizontalLayout);
		
		AbstractOrderedLayout contactInformationVerticalLayout = new VerticalLayout();
		informationHorizontalLayout.setSpacing(true);
		informationHorizontalLayout.setSizeUndefined();
		contactInformationVerticalLayout.setMargin(new MarginInfo(false, true, false, true));
		
		Label contactInformationLabel = new Label(Messages.getString("InformationView.contactInformationLabel"), ContentMode.HTML); //$NON-NLS-1$
		contactInformationVerticalLayout.addComponent(contactInformationLabel);
		
		AbstractOrderedLayout contactInformationHorizontalLayout = new HorizontalLayout();
		contactInformationHorizontalLayout.setSpacing(true);
		contactInformationHorizontalLayout.setSizeUndefined();
		
		final BeanItem<ContactInformation> item = new BeanItem<ContactInformation>(new ContactInformation());
		final FieldGroup fieldGroup = new FieldGroup(item);
		
		final AbstractTextField firstName = (AbstractTextField)fieldGroup.buildAndBind(Messages.getString("InformationView.firstName"), "firstName"); //$NON-NLS-1$ //$NON-NLS-2$
		firstName.setNullRepresentation(Messages.getString("InformationView.nullRepresentation")); //$NON-NLS-1$
		firstName.setInputPrompt(Messages.getString("InformationView.inputPromptRequired")); //$NON-NLS-1$
		firstName.setWidth(Messages.getString("InformationView.textFieldWidth"));   //$NON-NLS-1$
		final AbstractTextField lastName = (AbstractTextField)fieldGroup.buildAndBind(Messages.getString("InformationView.lastName"), "lastName"); //$NON-NLS-1$ //$NON-NLS-2$
		lastName.setNullRepresentation(Messages.getString("InformationView.nullRepresentation")); //$NON-NLS-1$
		lastName.setInputPrompt(Messages.getString("InformationView.inputPromptRequired")); //$NON-NLS-1$
		lastName.setWidth(Messages.getString("InformationView.textFieldWidth")); //$NON-NLS-1$
		final AbstractTextField emailAddress = (AbstractTextField)fieldGroup.buildAndBind(Messages.getString("InformationView.emailAddress"), "emailAddress"); //$NON-NLS-1$ //$NON-NLS-2$
		emailAddress.setNullRepresentation(Messages.getString("InformationView.nullRepresentation")); //$NON-NLS-1$
		emailAddress.setInputPrompt(Messages.getString("InformationView.inputPromptRequired")); //$NON-NLS-1$
		emailAddress.setWidth(Messages.getString("InformationView.textFieldWidth")); //$NON-NLS-1$
		final AbstractTextField phoneNumber = (AbstractTextField)fieldGroup.buildAndBind(Messages.getString("InformationView.phoneNumber"), "phoneNumber"); //$NON-NLS-1$ //$NON-NLS-2$
		phoneNumber.setNullRepresentation(Messages.getString("InformationView.nullRepresentation")); //$NON-NLS-1$
		phoneNumber.setInputPrompt(Messages.getString("InformationView.inputPromptRequired")); //$NON-NLS-1$
		phoneNumber.setWidth(Messages.getString("InformationView.textFieldWidth")); //$NON-NLS-1$
		
		final AbstractTextField companyName = (AbstractTextField)fieldGroup.buildAndBind(Messages.getString("InformationView.companyName"), "companyName"); //$NON-NLS-1$ //$NON-NLS-2$
		companyName.setNullRepresentation(Messages.getString("InformationView.nullRepresentation")); //$NON-NLS-1$
		companyName.setWidth(Messages.getString("InformationView.textFieldWidth"));   //$NON-NLS-1$
		final AbstractTextField advertiserName = (AbstractTextField)fieldGroup.buildAndBind(Messages.getString("InformationView.advertiserName"), "advertiserName"); //$NON-NLS-1$ //$NON-NLS-2$
		advertiserName.setNullRepresentation(Messages.getString("InformationView.nullRepresentation")); //$NON-NLS-1$
		advertiserName.setWidth(Messages.getString("InformationView.textFieldWidth")); //$NON-NLS-1$
		
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
		
		Button continueButton = new Button(Messages.getString("InformationView.continue"));	 //$NON-NLS-1$
		continueButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	try {
            		
            		Collection<String> fieldGroupErrors = ErrorUtils.getComponentError(fieldGroup.getFields());
            		
            		if(fieldGroupErrors.isEmpty()){
            			firstName.addValidator(new BeanValidator(ContactInformation.class, "firstName")); //$NON-NLS-1$
            			lastName.addValidator(new BeanValidator(ContactInformation.class, "lastName")); //$NON-NLS-1$
            			emailAddress.addValidator(new BeanValidator(ContactInformation.class, "emailAddress")); //$NON-NLS-1$
            			phoneNumber.addValidator(new BeanValidator(ContactInformation.class, "phoneNumber")); //$NON-NLS-1$
            			companyName.addValidator(new BeanValidator(ContactInformation.class, "companyName")); //$NON-NLS-1$
            			advertiserName.addValidator(new BeanValidator(ContactInformation.class, "advertiserName")); //$NON-NLS-1$
            		}
            		
                    fieldGroup.commit();
                } catch (CommitException e) {
                    // Show all the validate errors:
                    ErrorUtils.showComponentErrors(fieldGroup.getFields());

                    return;
                }
            	getUI().getNavigator().addView(UploadView.NAME, UploadView.class);
                // Navigate to upload creative view
                getUI().getNavigator().navigateTo(UploadView.NAME);
            }
        });
		Button cancelButton = new Button(Messages.getString("InformationView.cancel")); //$NON-NLS-1$
		cancelButton.addClickListener(new Button.ClickListener() {
            public void buttonClick(ClickEvent event) {
            	if(oldView instanceof HomeView){
            		// "Logout" the user
    	            getSession().setAttribute(Messages.getString("InformationView.sessionUser"), null);    //$NON-NLS-1$

    	            // Refresh this view, should redirect to login view
    	            getUI().getNavigator().navigateTo(NAME);
	            }
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
		oldView = event.getOldView();	
	}
	
}
