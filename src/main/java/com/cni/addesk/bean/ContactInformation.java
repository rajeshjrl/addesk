package com.cni.addesk.bean;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
 
import org.hibernate.validator.constraints.Email;


public class ContactInformation {
	
	@Size(max = 250)
	@NotNull(message = "First Name cannot be empty.")
	private String firstName;
	@Size(max = 250)
	@NotNull(message = "Last Name cannot be empty.")
	private String lastName;
	@Size(max = 250)
	@NotNull(message = "email Address cannot be empty.")
	@Email(message = "Not a valid email Address.")
	private String emailAddress;
	@Size(max = 250)
	@NotNull(message = "Phone Number cannot be empty.")
	private String phoneNumber;
	@Size(max = 250)
	private String companyName;
	@Size(max = 250)
	private String advertiserName;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmailAddress() {
		return emailAddress;
	}
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getAdvertiserName() {
		return advertiserName;
	}
	public void setAdvertiserName(String advertiserName) {
		this.advertiserName = advertiserName;
	}	

}
