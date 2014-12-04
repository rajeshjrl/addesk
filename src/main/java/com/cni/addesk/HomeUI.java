package com.cni.addesk;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomLayout;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

@Theme("cnitheme")
@SuppressWarnings("serial")
@PreserveOnRefresh
@Title("Account Activity")
public class HomeUI extends UI
{

    @WebServlet(value = "/*", asyncSupported = true)
    @VaadinServletConfiguration(productionMode = false, ui = HomeUI.class, widgetset = "com.cni.addesk.AppWidgetSet")
    public static class Servlet extends VaadinServlet {
    }

    @Override
    protected void init(VaadinRequest request) {    
    	// Define a common menu command for all the menu items.
    	MenuBar.Command menuCommand = new MenuBar.Command() {
    	    public void menuSelected(MenuItem selectedItem) {
    	    	System.out.println("Hello World!");
    	    }  
    	};
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        CustomLayout headerTemplate = new CustomLayout("header");
        MenuBar menubar = new MenuBar();
        MenuItem menuItem = menubar.addItem("", new ThemeResource("layouts/images/menu_mobile.png"), null);
        menuItem.addItem("Home", null, menuCommand);
        menuItem.addItem("Reports", null, menuCommand);
        menuItem.addItem("History", null, menuCommand);
        menuItem.addItem("Help & Information", null, menuCommand);
        menuItem.addItem("Sign Out", null, menuCommand);
        
        MenuBar rightMenu = new MenuBar();
        MenuItem homeMenuItem = rightMenu.addItem("Home", null, null);        
        MenuItem helpMenuItem = rightMenu.addItem("Help & Information", null, null);
        MenuItem myaccountMenuItem = rightMenu.addItem("My Account", null, null);
        myaccountMenuItem.addItem("Sign Out", null, menuCommand);
        
        headerTemplate.addComponent(menubar, "menubar");
        headerTemplate.addComponent(rightMenu, "rightMenu");
        
        layout.addComponent(headerTemplate);
    }

}
