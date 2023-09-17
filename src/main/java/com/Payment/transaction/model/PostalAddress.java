package com.Payment.transaction.model;


@lombok.AllArgsConstructor
@lombok.RequiredArgsConstructor
@lombok.NoArgsConstructor
@lombok.Builder
public class PostalAddress {

	
	private String postBox;
	private String addressLine1;
	private String addressLine2;
	private String postCode;
	private String townName;
	private String countrySubDivision;
	private String country;
	
	public PostalAddress postBox(String postBox) {
		this.postBox=postBox;
		return this;
	}
	public String getpostBox() {
		return postBox;
	}
	public void setpostBox(String postBox) {
		this.postBox=postBox;
	}
	
	public PostalAddress addressLine1(String addressLine1) {
		this.addressLine1=addressLine1;
		return this;
	}
	public String getAddressLine1() {
		return addressLine1;
	}
	public void setAddressLine1(String addressLine1) {
		this.addressLine1=addressLine1;
	}
	
	
	public PostalAddress addressLine2(String addressLine2) {
		this.addressLine2=addressLine2;
		return this;
	}
	public String getAddressLine2() {
		return addressLine2;
	}
	public void setAddressLine2(String addressLine2) {
		this.addressLine2=addressLine2;
	}
	
	
	public PostalAddress postCode(String addressLine1) {
		this.postCode=postCode;
		return this;
	}
	public String getPostCode() {
		return postCode;
	}
	public void setPostCode(String postCode) {
		this.postCode=postCode;
	}
	
	public PostalAddress townName(String townName) {
		this.townName=townName;
		return this;
	}
	public String getTownName() {
		return townName;
	}
	public void setTownName(String townName) {
		this.townName=townName;
	}
	
	public PostalAddress countrySubDivision(String countrySubDivision) {
		this.countrySubDivision=countrySubDivision;
		return this;
	}
	public String getcountrySubDivision() {
		return countrySubDivision;
	}
	public void setcountrySubDivision(String countrySubDivision) {
		this.countrySubDivision=countrySubDivision;
	}
	
	
	
	public PostalAddress country(String country) {
		this.country=country;
		return this;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country=country;
	}
	
}
