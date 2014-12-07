package com.cni.addesk.ui;

import javax.servlet.annotation.WebServlet;

import com.cni.addesk.view.HomeView;
import com.cni.addesk.view.MainView;
import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.UI;

@Theme("cnitheme")
@SuppressWarnings("serial")
@PreserveOnRefresh
@Title("Account Activity")
public class HomeUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = HomeUI.class, widgetset = "com.cni.addesk.ui.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {    
    	// Define a common menu command for all the menu items.
    	//
        // Create a new instance of the navigator. The navigator will attach
        // itself automatically to this view.
        //
        new Navigator(this, this);

        //
        // The initial log view where the user can login to the application
        //
        getNavigator().addView(HomeView.NAME, HomeView.class);

        //
        // Add the main view of the application
        //
        getNavigator().addView(MainView.NAME, MainView.class);

        //
        // We use a view change handler to ensure the user is always redirected
        // to the login view if the user is not logged in.
        //
        getNavigator().addViewChangeListener(new ViewChangeListener() {

            @Override
            public boolean beforeViewChange(ViewChangeEvent event) {

                // Check if a user has logged in
                boolean isLoggedIn = getSession().getAttribute("user") != null;
                boolean isLoginView = event.getNewView() instanceof HomeView;

                if (!isLoggedIn && !isLoginView) {
                    // Redirect to login view always if a user has not yet logged in
                    getNavigator().navigateTo(HomeView.NAME);
                    return false;

                } else if (isLoggedIn && isLoginView) {
                    // If someone tries to access to login view while logged in,  then cancel
                    return false;
                }

                return true;
            }

            @Override
            public void afterViewChange(ViewChangeEvent event) {

            }
        });
    }    	

}
