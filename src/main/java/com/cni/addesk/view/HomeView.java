package com.cni.addesk.view;

import com.ejt.vaadin.loginform.DefaultVerticalLoginForm;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.AbstractOrderedLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.VerticalLayout;

public class HomeView extends CustomComponent implements View{
	
	public static final String NAME = "home";
	private final AbstractOrderedLayout rootVerticalLayout;
	
	private MenuBar.Command menuCommand = new MenuBar.Command() {
	    public void menuSelected(MenuItem selectedItem) {
	    	if("Login".equals(selectedItem.getText())){	    		
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
	    	}
	    }  
	};
	
	public HomeView() {
	    setSizeFull();
	    rootVerticalLayout = new VerticalLayout();
		setCompositionRoot(rootVerticalLayout);
		CustomLayout headerTemplate = new CustomLayout("header");
				
		MenuBar rightMenu = new MenuBar();
		MenuItem homeMenuItem = rightMenu.addItem("Home", null, menuCommand);        
		MenuItem helpMenuItem = rightMenu.addItem("Help & Information", null, menuCommand);
		MenuItem signInItem = rightMenu.addItem("Login", null, menuCommand);
		headerTemplate.addComponent(rightMenu, "rightMenu");
		
		rootVerticalLayout.addComponent(headerTemplate);
	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
	
	private class LoginForm extends DefaultVerticalLoginForm {

        @Override
        protected void login(String userName, String password) {
            //
            // Validate username and password with database here. For examples sake I use a dummy username and password.
            //
            boolean isValid = userName.equals("test@test.com") && password.equals("passw0rd");

            if (isValid) {
                // Store the current user in the service session
                getSession().setAttribute("user", userName);
                // Navigate to main view
                getUI().getNavigator().navigateTo(MainView.NAME);

            } 
        }
    }

}
