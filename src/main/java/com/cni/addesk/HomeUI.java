package com.cni.addesk;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.PreserveOnRefresh;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CustomLayout;
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
        final VerticalLayout layout = new VerticalLayout();
        setContent(layout);
        CustomLayout headerTemplate = new CustomLayout("header");
        layout.addComponent(headerTemplate);
    }

}
