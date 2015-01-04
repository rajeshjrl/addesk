package com.cni.addesk.bean;

import java.io.Serializable;

public class ProductsName implements Serializable{
	
	String name;

    public ProductsName(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
