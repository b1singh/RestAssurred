package com.Payment.transaction.model;

public class ComprensivePaymentInfo {

	private String tokenid;
	private String endtoendIdentification;
	
	public ComprensivePaymentInfo tokenid(String tokenid)
	{
	this.tokenid=tokenid;
	return this;
	}
	public String gettokenId() {
		return tokenid;
	}
	public void setTokenId(String tokenid) {
		this.tokenid=tokenid;
	}
	public void setEndToEndIdentification(String endtoendIdentification) {
		this.endtoendIdentification=endtoendIdentification;
	}
	public String getEndToEndIdentification() {
		return endtoendIdentification;
	}
	
}
